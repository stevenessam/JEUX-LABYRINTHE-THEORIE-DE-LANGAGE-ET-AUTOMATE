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
	public static final String stairs = "stairs.png";
	public static final String stairs_item = "stairsitem.png";

	private static int scale;
	public static int getScale() {
		return scale;
	}
	public static void setScale(int scale) {
		Textures.scale = scale;
	}

	public static Image load(String url){
		Image input = new Image(url);
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final int S = scale;
		
		WritableImage output = new WritableImage(
		  W * S,
		  H * S
		);
		
		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();
		
		for (int y = 0; y < H; y++) {
		  for (int x = 0; x < W; x++) {
			final int argb = reader.getArgb(x, y);
			for (int dy = 0; dy < S; dy++) {
			  for (int dx = 0; dx < S; dx++) {
				writer.setArgb(x * S + dx, y * S + dy, argb);
			  }
			}
		  }
		}
		
		return output;
		// return new Image(url,width,height,false,false);
	}
}
