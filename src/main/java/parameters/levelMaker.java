package parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import automate.Automate;
import automate.AutomateString;
import automate.RegonizeToken;
import automate.Token;

public class levelMaker {
	Automate level = new Automate();
	List<List<Token>> groups = new ArrayList<List<Token>>();
	
	public levelMaker() {
		level.setDebug(true);
		
		Token endCommand = new Token(";","endCommand");
		level.addToken(endCommand);

		/** commentaire "--[a-z]*" */
		List<Token> group = new ArrayList<Token>();
		Token commentStart = new Token("--","commentStart");
		level.addToken(commentStart);
		commentStart.add(0);

		Token commentComment = new Token(Pattern.compile("[^\n]"),"commentComment");
		level.addToken(commentComment);
		commentComment.add(commentStart);
		commentComment.add(commentComment);

		group.add(commentComment);
		groups.add(group);

		/** define block "set . to [a-z]*;" */
		group = new ArrayList<Token>();
		Token blockStart = new Token("set ","blockStart");
		level.addToken(blockStart);
		blockStart.add(0);

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


		// parser("--set vars");
		// parser("set _ to floor;");
		// parser("create terrain __\n__\n__;");
		// parser("spawn skeleton()");
		// parser("spawn skeleton(6,5)");
		// parser("spawn skeleton(6,4,(2,0));");
		// parser("(RIGHT)");
		// parser("(RIGHT*4,DOWN*2,LEFT*4,UP*2)");
		// parser("spawn skeleton(6,4,(RIGHT*4,DOWN*2,LEFT*4,UP*2));");
	}
	public List<RegonizeToken> parser(String map){
		return level.regroup(new AutomateString(map),groups);
	}
	public void exec(String map){
		List<RegonizeToken> tokens = this.parser(map);
	}
}
