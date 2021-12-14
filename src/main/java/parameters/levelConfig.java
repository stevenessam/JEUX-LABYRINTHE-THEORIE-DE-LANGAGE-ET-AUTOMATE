package parameters;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class levelConfig {
    try{
        InputStream flux=new FileInputStream();
        InputStreamReader lecture=new InputStreamReader(flux);
        BufferedReader buff=new BufferedReader(lecture);
        String ligne;
        while ((ligne=buff.readLine())!=null){
            System.out.println(ligne);
        }
        buff.close();

catch (Exception e){
        System.out.println(e.toString());
    }

}
