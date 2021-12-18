package parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import automate.Automate;
import automate.AutomateString;
import automate.RegonizeToken;
import automate.Token;
import entity.Mob;
import gameObject.gameObject;
import gameObject.gameScene;
import gameObject.movement;
import gameObject.pattern;

public class levelMaker {
	Automate level = new Automate();
	List<List<Token>> groups = new ArrayList<List<Token>>();
	private int width;
	public void setWidth(int width) {
		this.width = width;
	}
	private int height;
	public void setHeight(int height) {
		this.height = height;
	}
	private HashMap<String,Function<List<Object>,gameObject>> gameObjectMatcher = new HashMap<String,Function<List<Object>,gameObject>>();
	public void put(String key,Function<List<Object>,gameObject> value) {
		gameObjectMatcher.put(key, value);
	}

	public levelMaker() {
		// level.setDebug(true);
		
		Token endCommand = new Token(";","endCommand");
		level.addToken(endCommand);

		Token endLine = new Token(Pattern.compile("[\n\t\r]",Pattern.CASE_INSENSITIVE),"endLine");
		level.addToken(endLine);
		endLine.add(endCommand);
		endLine.add(endLine);

		/** commentaire "--[a-z]*" */
		List<Token> group = new ArrayList<Token>();
		Token commentStart = new Token("--","commentStart");
		level.addToken(commentStart);
		commentStart.add(0);
		commentStart.add(endLine);

		Token commentComment = new Token(Pattern.compile("[^\n]"),"commentComment");
		level.addToken(commentComment);
		commentComment.add(commentStart);
		commentComment.add(commentComment);

		endLine.add(commentComment);

		group.add(commentComment);
		groups.add(group);

		/** define block "set . to [a-z]*;" */
		group = new ArrayList<Token>();
		Token blockStart = new Token("set ","blockStart");
		level.addToken(blockStart);
		blockStart.add(0);
		blockStart.add(endLine);
		blockStart.add(endCommand);

		Token blockVar = new Token(Pattern.compile("."),"blockVar");
		level.addToken(blockVar);
		blockVar.add(blockStart);

		Token blockAssign = new Token(" to ","blockAssign");
		level.addToken(blockAssign);
		blockAssign.add(blockVar);

		Token blockName = new Token(Pattern.compile("[a-z]"),"blockName");
		level.addToken(blockName);
		blockName.add(blockAssign);
		blockName.add(blockName);

		endCommand.add(blockName);

		group.add(blockName);
		groups.add(group);

		/** create terrain ""create terrain (.*\n)*;" */
		group = new ArrayList<Token>();
		Token terrainCreate = new Token("create terrain","terrainCreate");
		level.addToken(terrainCreate);
		terrainCreate.add(0);
		terrainCreate.add(endCommand);
		terrainCreate.add(endLine);

		Token terrainCreateSpace = new Token(Pattern.compile("[ \n\t]"),"terrainCreateSpace");
		level.addToken(terrainCreateSpace);
		terrainCreateSpace.add(terrainCreate);

		Token terrainLine = new Token(Pattern.compile("[^\n]"),"terrainLine");
		level.addToken(terrainLine);
		terrainLine.add(terrainCreate);
		terrainLine.add(terrainCreateSpace);
		terrainLine.add(terrainLine);

		terrainCreateSpace.add(terrainLine);
		endCommand.add(terrainCreateSpace);
		endCommand.add(terrainLine);

		group.add(terrainLine);
		groups.add(group);

		/** spawn entity "spawn [a-z]*((\d|"list"?,)*);" */
		group = new ArrayList<Token>();
		Token SpawnEntity = new Token("spawn ","SpawnEntity");
		level.addToken(SpawnEntity);
		SpawnEntity.add(0);
		SpawnEntity.add(endLine);

		Token EntityName = new Token(Pattern.compile("[a-z]"),"EntityName");
		level.addToken(EntityName);
		EntityName.add(SpawnEntity);
		EntityName.add(EntityName);

		group.add(EntityName);
		groups.add(group);

		/* VarTypes */

		Token Integer = new Token(Pattern.compile("[0-9]"),"Integer");
		level.addToken(Integer);
		Integer.add(Integer);
		group = new ArrayList<Token>();
		group.add(Integer);
		groups.add(group);

		Token Var = new Token(Pattern.compile("[a-z]"),"Var");
		level.addToken(Var);
		Var.add(Var);
		group = new ArrayList<Token>();
		group.add(Var);
		groups.add(group);

		Token Const = new Token(Pattern.compile("[A-Z]"),"Const");
		level.addToken(Const);
		Const.add(Const);

		group = new ArrayList<Token>();
		group.add(Const);
		groups.add(group);

		/* List */
		Token ListStart = new Token("(","ListStart");
		level.addToken(ListStart);
		ListStart.add(0);
		ListStart.add(endLine);
		ListStart.add(EntityName);
		ListStart.add(ListStart);

		Integer.add(ListStart);
		Var.add(ListStart);
		Const.add(ListStart);

		Token ListSeparator = new Token(",","ListSeparator");
		level.addToken(ListSeparator);
		ListSeparator.add(ListStart);
		ListSeparator.add(Integer);
		ListSeparator.add(Var);
		ListSeparator.add(Const);

		Integer.add(ListSeparator);
		Var.add(ListSeparator);
		Const.add(ListSeparator);
		ListStart.add(ListSeparator);

		Token ListEnd = new Token(")","ListEnd");
		level.addToken(ListEnd);
		ListEnd.add(ListStart);
		ListEnd.add(ListEnd);
		ListEnd.add(Integer);
		ListEnd.add(Var);
		ListEnd.add(Const);

		endCommand.add(ListEnd);

		/**Operator */
		Token Repeat = new Token("*","Repeat");
		level.addToken(Repeat);
		Repeat.add(Integer);
		Repeat.add(Var);
		Repeat.add(Const);

		Integer.add(Repeat);


		// exec("--set vars");
		// exec("set _ to floor;");
		// exec("set _ to floor;set _ to floor;");
		// exec("create terrain __\n__\n__;");
		// parser("spawn skeleton()");
		// parser("spawn skeleton(6,5)");
		// parser("spawn skeleton(6,4,(2,0));");
		// parser("(RIGHT)");
		// parser("(RIGHT*4,DOWN*2,LEFT*4,UP*2)");
		// exec("spawn skeleton(6,4,(RIGHT*4,DOWN*2,LEFT*4,UP*2));");
	}
	public List<RegonizeToken> parser(String map){
		return level.regroup(new AutomateString(map),groups);
	}
	private HashMap<String,String> block_synonym = new HashMap<String,String>();

