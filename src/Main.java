import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;




public class Main{
	public static void main(String[] args) {
		int numsteps = 5000;
		//sets up window
		JFrame frame = new JFrame("Orbit Window");
		OrbitWindow o = new OrbitWindow(numsteps);
		
		//adds orbit printout
		frame.getContentPane().add(o, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		frame.setBackground(Color.white);
		
		//array to send to orbit window with points in it
		double[][] points = new double[numsteps][3];
		
		//Create world, start simulation
		World w = new World();
		for(int i = 0; i < numsteps; i++){
			for(int k = 0; k < 5000; k++){
				w.step();
			}
			
			points[i] = w.getLocation().clone();
		}
		
		System.out.println("done");
		
		//Print simulation to screen
		o.plotpoints(points);
	}
	
}
