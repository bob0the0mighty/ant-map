package antroad;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class AntRoad extends PApplet {

	private PImage				i_img, r_img;
	private final String	img_loc	= "../Eupa-third-managed.png";
	private int						num_ants;
	private List<Ant>			ants;
	private final int			seed		= 8465548;
	private final Point		home		= new Point(949, 889);
	private int						turns;

	@Override
	public void setup() {
		size(1920, 1200);
		i_img = loadImage(img_loc);
		i_img.loadPixels();
		r_img = createImage(1920, 1200, ARGB);
		r_img.loadPixels();
		i_img.pixels[home.y * i_img.width + home.x] = 0xFFFFFFFF;

		turns = 1;

		for (int pix : i_img.pixels) {
			if (pix == 0xFFFF33E9)
				num_ants += 1;
		}

		ants = new ArrayList<Ant>(num_ants);

		for (int x = 0; x < num_ants; x++) {
			ants.add(new Ant(home.x, home.y, this, i_img, r_img, seed + x));
		}
		println(num_ants);
	}

	@Override
	public void draw() {
		image(i_img, 0, 0, width, height);
		image(r_img, 0, 0);
		for (Ant a : ants) {
			int next_move = a.move();
			r_img.pixels[next_move] = a.getColor();// 0x7FFF0000;
			fill(a.getColor());
			if (a.found_port() && next_move == a.get_port()) {
				i_img.pixels[next_move] = 0xFFFFFFFF;
			}
			ellipse(a.cur_x, a.cur_y, 5, 5);
		}

		if (turns % 11 == 0) {
			for (int x = 0; x < r_img.pixels.length; x++) {
				int a = (r_img.pixels[x] >> 24) & 0xFF;
				int r = (r_img.pixels[x] >> 16) & 0xFF;
				int g = (r_img.pixels[x] >> 8) & 0xFF;
				int b = r_img.pixels[x] & 0xFF;
				if (r > 0) {
					r_img.pixels[x] = color(r, g, b, a - 1);
				}
			}
			turns = 1;
		}
		r_img.updatePixels();
		turns += 1;
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { antroad.AntRoad.class.getName() });
	}
}
