
public class World {
	double[] Location = {0, 6711000,0};		//orbit is 340km, same as ISS, radius + orbit
	double[] Velocity = {7656.569225,0,0};		//orbital speed initialized upon startup to be circular based on mass of craft
	double massOfEarth = 5972190000000000000000000.0;
	double radiusOfEarth = 6371000.0;
	double massOfSpacecraft = 1000.0;
	double AreaOfSpaceCraft = 4;
	double G = 0.0000000000667384;		//Gravitational Constant G
	double timeInterval = 0.0001;			//time in seconds between each snapshot. Modeling at steps of less than a second because speeds are in excess of km/s
	
	public World(){
		
	}
	
	//returns the current altitude/location/speed of craft (simulated GPS)
	public double getAltitude(){
		double alt = Math.sqrt(Math.pow(Location[0], 2) 
				+ Math.pow(Location[1], 2) 
				+ Math.pow(Location[2], 2));
		return alt;
	}
	public double[] getLocation(){
		return Location;
	}
	public double[] getVelocity(){
		return Velocity;
	}
	
	//steps the simulation through one unit of time
	public void step(){
		double[] GA = getGravityAccel();
		double[] DA = getDragAccel();
		Velocity[0] += timeInterval * (GA[0] + DA[0]);
		Velocity[1] += timeInterval * (GA[1] + DA[1]);
		Velocity[2] += timeInterval * (GA[2] + DA[2]);
		Location[0] += timeInterval * (Velocity[0]);
		Location[1] += timeInterval * (Velocity[1]);
		Location[2] += timeInterval * (Velocity[2]);
	}

	//Returns acceleration due to gravity as a vector
	public double[] getGravityAccel() {
		double[] GravAccel = new double[3];
		GravAccel[0] = -(G * massOfEarth * Location[0])/(Math.pow(getAltitude(), 3));
		GravAccel[1] = -(G * massOfEarth * Location[1])/(Math.pow(getAltitude(), 3));
		GravAccel[2] = -(G * massOfEarth * Location[2])/(Math.pow(getAltitude(), 3));
		return GravAccel;
	}
	
	private double[] getDragAccel(){
		//still working on all of this, placeholder
		double[] drag = {0.0, 0.0, 0.0};
		
		double speed = Math.sqrt(Math.pow(Velocity[0], 2) 
				+ Math.pow(Velocity[1], 2) 
				+ Math.pow(Velocity[2], 2));
		
		double preasure = 1.4212700405 * Math.exp( -0.0001411008 * (getAltitude() - radiusOfEarth));
		double coeficientOfDrag = 0.82;
		
		
		double[] normalv = {Velocity[0]/speed, Velocity[1]/speed, Velocity[2]/speed};
		double fdrag = -.5 * preasure * Math.pow(speed, 2) * coeficientOfDrag * AreaOfSpaceCraft;
		
		//System.out.println(preasure +" "+ getAltitude());
		
		drag[0] = (fdrag * normalv[0])/massOfSpacecraft;
		drag[1] = (fdrag * normalv[1])/massOfSpacecraft;
		drag[2] = (fdrag * normalv[2])/massOfSpacecraft;
		return drag;
	}
	
}
