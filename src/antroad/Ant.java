package antroad;

import java.util.Random;

import processing.core.PImage;

public class Ant {
	private int	cur_x, cur_y;
	private final int	home_x, home_y;
	private int				port_x, port_y;
	private final PImage	init, road;
	private final boolean	from_port;
	private boolean				to_port;
	private final Random	rng;

	public Ant(int init_x, int init_y, PImage i, PImage r, Random ran) {
		rng = ran;
		cur_x = home_x = init_x;
		cur_y = home_y = init_y;
		init = i;
		road = r;
		from_port = to_port = false;
	}

	public int[] get_Pos() {
		int[] cur_pos = { cur_x, cur_y };
		return cur_pos;
	}

	public int[] get_Home() {
		int[] home_pos = { home_x, home_y };
		return home_pos;
	}

	public int[] move() {
		int[] temp = choose_direction();
		cur_x += temp[0];
		cur_y += temp[1];
		return get_Pos();
	}

	private int[] choose_direction() {
		if (!from_port && !to_port) { // searching for port from home

		}
		int[] temp = { (rng.nextInt(3) - 1), (rng.nextInt(3) - 1) };
		return temp;
	}

	private int[] look_around() {
		return new int[0];
	}
}