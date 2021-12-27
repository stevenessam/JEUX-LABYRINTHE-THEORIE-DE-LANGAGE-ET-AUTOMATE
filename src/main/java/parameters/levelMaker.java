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
	/**
	 * ajouter un object au langage;
	 * @param key
	 * @param value
	 */
	public void put(String key,Function<List<Object>,gameObject> value) {
		gameObjectMatcher.put(key, value);
	}

	public levelMaker() {
		//afficher si le token et reconnu ou pas (debug mode)
		//  level.setDebug(true);
		
		Token endCommand = new Token(";","endCommand");
		level.addToken(endCommand);

		Token endLine = new Token(Pattern.compile("[\n\t\r]",Pattern.CASE_INSENSITIVE),"endLine");
		level.addToken(endLine);
		endLine.add(endCommand);
		endLine.add(endLine);

		/** commentaire "--[a-z]*" */
		List<Token> group = new ArrayList<Token>();
		//definir un  commantaire
		Token commentStart = new Token("--","commentStart");
		level.addToken(commentStart);
		commentStart.add(0);
		commentStart.add(endLine);

		// contenu un commantaire (il se fini par \n
		// donc le commentaire c'est tout le reste : ^\n)
		Token commentComment = new Token(Pattern.compile("[^\n]"),"commentComment");
		level.addToken(commentComment);
		commentComment.add(commentStart);
		commentComment.add(commentComment);

		//après la fin du commentaire on va a l'etat endLine
		endLine.add(commentComment);

		//regroupement des symboles formant le contenu du commantaire
		group.add(commentComment);
		groups.add(group);

		/** define block "set . to [a-z]*;" */
		group = new ArrayList<Token>();
		//definir un caracter comme un block (le caracter sera utiliser dans create terrain)
		Token blockStart = new Token("set ","blockStart");
		level.addToken(blockStart);
		blockStart.add(0);
		blockStart.add(endLine);
		blockStart.add(endCommand);

		//caracter choisit 
		Token blockVar = new Token(Pattern.compile("."),"blockVar");
		level.addToken(blockVar);
		blockVar.add(blockStart);

		//assignement (equivalent à = dans la plus par des langauge)
		Token blockAssign = new Token(" to ","blockAssign");
		level.addToken(blockAssign);
		blockAssign.add(blockVar);

		//nom du block (il doit etre aussi present dans les objects du language)
		Token blockName = new Token(Pattern.compile("[a-z]"),"blockName");
		level.addToken(blockName);
		blockName.add(blockAssign);
		blockName.add(blockName);

		endCommand.add(blockName);

		group.add(blockName);
		groups.add(group);

		/** create terrain ""create terrain (.*\n)*;" */
		group = new ArrayList<Token>();
		// creation terrain
		Token terrainCreate = new Token("create terrain","terrainCreate");
		level.addToken(terrainCreate);
		terrainCreate.add(0);
		terrainCreate.add(endCommand);
		terrainCreate.add(endLine);

		//changement de ligne sur le terrain
		Token terrainCreateSpace = new Token(Pattern.compile("[ \n\t]"),"terrainCreateSpace");
		level.addToken(terrainCreateSpace);
		terrainCreateSpace.add(terrainCreate);

		//contenu d'un ligne du terrain
		Token terrainLine = new Token(Pattern.compile("[^\n]"),"terrainLine");
		level.addToken(terrainLine);
		terrainLine.add(terrainCreate);
		terrainLine.add(terrainCreateSpace);
		terrainLine.add(terrainLine);

		//après une ligne on peut aller a l'etat terrainCreateSpace (pour remettre un nouvelle ligne)
		terrainCreateSpace.add(terrainLine);
		//après l'etat terrainCreateSpace on peut finir la command (et autoriser l'execution la commande create terrain)
		endCommand.add(terrainCreateSpace);
		//après une ligne on peut finir la command (et autoriser l'execution la commande create terrain)
		endCommand.add(terrainLine);

		// regrouper les symboles d'une ligne (le terrain de genere ligne par ligne)
		group.add(terrainLine);
		groups.add(group);

		/** spawn entity "spawn [a-z]*((\d|"list"?,)*);" */
		group = new ArrayList<Token>();
		//definir la postion d'un entité (Mob ou player) (cela pourrait aussi etre un block)
		Token SpawnEntity = new Token("spawn ","SpawnEntity");
		level.addToken(SpawnEntity);
		SpawnEntity.add(0);
		SpawnEntity.add(endLine);

		//le nom de l'object
		Token EntityName = new Token(Pattern.compile("[a-z]"),"EntityName");
		level.addToken(EntityName);
		EntityName.add(SpawnEntity);
		EntityName.add(EntityName);

		//regrouper les symbole formant le nom
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

		/* List (.*?,?) */
		Token ListStart = new Token("(","ListStart");
		level.addToken(ListStart);
		ListStart.add(0);
		ListStart.add(endLine);
		ListStart.add(EntityName);
		ListStart.add(ListStart);

		Integer.add(ListStart);
		Var.add(ListStart);
		Const.add(ListStart);

		Token ListSeparator = new Token(Pattern.compile("\s*,"),"ListSeparator");
		level.addToken(ListSeparator);
		ListSeparator.add(ListStart);
		ListSeparator.add(Integer);
		ListSeparator.add(Var);
		ListSeparator.add(Const);

		Integer.add(ListSeparator);
		Var.add(ListSeparator);
		Const.add(ListSeparator);
		ListStart.add(ListSeparator);

		Token ListSpace = new Token(Pattern.compile("\s"),"ListSeparator");
		level.addToken(ListSpace);
		ListSpace.add(ListSeparator);
		ListSpace.add(ListStart);

		Integer.add(ListSpace);
		Var.add(ListSpace);
		Const.add(ListSpace);
		ListStart.add(ListSpace);

		Token ListEnd = new Token(Pattern.compile("\s*[)]"),"ListEnd");
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

		/** list des test effectuer avec le langauge */
		// exec("--set vars");
		// exec("set _ to floor;");
		// exec("set _ to floor;set _ to floor;");
		// exec("create terrain __\n__\n__;");
		// parser("spawn skeleton()");
		// List<RegonizeToken> tokens = parser("spawn skeleton(6,5)");
		// List<RegonizeToken> tokens = parser("spawn skeleton(6 , 5)");
		// List<RegonizeToken> tokens = parser("spawn skeleton(6 , 4 , (2 , 0) );");
		// System.out.println(tokens);
		// parser("(RIGHT)");
		// parser("(RIGHT*4,DOWN*2,LEFT*4,UP*2)");
		// exec("spawn skeleton(6,4,(RIGHT*4,DOWN*2,LEFT*4,UP*2));");
	}
	/**
	 * parse la map entree en parametre avec le langage de creation de niveau (map)
	 * @param map
	 * @return
	 */
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
		// l'execution genere un scene avec la map
		scene = new gameScene(width,height);
		// decoupage des tokens
		List<RegonizeToken> tokens = this.parser(map);
		// System.err.println(tokens);
		// execution des tokens
		exec(tokens);
		return scene;
	}
	/**
	 * Analyse Syntaxique et Interpretation
	 * @param tokens
	 * @return
	 */
	public Object exec(List<RegonizeToken> tokens){
		//synonyme du block
		String synonym = "";
		// ligne de la map
		int y = 0;
		
		// recurrence pour les listes
		boolean beinrecurtion = false;
		int indent = -1;
		List<RegonizeToken> childList = new ArrayList<RegonizeToken>();
		
		//nom de la class a initialiser
		String callback= "";
		// argurments pour la function
		List<Object> args = new ArrayList<Object>();

		// la varaiable est repeter
		boolean isrepeated = false;
		for (RegonizeToken token : tokens) {
			if(beinrecurtion){
				if(token.getOutput() == "ListEnd" && indent == 1){
					// fin list
					beinrecurtion = false;
					childList.add(token);
					// execution du code et ajoute de la list en argument
					args.add(exec(childList));
					childList.clear();
					indent = 0;
				}else{
					// code a executer à la recurtion
					if(token.getOutput() == "ListStart"){
						// debut d'un sous list
						indent++;
					}
					if(token.getOutput() == "ListEnd"){
						// fin d'un sous list
						indent--;
					}
					childList.add(token);
				}
			}else{
				switch(token.getOutput()){
					case"commentComment":
						// commantaire afficher en console
						System.out.println(token.getInput());
					break;
					case"blockVar":
						//synonyme utilise
						synonym = token.getInput();
					break;
					case"blockName":
						//enregistrer un symbole comme synonyme d'un block
						block_synonym.put(synonym, token.getInput());
					break;
					case"terrainLine":
						// traintement d'un ligne de terrain
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
						//nom de la class a initialiser
						callback = token.getInput();
					break;
					case "Var":
						// non implementer
						System.out.println(token.getInput());
					break;
					case "Const":
						// ajouter la constante au argument
						args.add(token.getInput());
					break;
					case "Integer":
						int value = Integer.valueOf(token.getInput());
						if(isrepeated){
							// repeter le dernier argument
							Object last = args.get(args.size()-1);
							for (int i = 0; i < value; i++) {
								args.add(last);
							}
							isrepeated = false;
						}else{
							// ajouter le nombre au argument
							args.add(value);
						}
					break;
					case "ListEnd":
						//fin list retour indent init
						indent = -1;
					break;
					case "ListStart":
						//debut list
						if(indent == 0){
							// preparation recurence
							indent = 1;
							beinrecurtion = true;
							childList.clear();
							childList.add(token);
						}else{
							indent = 0;
						}
					break;
					case "Repeat":
						//est repeter
						isrepeated = true;
					break;
					case "endCommand":
						//execution de la commande
						createEntity(callback, args);
						args.clear();
					break;
				}
			}
		}
		return args;
	}
	private gameScene scene;
	/**
	 * retourne la scene creer par l'exectuion 
	 * @see gameObject 
	 * @return
	 */
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
			if(args.size()>2 && args.get(2)!=null && args.get(2) instanceof List){
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
