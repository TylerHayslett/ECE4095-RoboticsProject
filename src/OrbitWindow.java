import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class OrbitWindow extends JPanel {
	boolean first = true;
	double[][] point;
	int numsteps;
	
	public OrbitWindow(int _numsteps){
		super();
        this.setOpaque(true);
        numsteps = _numsteps;
        point = new double[numsteps][3];
	}
	
	//Standard boilerplate inherited method
	public void paint (Graphics g) {
		earth(g);
		pointpaint(g);
    }
	
	private void pointpaint(Graphics g) {
		if(point[0] != null){
			for(int i = 0; i < numsteps; i++){
				//convert coordinates of sim into those of display
				int x = (((int) point[i][0])/25484) + 450;
				int y = (((int) point[i][1])/25484) + 450;
				
				//draw a point for each one
				g.drawOval(x, y, 1, 1);
			}
		}
	}

	//Paints a big green ball to represent earth (to scale)
	private void earth(Graphics g){
		g.setColor(Color.green);
		g.fillOval(200, 200, 500, 500);
		g.setColor(Color.black);
		g.drawOval(200, 200, 500, 500);
	}

	//Gets point array, then repaints to add them in
	public void plotpoints(double[][] points) {
		point = points;
		this.repaint();
	}
}
