package ressourceManages;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Textures {
	public final static String night_backdrop = "backdrop.png";
	public final static String Knight = "knight.png";

	public final static String wall = "wall.png";
	public final static String wall_lighted = "wall_lighted.png";
	public final static String mob = "skeleton.png";

	public final static String Door = "door.png";
	public final static String KeyDoor = "key.png";
	public static final String Stats = "stats.png";
	public static final String Floor = "floor.png";
	public static final String HeatlthPostion = "potion.png";

	public static final String stairs = "stairs.png";
	public static final String stairs_item = "stairsitem.png";
	public static final String font = "font.png";
	public static final String DarkSky = "DarkSky.png";
	public static final String Title = "title.png";

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
		Image input = new Image(url);
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
		// return new Image(url,width,height,false,false);
	}
}
