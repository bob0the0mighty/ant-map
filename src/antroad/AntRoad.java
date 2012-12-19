package antroad;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

public class AntRoad extends PApplet {

	private PImage				i_img, r_img;
	private final String	img_loc		= "../Eupa-third-managed.png";
	private final int			num_ants	= 3;
	private final Ant[]		ants			= new Ant[num_ants];
	private final Random	rng				= new Random(846548);

	@Override
	public void setup() {
		size(1920, 1200);
		i_img = loadImage(img_loc);
		i_img.loadPixels();
		r_img = createImage(1920, 1200, ARGB);
		r_img.loadPixels();
		ants[0] = new Ant(949, 889, i_img, r_img, rng);
		ants[1] = new Ant(949, 889, i_img, r_img, rng);
		ants[2] = new Ant(949, 889, i_img, r_img, rng);
	}

	@Override
	public void draw() {
		image(i_img, 0, 0, width, height);
		image(r_img, 0, 0);
		for (int x = 0; x < ants.length; x++) {
			// println("Home ");
			// println(home);
			int next_move = ants[x].move();
			r_img.pixels[next_move] = 0x7FFF0000;
			println(next_move);
			// println("Loc");
			// println(ants[x].get_Pos());
		}
		// for (int x = 0; x < r_img.pixels.length; x++) {
		// int a = (r_img.pixels[x] >> 24) & 0xFF;
		// int r = (r_img.pixels[x] >> 16) & 0xFF;
		// int g = (r_img.pixels[x] >> 8) & 0xFF;
		// int b = r_img.pixels[x] & 0xFF;
		// a -= 5;
		//
		// r_img.pixels[x] = a | r | g | b;
		// }
		r_img.updatePixels();
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { antroad.AntRoad.class.getName() });
	}
}
