
public class Agent {
	private static boolean hasDeorbited = false;
	double radians;
	World w;
	
	
	public Agent(World _w, double _radians){
		w = _w;
		radians = _radians;
	}
	
	//returns the estimated coordinates of touchdown
	public double[] predictLanding(World testw){
		World tempworld = testw;
		tempworld.Velocity[0] -= 100;
		int i = 0;
		
		double alt = tempworld.getAltitude() - 6371000.0;
		while(alt > 0){
			for(int k = 0; k < 5000; k++){
				if(alt > 0){
					tempworld.step();
				}
				alt = tempworld.getAltitude() - 6371000.0;
				i++;
			}
		}
		//System.out.println("Predicted landing at " + tempworld.Location[0] + " " + tempworld.Location[1] + " " + tempworld.Location[2]);
		
		double[] ret = new double[]{tempworld.getLocation()[0], tempworld.getLocation()[1], 
									tempworld.getLocation()[2], Math.acos(tempworld.Location[1]/tempworld.getAltitude())};
		return ret;
	}
	
	//calls to transform velocity in correct direction to allow for landing
	//This is the flagship method of the whole project
	private void adjustCourse(){
		if(!hasDeorbited){
			if((2.3169743386973476 + Math.acos(w.Location[1]/w.getAltitude()) - radians) > 0){
				w.Velocity[0] = w.Velocity[0] * 0.987024057388;
				w.Velocity[1] = w.Velocity[1] * 0.987024057388;
				w.Velocity[2] = w.Velocity[2] * 0.987024057388;
				System.out.println("deorbiting");
				hasDeorbited = true;
			}
		}
		else if((w.getAltitude() - 6371000.0 )< 20000){
			double[] landing = predictLanding(new World(w.getLocation().clone(), w.getVelocity().clone(), .1));
			double thetaY = Math.asin(w.Velocity[1]/w.getSpeed());
			double thetaz = Math.atan(w.Velocity[0]/w.Velocity[2]);
			
			//System.out.println(landing[2]);
			double sinY = Math.sin(thetaY);
			double sinZ = Math.sin(thetaz);
			
			double cosY = Math.cos(thetaY);
			double cosZ = Math.cos(thetaz);
		
			double theta1 = 0;
			if(landing[2] < -1){
				//System.out.println("Turning Right");
				theta1 = .1;
				double sin1 = Math.sin(theta1);
				double cos1 = Math.cos(theta1);
				
				
				
				
				double[] vtemp = new double[3];
				vtemp[0] = (w.Velocity[0] * cos1 * cosZ * cosY) + (w.Velocity[2] * sinY * sin1);
				vtemp[1] = (w.Velocity[0] * cos1 * sinZ);
				vtemp[2] = (w.Velocity[0] * cos1 * sinY * cosZ) + (w.Velocity[2] * sinY * sin1);
				
				//System.out.println(vtemp[2]);
				
				//w.Velocity = vtemp;
			}
			else if(landing[2] > 1500){
				System.out.println("Turning Left");
				theta1 = -.1;
				double sin1 = Math.sin(theta1);
				double cos1 = Math.cos(theta1);
				
				double[] vtemp = new double[3];
				vtemp[0] = (w.Velocity[0] * cos1 * cosZ * cosY) + (w.Velocity[2] * sinY * sin1);
				vtemp[1] = (w.Velocity[0] * cos1 * sinZ);
				vtemp[2] = (w.Velocity[0] * cos1 * sinY * cosZ) + (w.Velocity[2] * sinY * sin1);
				
				w.Velocity = vtemp;
			}
			
			
			
			
		}
	}
	
	//Main function call, agent predicts landing, compares it to its destination, and attempts necessary adjustment
	public void operate(){
		
		adjustCourse();
		
	}
	
	
	
}
