public class NBody {
	//radius of universe
	public static double readRadius(String fileName) {
		In in = new In(fileName);
		int n = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	//return an array of Planets corresponding to the planets in the file
	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);
		int N = in.readInt();
		double radius = in.readDouble();
		Planet[] allPlanets = new Planet[N];
		for (int i = 0; i < N; i++) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			Planet newPlanets = new Planet(xP, yP, xV, yV, m, img);
			allPlanets[i] = newPlanets;
		}
		return allPlanets;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);//the amount of T
		double dt = Double.parseDouble(args[1]);//the time interval
		String filename = args[2];

		double univR = readRadius(filename);
		Planet[] univPlanets = readPlanets(filename);

		//draw background
		String background = "images/starfield.jpg";
		StdDraw.setScale(-univR, univR);
		StdDraw.clear();
		StdDraw.picture(0, 0, background);

		//draw all planets
		for (int i = 0; i < univPlanets.length; i++) {
			univPlanets[i].draw();
		}

		//create an animation
		int waitTimeMilliseconds = 30;
		StdAudio.play("audio/HIP_HOP.MID");//loop an audio file in the background
		
		for (double t = 0; t < T; t = t + dt) {
			//net x and y forces for each planet
			double[] xForces = new double[univPlanets.length];
			double[] yForces = new double[univPlanets.length];
			for (int i = 0; i < univPlanets.length; i++) {
				xForces[i] = univPlanets[i].calcNetForceExertedByX(univPlanets);
				yForces[i] = univPlanets[i].calcNetForceExertedByY(univPlanets);
			}
			
			//update each planet's position, velocity, and acceleration.
			for (int i = 0; i < univPlanets.length; i++) {
				univPlanets[i].update(dt, xForces[i], yForces[i]);
			}

			//draw background and all of the planets
			StdDraw.picture(0, 0, background);
			for (int i = 0; i < univPlanets.length; i++) {
				univPlanets[i].draw();
			}

			StdDraw.show(waitTimeMilliseconds);
		}

		//print the universe
		StdOut.printf("%d\n", univPlanets.length);
		StdOut.printf("%.2e\n",univR);
		for (int i = 0; i < univPlanets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					univPlanets[i].xxPos, univPlanets[i].yyPos, univPlanets[i].xxVel, univPlanets[i].yyVel, univPlanets[i].mass, univPlanets[i].imgFileName);
		}
	}
}