	/**
	 * execute la chaine de character entré
	 * @param map
	 * @return 
	 */
	public gameScene exec(String map){
		scene = new gameScene(width,height);
		// decoupage des tokens
		List<RegonizeToken> tokens = this.parser(map);
		// System.err.println(tokens);
		exec(tokens);
		return scene;
	}
	/**
	 * Analyse Syntaxique et Interpretation
	 * @param tokens
	 * @return
	 */
	public Object exec(List<RegonizeToken> tokens){
		String synonym = "";
		int y = 0;
		List<Object> args = new ArrayList<Object>();
		boolean beinrecurtion = false;
		int indent = -1;
		String callback= "";
		List<RegonizeToken> childList = new ArrayList<RegonizeToken>();
		boolean isrepeated = false;
		for (RegonizeToken token : tokens) {
			if(beinrecurtion){
				if(token.getOutput() == "ListEnd" && indent == 1){
					beinrecurtion = false;
					childList.add(token);
					args.add(exec(childList));
					childList.clear();
					indent = 0;
				}else{
					if(token.getOutput() == "ListStart"){
						indent++;
					}
					if(token.getOutput() == "ListEnd"){
						indent--;
					}
					childList.add(token);
				}
			}else{
				switch(token.getOutput()){
					case"commentComment":
						System.out.println(token.getInput());
					break;
					case"blockVar":
						synonym = token.getInput();
					break;
					case"blockName":
						block_synonym.put(synonym, token.getInput());
					break;
					case"terrainLine":
						String line = token.getInput();
						for (int i = 0; i < line.length(); i++) {
							String block = ""+line.charAt(i);
							block = block_synonym.get(block);
							createBlock(block,i,y);
							// System.out.println("block"+block+" "+i+"x"+y);
						}
						if(line.length()>2)
							y++;
					break;
					case "EntityName":
						callback = token.getInput();
					break;
					case "Var":
						System.out.println(token.getInput());
					break;
					case "Const":
						args.add(token.getInput());
					break;
					case "Integer":
						int value = Integer.valueOf(token.getInput());
						if(isrepeated){
							Object last = args.get(args.size()-1);
							for (int i = 0; i < value; i++) {
								args.add(last);
							}
							isrepeated = false;
						}else{
							args.add(value);
						}
					break;
					case "ListEnd":
						indent = -1;
					break;
					case "ListStart":
						if(indent == 0){
							indent = 1;
							beinrecurtion = true;
							childList.clear();
							childList.add(token);
						}else{
							indent = 0;
						}
					break;
					case "Repeat":
						isrepeated = true;
					break;
					case "endCommand":
						createEntity(callback, args);
						args.clear();
					break;
				}
			}
		}
		return args;
	}
	private gameScene scene;
	public gameScene getScene() {
		return scene;
	}
	private void createBlock(String name,int x,int y){
		Function<List<Object>, gameObject> callback = this.gameObjectMatcher.get(name);
		if(callback!=null){
			gameObject gO = callback.apply(Arrays.asList(x,y));
			if(gO!=null)
			scene.placeBlock(gO, x, y);
		}
	}
	private void createEntity(String name,List<Object> args){
		Function<List<Object>, gameObject> callback = this.gameObjectMatcher.get(name);
		if(callback!=null){
			gameObject gO = callback.apply(args);
			int x = (int) args.get(0);
			int y = (int) args.get(1);
			pattern path = new pattern();
			if(args.size()>2 && args.get(2)!=null){
				List<String> lso = (List<String>) args.get(2);
				for (String move : lso) {
					 switch(move){
							case "RIGHT":path.add(movement.RIGHT);break;
							case "DOWN":path.add(movement.DOWN);break;
							case "LEFT":path.add(movement.LEFT);break;
							case "UP":path.add(movement.UP);break;
							case "RESTART":path.add(movement.RESTART);break;
					 }
				}
				
			}
			if(gO!=null){
				gO.setX(x);
				gO.setY(y);
				scene.placeBlock(gO);
				if(args.size()>2 && args.get(2)!=null && gO instanceof Mob){
					((Mob)gO).setInitX(x);
					((Mob)gO).setInitY(y);
					((Mob)gO).setPattern(path);
				}
			}
			// System.out.println(name);
			if(name.equals("player")){
				scene.center(x,y);
			}
		}
	}
}
