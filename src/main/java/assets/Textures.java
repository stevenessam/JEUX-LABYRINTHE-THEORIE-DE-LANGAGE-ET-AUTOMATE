package assets;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Textures {
	public final static String night_backdrop = "/assets/skies/backdrop.png";

	public final static String mob = "/assets/entities/skeleton.png";
	public final static String Knight = "/assets/entities/knight.png";
	public final static String skeleton = "/assets/entities/skeleton.png";
	public final static String bat = "/assets/entities/bat.png";

	public final static String wall = "/assets/blocks/wall.png";
	public final static String wall_lighted = "/assets/blocks/wall_lighted.png";
	public final static String Door = "/assets/blocks/door.png";
	public static final String Floor = "/assets/blocks/floor.png";
	public static final String stairs = "/assets/blocks/stairs.png";
	public static final String spiderWeb = "/assets/blocks/spiderWeb.png";

	public final static String KeyDoor = "/assets/items/key.png";
	public static final String HeatlthPostion = "/assets/items/potion.png";
	public static final String stairs_item = "/assets/items/stairsitem.png";
	
	public static final String DarkSky = "/assets/gui/DarkSky.png";
	public static final String font = "/assets/gui/font.png";
	public static final String Title = "/assets/gui/title.png";
	public static final String Stats = "/assets/gui/stats.png";

	private static double scale = 1;
	private static double quality = 1;
	public static void setQuality(double quality) {
		Textures.quality = quality;
	}
	public static double getQuality() {
		return quality;
	}
	public static double getScale() {
		return scale;
	}
	public static void setScale(double scale) {
		Textures.scale = scale;
	}

	public static Image load(String url){
		InputStream is = Textures.class.getResourceAsStream(url);
		Image input;
		try{
			input = new Image(is);
			final int W = (int) input.getWidth();
			final int H = (int) input.getHeight();
			final int S = (int) (scale*quality);
			
			WritableImage output = new WritableImage((int)(W * S),(int)(H * S));
			
			PixelReader reader = input.getPixelReader();
			PixelWriter writer = output.getPixelWriter();
			
			for (int y = 0; y < H; y++) {
				for (int x = 0; x < W; x++) {
					final int argb = reader.getArgb(x, y);
					for (int dy = 0; dy < S; dy++) {
						for (int dx = 0; dx < S; dx++) {
							writer.setArgb((int)(x * S + dx),(int)(y * S + dy), argb);
						}
					}
				}
			}
			return output;
		}catch(Exception e){
			System.out.println("image not find "+e.getClass().toString());
			System.out.println(Textures.class.getResourceAsStream(url));
			System.out.println(Textures.class.getResource(url));
			System.out.println("level1 : "+Textures.class.getResource("/assets/empty.png"));
			System.out.println("level1 : "+Textures.class.getResource("/maps/level1.map"));
		}
		return null;
		// return new Image(url,width,height,false,false);
	}
}
class assets {
	
}
