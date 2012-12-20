package antroad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

public class Ant {
	public int						cur_x, cur_y;
	private final int			home_x, home_y;
	private int						port_x, port_y;
	private int						dx, dy;
	private final PApplet	applet;
	private final PImage	init, road;
	private final Random	rng;
	private int						alpha;
	private final boolean	to_home;

	public Ant(int init_x, int init_y, PApplet apl, PImage i, PImage r, int seed) {
		rng = new Random(seed);
		cur_x = home_x = init_x;
		cur_y = home_y = init_y;
		init = i;
		road = r;
		applet = apl;
		alpha = 100;
		to_home = false;
	}

	public int getColor() {
		return applet.color(255, 0, 0, alpha);
	}

	public int get_Pos() {
		return cur_y * init.width + cur_x;
	}

	public int get_Home() {
		return home_y * init.width + home_x;
	}

	public int get_port() {
		return port_y * init.width + port_x;
	}

	public boolean found_port() {
		return (port_x > 0);
	}

	public int move() {
		int[] temp = choose_direction();
		dx = temp[0];
		dy = temp[1];
		cur_x += dx;
		cur_y += dy;

		if (PApplet.hex(init.pixels[get_Pos()]).equals("FFFF33E9")) {// found city
			port_x = cur_x;
			port_y = cur_y;
			alpha = 200;
		}

		return get_Pos();
	}

	private int[] choose_direction() {

		int[][] around = getLocal();// look around ant
		int[] point = new int[] { cur_x, cur_y };

		List<List<int[]>> categories = categorizeArea(around);
		boolean ahead = false;

		if (!found_port()) { // searching for port from home

			for (int[] direction : categories.get(0)) {
				if (direction[0] == (cur_x + dx) && direction[1] == (cur_y + dy)
						&& rng.nextFloat() < 0.9) {
					point = new int[] { cur_x + dx, cur_y + dy };
					ahead = true;
					break;
				}
			}
			if (!ahead) {
				point = categories.get(0).get(rng.nextInt(categories.get(0).size()));
				int level = 1;
				while (rng.nextFloat() > 0.55 && level != categories.size()) {
					point = categories.get(level).get(
							rng.nextInt(categories.get(level).size()));
				}
			}
		} else if (to_home) {

			point = categories.get(0).get(rng.nextInt(categories.get(0).size()));
			int level = 1;
			while (rng.nextFloat() > 0.55 && level != categories.size()) {
				point = categories.get(level).get(
						rng.nextInt(categories.get(level).size()));
			}
		} else {

		}
		int[] temp = { (point[0] - cur_x), (point[1] - cur_y) };
		// System.out.println(temp[0] + " " + temp[1]);
		return temp;
	}

	private List<List<int[]>> categorizeArea(int[][] area) {
		List<List<int[]>> categorized = new ArrayList<List<int[]>>();

		for (int i = 0; i < Color.values().length * 2; i++) {// add categories for
			categorized.add(new ArrayList<int[]>()); // colors
		}

		for (int[] point : area) { // fill each category points that have that color
			try {
				int pos = point[1] * init.width + point[0];
				Color c = Color.valueOf(PApplet.hex(init.pixels[pos]));
				if (to_home) {
					if (road.pixels[pos] != 0) {// if visited
						categorized.get(0).add(point);
					} else {// if not visited
						categorized.get(Color.values().length + c.ordinal()).add(point);
					}
				} else {
					if (road.pixels[pos] == 0) {// if not visited
						categorized.get(c.ordinal()).add(point);
					} else {// if visited
						categorized.get(Color.values().length + c.ordinal()).add(point);
					}
				}
			} catch (IllegalArgumentException ex) {
				// System.out.println(PApplet.hex(init.pixels[point[1] * init.width
				// + point[0]]));
			}
		}

		for (int i = 0; i < categorized.size(); i++) {
			if (categorized.get(i).isEmpty()) { // remove empty categories
				categorized.remove(categorized.get(i));
				i -= 1;// account for changed size
			}
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