package parameters;

import automate.Automate;
import automate.Token;
import gameObject.gameObject;
import gameObject.gameScene;
import gameObject.player;
import ressourceManages.Textures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class levelConfig {
	private optionConfig option;
	public levelConfig(optionConfig option) {
		this.option = option;
	}public gameScene getLevelScene(){
		gameScene scene = new gameScene(480,360);
		scene.setBackdrop(Textures.night_backdrop);
		scene.add(new gameObject(0,0));
		player a =new player(0,0);
		a.setControls(option.getPlayerControls(0));
		scene.add(a);
		return scene;
	}

	public String ReadeFile(String file) {
		Automate optionLang = new Automate();
		Token ObjectStart = new Token("{","ObjectStart");
		ObjectStart.add(0);
		optionLang.addToken(ObjectStart);
		Token Indent = new Token(Pattern.compile("[\n\t\r]",Pattern.CASE_INSENSITIVE),"Indent");
		Indent.add(ObjectStart);
		optionLang.addToken(Indent);
		Token ObjectKey = new Token(Pattern.compile("[a-z]",Pattern.CASE_INSENSITIVE),"ObjectKey");
		ObjectKey.add(ObjectStart);
		ObjectKey.add(Indent);
		optionLang.addToken(ObjectKey);
		ObjectKey.add(ObjectKey);

		InputStream config = optionConfig.class.getResourceAsStream(file);
		if (config != null) {
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
}

