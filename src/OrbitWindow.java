import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class OrbitWindow extends JPanel{
	boolean first = true;
	double[][] point;
	int numsteps;
	double theta = 0;//3.14159/2;
	int offsetx = 0;
	int offsety = 0;
	double scalingFactor = 1;
	
	int x, y;
	
	
	public OrbitWindow(int _numsteps){
		super();
        this.setOpaque(true);
        numsteps = _numsteps;
        point = new double[numsteps][3];
        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {}

            public void keyReleased(KeyEvent e) {
            	if(e.getKeyCode() == 37){theta -= 3.14159/16; repaint();} 
            	else if(e.getKeyCode() == 39){ theta += 3.14159/16; repaint();}
            	else if(e.getKeyCode() == 38){ scalingFactor -= .1; repaint();}
            	else if(e.getKeyCode() == 40){ scalingFactor += .1; repaint();}
            }

            public void keyTyped(KeyEvent e) {  }
        });
        this.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() < 0){
					scalingFactor = scalingFactor * 1.1;
				}
				else{
					scalingFactor = scalingFactor / 1.1;
				} 
				repaint();
			}
        });
        this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				offsetx -= x - e.getX();
				offsety -= y - e.getY();
				repaint();
			}
			
        });
	}
	
	//Standard boilerplate inherited method
	public void paint (Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);
		earth(g);
		pointpaint(g);
    }
	
	private void pointpaint(Graphics g) {
		g.setColor(Color.red);
		if(point[0] != null){
			for(int i = 0; i < numsteps; i++){
				//convert coordinates of sim into those of display
				int x = ((int)(((point[i][0] * Math.cos(theta)) + (point[i][2] * Math.sin(theta)))/(20420 / scalingFactor))) + 452 + offsetx;
				int y = -((int) (point[i][1]/(20420 / scalingFactor))) + 452 + offsety;
				
				//draw a point for each one
				g.fillOval(x, y, 2, 2);
				
			}
		}
	}

	//Paints a big green ball to represent earth (to scale)
	private void earth(Graphics g){
		try {
			g.drawImage(ImageIO.read(new File("sphere.png")), (452 + offsetx - ((int)(312 * scalingFactor))), 
																(452 + offsety - ((int)(312 * scalingFactor))), (int)(624 * scalingFactor), (int)(624 * scalingFactor), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("nogo");
		}
	}

	//Gets point array, then repaints to add them in
	public void plotpoints(double[][] points) {
		point = points;
		this.repaint();
	}

	
}
