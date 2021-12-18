package parameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import automate.Automate;
import automate.AutomateString;
import automate.RegonizeToken;
import automate.Token;
import javafx.scene.input.KeyCode;

public class optionConfig {
	public optionConfig(String path){
		/* Setup Language */
		Automate optionLang = new Automate();
		// optionLang.setDebug(true);

		Token ObjectStart = new Token("{","ObjectStart");
		ObjectStart.add(0);
		optionLang.addToken(ObjectStart);

		// System.out.println(Pattern.compile("[\t\n\r]",Pattern.CASE_INSENSITIVE).matcher("a\nb\n\tc").find());

		Token Indent = new Token(Pattern.compile("[\n\t\r]",Pattern.CASE_INSENSITIVE),"Indent");
		// Token Indent = new Token(Arrays.asList(new Character[]{9,10,13}),"Indent");
		Indent.add(ObjectStart);
		optionLang.addToken(Indent);

		Token ObjectKeyStart = new Token("\"","ObjectKeyStart");
		optionLang.addToken(ObjectKeyStart);
		ObjectKeyStart.add(ObjectStart);
		ObjectKeyStart.add(Indent);

		Token ObjectKeyContent = new Token(Pattern.compile("[^\"]"),"ObjectKeyContent");
		optionLang.addToken(ObjectKeyContent);
		ObjectKeyContent.add(ObjectKeyStart);
		ObjectKeyContent.add(ObjectKeyContent);

		Token ObjectKeyEnd = new Token("\"","ObjectKeyEnd");
		optionLang.addToken(ObjectKeyEnd);
		ObjectKeyEnd.add(ObjectKeyStart);
		ObjectKeyEnd.add(ObjectKeyContent);

		Indent.add(ObjectKeyEnd);

		// Token ObjectKey = new Token(Pattern.compile("[a-z]",Pattern.CASE_INSENSITIVE),"ObjectKey");
		// ObjectKey.add(ObjectStart);
		// ObjectKey.add(Indent);
		// optionLang.addToken(ObjectKey);
		
		// ObjectKey.add(ObjectKey);

		Token ObjectAssign = new Token(":","ObjectAssign");
		ObjectAssign.add(ObjectKeyEnd);
		optionLang.addToken(ObjectAssign);

		Indent.add(ObjectAssign);
		ObjectStart.add(ObjectAssign);

		Token ObjectStringStart = new Token("\"","ObjectStringStart");
		ObjectStringStart.add(ObjectAssign);
		optionLang.addToken(ObjectStringStart);

		Token ObjectStringContent = new Token(Pattern.compile("[^\"]"),"ObjectStringContent");
		ObjectStringContent.add(ObjectStringStart);
		optionLang.addToken(ObjectStringContent);
		ObjectStringContent.add(ObjectStringContent);

		Token ObjectStringEnd = new Token("\"","ObjectStringEnd");
		ObjectStringEnd.add(ObjectStringStart);
		ObjectStringEnd.add(ObjectStringContent);
		optionLang.addToken(ObjectStringEnd);

		Indent.add(ObjectStringEnd);

		Token ObjectSeparator = new Token(",","ObjectSeparator");
		ObjectSeparator.add(ObjectStringEnd);
		optionLang.addToken(ObjectSeparator);

		Indent.add(ObjectSeparator);
		ObjectKeyStart.add(ObjectSeparator);

		Token ObjectEnd = new Token("}","ObjectEnd");
		ObjectEnd.add(ObjectEnd);
		ObjectEnd.add(ObjectStringEnd);
		ObjectEnd.add(ObjectSeparator);
		optionLang.addToken(ObjectEnd);

		Indent.add(ObjectEnd);

		// Token ValueName = new Token(Pattern.compile("[a-z]",Pattern.CASE_INSENSITIVE),"ValueName");
		// ValueName.add(-1);
		// optionLang.addToken(ValueName);

		List<List<Token>> groups = new ArrayList<>();
		List<Token> group = new ArrayList<Token>();
		group.add(ObjectStringStart);
		group.add(ObjectStringContent);
		group.add(ObjectStringEnd);
		groups.add(group);

		group = new ArrayList<Token>();
		group.add(ObjectKeyContent);
		groups.add(group);

		/* Prepare Execution */
		
		String config = readFile(path);
		// System.out.println(config);
		List<RegonizeToken> tokens = optionLang.regroup(new AutomateString(config),groups);
		// System.out.println(tokens);
		HashMap<String,Object> opts = interprete(tokens);
		
		if(opts.get("level")!= null){
			level = Integer.valueOf((String) opts.get("level"));
		}

		Object obj_play = opts.get("player");
		if(obj_play instanceof HashMap){
			HashMap<String,Object> player = (HashMap<String, Object>) obj_play;
			HashMap<KeyCode, String> playercontrols = new HashMap<KeyCode, String>();
			player.forEach((String key,Object value)->{
				KeyCode keycode = null;
				switch(value.toString()){
					case "UP":keycode = KeyCode.UP;break;
					case "Z":keycode = KeyCode.Z;break;
					case "LEFT":keycode = KeyCode.LEFT;break;
					case "D":keycode = KeyCode.D;break;
					case "Q":keycode = KeyCode.Q;break;
					case "RIGHT":keycode = KeyCode.RIGHT;break;
					case "S":keycode = KeyCode.S;break;
					case "DOWN":keycode = KeyCode.DOWN;break;
				}
				playercontrols.put(keycode,key);
			});
			this.playerControls.add(playercontrols);
		}
		
	}
	private HashMap<String,Object> interprete(List<RegonizeToken> tokens){
		HashMap<String,Object> Object = new HashMap<String,Object>();
		String name = null;
		Object value = null;
		boolean beinrecurtion = false;
		int indent = -1;
		List<RegonizeToken> childList = new ArrayList<RegonizeToken>();
		for (RegonizeToken token : tokens) {
			// System.out.println("Watch on "+token+"with indent = "+indent);
			if(beinrecurtion){
				if(token.getOutput() == "ObjectEnd" && indent == 1){
					beinrecurtion = false;
					childList.add(token);
					value = interprete(childList);
					childList.clear();
					indent = 0;
				}else{
					if(token.getOutput() == "ObjectStart"){
						indent++;
					}
					if(token.getOutput() == "ObjectEnd"){
						indent--;
					}
					childList.add(token);
				}
			}else{
				switch(token.getOutput()){
					case "ObjectStart":
						if(indent == 0){
							indent = 1;
							beinrecurtion = true;
							childList.clear();
							childList.add(token);
						}else{
							indent = 0;
						}
					break;
					case "ObjectSeparator":
						Object.put(name, value);
						name = null;
						value = null;
					break;
					case "ObjectKeyContent":
						name = token.getInput();
					break;
					case "ObjectStringStart":
						String str = token.getInput();
						value = str.substring(1, str.length()-1);
					break;
					case "Indent":
						// System.out.println("Indent");
					break;
				}
			}
		}
		if(name != null){
			Object.put(name, value);
		}
		return Object;
	}
	private String readFile(String file){

		InputStream config = optionConfig.class.getResourceAsStream(file);

		if(config != null){
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(config));

			String line;
			try {
				while ((line = br.readLine()) != null) {
					sb.append(line + System.lineSeparator());
				}
				return sb.toString().trim().replaceAll("[\n\t\r]", "");
		
			} catch (IOException e) {
				System.err.println("path Not find");
			}
		}else{
			System.err.println(file + "not existe");
		}
		return null;
	}
	private List<HashMap<KeyCode, String>> playerControls = new ArrayList<HashMap<KeyCode, String>>();
	public HashMap<KeyCode, String> getPlayerControls(int index){
		return playerControls.get(index);
	}
	private int level = 0;
	public int getLevel() {
		return level;
	}
}
