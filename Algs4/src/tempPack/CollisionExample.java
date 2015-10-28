package tempPack;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.*;

public class CollisionExample {

	public static void main(String[] args) {

		Scanner in = null;
		try {
			in = new Scanner(new File("C:\\Users\\Pavel_Fedorov\\Downloads\\Libs\\diff.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		StdDraw.setCanvasSize(800, 800);

		// remove the border
		// StdDraw.setXscale(1.0/22.0, 21.0/22.0);
		// StdDraw.setYscale(1.0/22.0, 21.0/22.0);

		// turn on animation mode
		StdDraw.show(0);

		// the array of particles
		Particle[] particles;

		int N = in.nextInt();
		particles = new Particle[N];
		for (int i = 0; i < N; i++) {
			double rx = in.nextDouble();
			double ry = in.nextDouble();
			double vx = in.nextDouble();
			double vy = in.nextDouble();
			double radius = in.nextDouble();
			double mass = in.nextDouble();
			int r = in.nextInt();
			int g = in.nextInt();
			int b = in.nextInt();
			Color color = new Color(r, g, b);
			particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
		}

		// create collision system and simulate
		CollisionSystem system = new CollisionSystem(particles);
		system.simulate(10000);

	}
}
