package antroad;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

public class AntRoad extends PApplet {

	private PImage				i_img, r_img;
	private final String	img_loc		= "../Eupa-third-managed.png";
	private final int			num_ants	= 1;
	private final Ant[]		ants			= new Ant[num_ants];
	private final Random	rng				= new Random(846548);

	private enum Colors { // very simplistic biome selection
		FF5A8552, // Decid forests
		FFFF33E9, // cities
		FF8BAA5E, // Conif forests
		FF8EA678, // moist grasslands
		FFC4CE9C, // arid grasslands
		FF657453, // moist hills
		FF56594c, // arid hills
		FF2F628F, // water
		FF86A3AB// ditto
	};

	@Override
	public void setup() {
		size(1920, 1200);
		i_img = loadImage(img_loc);
		i_img.loadPixels();
		r_img = createImage(1920, 1200, ARGB);
		r_img.loadPixels();
		ants[0] = new Ant(949, 889, i_img, r_img, rng);
	}

	@Override
	public void draw() {
		image(i_img, 0, 0, width, height);
		image(r_img, 0, 0);
		for (int x = 0; x < ants.length; x++) {
			// println("Home ");
			// println(home);
			int[] trail = ants[x].move();
			r_img.pixels[trail[1] * width + trail[0]] = 0x7F000000;
			println(hex(i_img.pixels[trail[1] * width + trail[0]]));
			// println("Loc");
			// println(ants[x].get_Pos());
		}
		r_img.updatePixels();
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { antroad.AntRoad.class.getName() });
	}
}
