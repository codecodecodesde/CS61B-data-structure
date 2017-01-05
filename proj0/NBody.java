public class NBody {
	public static double readRadius(String fileName) {
		In in = new In(fileName);
		int n = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

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
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		double univR = readRadius(filename);
		//System.out.print(univR);
		Planet[] univPlanets = readPlanets(filename);
		//System.out.print(univPlanets[2].imgFileName);

		String background = "images/starfield.jpg";
		StdDraw.setScale(-univR, univR);
		StdDraw.clear();
		StdDraw.picture(0, 0, background);

		for (int i = 0; i < univPlanets.length; i++) {
			univPlanets[i].draw();
		}

		int waitTimeMilliseconds = 30;
		StdAudio.play("audio/HIP_HOP.MID");
		for (double t = 0; t < T; t = t + dt) {
			double[] xForces = new double[univPlanets.length];
			double[] yForces = new double[univPlanets.length];

			for (int i = 0; i < univPlanets.length; i++) {
				xForces[i] = univPlanets[i].calcNetForceExertedByX(univPlanets);
				yForces[i] = univPlanets[i].calcNetForceExertedByY(univPlanets);
			}

			for (int i = 0; i < univPlanets.length; i++) {
				univPlanets[i].update(dt, xForces[i], yForces[i]);
			}

			StdDraw.picture(0, 0, background);
			for (int i = 0; i < univPlanets.length; i++) {
				univPlanets[i].draw();
			}
			StdDraw.show(waitTimeMilliseconds);
		}
		StdAudio.close();
		StdOut.printf("%d\n", univPlanets.length);
		StdOut.printf("%.2e\n",univR);
		for (int i = 0; i < univPlanets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					univPlanets[i].xxPos, univPlanets[i].yyPos, univPlanets[i].xxVel, univPlanets[i].yyVel, univPlanets[i].mass, univPlanets[i].imgFileName);
		}
	}
}