package parameters;

import automate.Automate;
import automate.Token;
import gameObject.gameObject;
import gameObject.gameScene;
import gameObject.player;
import javafx.scene.paint.Material;
import ressourceManages.Textures;

import javax.security.auth.kerberos.KerberosTicket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class levelConfig<BlockPlaceEvent, Player, Block> {
	private optionConfig option;
	//private KerberosTicket Bukkit;

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
	public void token(){


		//Etats de L'automate
		Automate ParaAuto = new Automate();
		Token ObjectStart = new Token("{","ObjectStart");
		ObjectStart.add(0);
		ParaAuto.addToken(ObjectStart);
		Token Indent = new Token(Pattern.compile("[\n\t\r]",Pattern.CASE_INSENSITIVE),"Indent");
		Indent.add(ObjectStart);
		ParaAuto.addToken(Indent);
		Token ObjectKey = new Token(Pattern.compile("[a-z]",Pattern.CASE_INSENSITIVE),"ObjectKey");
		ObjectKey.add(ObjectStart);
		ObjectKey.add(Indent);
		ParaAuto.addToken(ObjectKey);
		ObjectKey.add(ObjectKey);

	}
	/*Placer un block*/



}

