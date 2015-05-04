import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;




public class Main{
	static int numsteps = 15000;
	static double[][] points = new double[numsteps][3];
	
	public static void main(String[] args) {
		
		//sets up window
		JFrame frame = new JFrame("Orbit Window");
		//adds orbit printout
		OrbitWindow o = new OrbitWindow(numsteps);
		frame.setSize(1000, 1000);
		frame.getContentPane().add(o, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		o.setFocusable(true);
		
		//runs actual simulation
		runSimDeorbit();
		
		o.plotpoints(points);
	}
	
	private static void runSim() {
		
	}
	
	//runs sim, agent de-orbits at right time to land on target area, target given in radians
	public static void runSimDeorbit(){
		//Create world, start simulation, run until ground impact
		World w = new World(new double[]{0.0, 0.0, 0.0, 16000, 7000, 0.0});
		
		//new agent, radian distance to land at from starting point
		Agent a = new Agent(w, 5.3169743386973476);
		
		int i = 0;
		double alt = w.getAltitude() - 6371000.0;
		
		while(alt > 0){
			for(int k = 0; k < 5000; k++){
				if(alt > 0){
					w.step();
				}
				alt = w.getAltitude() - 6371000.0;
			}
			points[i] = w.getLocation().clone();
			i++;
			a.operate();
		}
		System.out.println("Landed at " + w.Location[0] + " " + w.Location[1] + " " + w.Location[2]);
		System.out.println("Speed on landing was " + w.getSpeed());
		
	}
	
	//No agent, Purely for demonstrating effect wind has on trajectory, Max jetstream speed for max altitude range
	public static void runSimWind(){
		
		//Create world, start simulation, run until ground impact
		World w = new World(new double[]{0.0, 0.0, 115.75, 16000, 7000, 0.0});
		w.Velocity[0] = 7606.569225;
		
		int i = 0;
		double alt = w.getAltitude() - 6371000.0;
		while(alt > 0){
			for(int k = 0; k < 5000; k++){
				if(alt > 0){
					w.step();
				}
				alt = w.getAltitude() - 6371000.0;
			}
			points[i] = w.getLocation().clone();
			i++;
		}
		System.out.println("Landed at " + w.Location[0] + " " + w.Location[1] + " " + w.Location[2]);
		
	}
	
	//Used for Drawing maps of drag to downrange distance.
	public static void testLoad(){
		double maxAccel = 0;
		for(int i = 0; i < 1000; i++){
			maxAccel = 0;
			World w = new World(7606.569225 - i);
			double alt = w.getAltitude() - 6371000.0;
			while(alt > 0){
				double tempAccel = w.step();
				if(tempAccel > maxAccel){
					maxAccel = tempAccel;
				}
				alt = w.getAltitude() - 6371000.0;
			}
			System.out.println(Math.acos(w.Location[1]/w.getAltitude()));
		}
	}
}
