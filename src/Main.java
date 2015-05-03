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
		OrbitWindow o = new OrbitWindow(numsteps);
		frame.setSize(1000, 1000);
		//adds orbit printout
		frame.getContentPane().add(o, BorderLayout.CENTER);
		//frame.pack();
		frame.setVisible(true);
		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		o.setFocusable(true);
		
		runSimDeorbit();
		//runSimWind();
		
		//array to send to orbit window with points in it
		o.plotpoints(points);
		//System.out.println("done");
	}
	
	private static void runSim() {
		
	}
	public static void runSimDeorbit(){
		
		//Create world, start simulation, run until ground impact
		World w = new World(new double[]{0.0, 0.0, 0.0, 16000, 7000, 0.0});
		w.Velocity[0] = 7706.569225;
		Agent a = new Agent(w, 5.3169743386973476);
		
		a.operate();
		
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
			//System.out.println(w.getSpeed());
		}
		//System.out.println(alt);
		//System.out.println(i);
		System.out.println("Landed at " + w.Location[0] + " " + w.Location[1] + " " + w.Location[2]);
		System.out.println(Math.acos(w.Location[1]/w.getAltitude()));
		System.out.println(w.getSpeed());
		
	}
	public static void runSimWind(){
		
		//Create world, start simulation, run until ground impact
		World w = new World(new double[]{0.0, 0.0, 115.75, 16000, 7000, 0.0});
		int i = 0;
		Agent a = new Agent(w, 2.6169743386973476);
		
		//a.operate();
		
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
			//a.operate();
		}
		//System.out.println(alt);
		//System.out.println(i);
		System.out.println("Landed at " + w.Location[0] + " " + w.Location[1] + " " + w.Location[2]);
		
	}
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
