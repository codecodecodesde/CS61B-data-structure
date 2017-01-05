public class Planet{
	double xxPos;//current x position
	double yyPos;//current y position
	double xxVel;//current velocity in x direction
	double yyVel;//current velocity in y direction
	double mass;//its mass
	String imgFileName;

	public Planet(double xP,double yP, double xV, double yV, double m, String img){
		xxPos=xP;
		yyPos=yP;
		xxVel=xV;
		yyVel=yV;
		mass=m;
		imgFileName=img;
	}

	public Planet(Planet p){
		xxPos=p.xxPos;
		yyPos=p.yyPos;
		xxVel=p.xxVel;
		yyVel=p.yyVel;
		mass=p.mass;
		imgFileName=p.imgFileName;
	}

	public double calcDistance(Planet x){
		double dx=x.xxPos-xxPos;
		double dy=x.yyPos-yyPos;
		return Math.sqrt(dx*dx+dy*dy);
	}

	 public double calcForceExertedBy(Planet x){
	 	double G=6.67e-11;
	 	double F=G*mass*x.mass/Math.pow(calcDistance(x),2);
	 	return F;
	 }

	 public double calcForceExertedByX(Planet x){
	 	double alpha=(x.xxPos-xxPos)/calcDistance(x);
	 	return calcForceExertedBy(x)*alpha;
	 }

	 public double calcForceExertedByY(Planet x){
	 	double beta=(x.yyPos-yyPos)/calcDistance(x);
	 	return calcForceExertedBy(x)*beta;
	 }

	 public double calcNetForceExertedByX(Planet[] allPlanets){
	 	double xNetForce=0;
	 	for(int i=0;i<allPlanets.length;i++){
	 		if(!equals(allPlanets[i]))
	 			xNetForce=xNetForce+calcForceExertedByX(allPlanets[i]);
	 	}
	 	return xNetForce;
	 }

	 public double calcNetForceExertedByY(Planet[] allPlanets){
	 	double yNetForce=0;
	 	for(int i=0;i<allPlanets.length;i++){
	 		if(!equals(allPlanets[i]))
	 			yNetForce=yNetForce+calcForceExertedByY(allPlanets[i]);
	 	}
	 	return yNetForce;
	 }

	 public void update(double dt, double fX, double fY){
	 	double ax=fX/mass;
	 	double ay=fY/mass;
	 	xxVel=xxVel+dt*ax;
	 	yyVel=yyVel+dt*ay;
	 	xxPos=xxPos+dt*xxVel;
	 	yyPos=yyPos+dt*yyVel;
	 }
	 public void draw(){
	 	String imgToDraw="images/"+imgFileName;
	 	//System.out.print(imgToDraw);
	 	StdDraw.picture(xxPos,yyPos,imgToDraw);
	 }
}
