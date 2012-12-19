package antroad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

public class Ant {
	private int	cur_x, cur_y;
	private final int	home_x, home_y;
	private int				port_x, port_y;
	private final PImage	init, road;
	private boolean				from_port;
	private final Random	rng;

	public Ant(int init_x, int init_y, PImage i, PImage r, Random ran) {
		rng = ran;
		cur_x = home_x = init_x;
		cur_y = home_y = init_y;
		init = i;
		road = r;
		init.pixels[home_y * init.width + home_x] = 0xFFFFFFFF;
		from_port = false;
	}

	public int get_Pos() {
		return cur_y * init.width + cur_x;
	}

	public int get_Home() {
		return home_y * init.width + home_x;
	}

	public int move() {
		int[] temp = choose_direction();
		cur_x += temp[0];
		cur_y += temp[1];
		if (PApplet.hex(init.pixels[get_Pos()]).equals("FFFF33E9")) {// found city
			from_port = true;
		}
		return get_Pos();
	}

	private int[] choose_direction() {

		int[][] around = getLocal();// look around ant
		int[] point = new int[] { cur_x, cur_y };

		List<List<int[]>> categories = categorizeArea(around);

		if (!from_port) { // searching for port from home

			point = categories.get(0).get(0);
			int level = 1;
			while (rng.nextFloat() > .50 && level != categories.size()) {
				point = categories.get(level).get(0);
			}

		} else if (from_port) {
			point = new int[] { cur_x, cur_y };
		}
		int[] temp = { (point[0] - cur_x), (point[1] - cur_y) };
		System.out.println(temp[0] + " " + temp[1]);
		return temp;
	}

	private List<List<int[]>> categorizeArea(int[][] area) {
		List<List<int[]>> categorized = new ArrayList<List<int[]>>();

		for (int i = 0; i < Color.values().length; i++) {// add categories for
			categorized.add(new ArrayList<int[]>()); // base colors
		}

		for (int i = 0; i < Color.values().length; i++) {// add categories for
			categorized.add(new ArrayList<int[]>()); // visted colors
		}

		for (int[] point : area) { // fill each category points that have that color
			try {
				int pos = point[1] * init.width + point[0];
				Color c = Color.valueOf(PApplet.hex(init.pixels[pos]));
				if (road.pixels[pos] == 0) {// if not visited
					categorized.get(c.ordinal()).add(point);
				} else {// if visited
					categorized.get(Color.values().length + c.ordinal()).add(point);
				}
			} catch (IllegalArgumentException ex) {

			}
		}

		for (int i = 0; i < categorized.size(); i++) {
			if (categorized.get(i).isEmpty()) { // remove empty categories
				categorized.remove(categorized.get(i));
			} else {
				Collections.shuffle(categorized.get(i), rng);// shuffle non-empty
			}// categories
		}

		return categorized;
	}

	private int[][] getLocal() {
		int[][] temp = new int[8][2];

		temp[0][0] = (cur_x - 1);// top left
		temp[0][1] = (cur_y - 1);
		temp[1][0] = (cur_x); // top
		temp[1][1] = (cur_y - 1);
		temp[2][0] = (cur_x + 1);// top right
		temp[2][1] = (cur_y - 1);
		temp[3][0] = (cur_x - 1); // left
		temp[3][1] = (cur_y);
		temp[4][0] = (cur_x + 1); // right
		temp[4][1] = (cur_y);
		temp[5][0] = (cur_x - 1);// bottom left
		temp[5][1] = (cur_y + 1);
		temp[6][0] = (cur_x); // bottom
		temp[6][1] = (cur_y + 1);
		temp[7][0] = (cur_x + 1);// bottom right
		temp[7][1] = (cur_y + 1);

		return temp;
	}
}