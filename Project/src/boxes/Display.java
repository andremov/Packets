/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxes;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andres
 */
public class Display extends Canvas implements Runnable {
    
	
	private Color color(double h, double s, double b) {
		return Color.getHSBColor((float)(h/360f),(float)(s/100f),(float)(b/100f));
	}
	
	@Override
	public void run() {
		createBufferStrategy(2);
		setBackground(color(84.3, 60.6, 92.5));
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) { Handler.stageAction(e.getX(), e.getY()); }
			@Override
			public void mousePressed(MouseEvent e) { }
			@Override
			public void mouseReleased(MouseEvent e) { }
			@Override
			public void mouseEntered(MouseEvent e) { }
			@Override
			public void mouseExited(MouseEvent e) { }
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent me) { }
			@Override
			public void mouseMoved(MouseEvent me) { Handler.mouse(me.getX(),me.getY()); }
		});
		
		while(true){
			Graphics g = getBufferStrategy().getDrawGraphics();
			
			g.clearRect(0, 0, Window.WINDOW_X, Window.WINDOW_Y);

			for (int i = 0; i < Handler.NUM_PLACES; i++) {
				for (int j = 0; j < Handler.places.get(i).getConnections().size(); j++) {
					if (Handler.places.get(i).getConnections().get(j).isDirect()) {
						int foreignIndex = Handler.places.get(i).getConnections().get(j).getGoal();
						if (Handler.places.get(foreignIndex).isLocated() && Handler.places.get(i).isLocated()) {
							int x1 = Handler.places.get(i).getCenterX();
							int y1 = Handler.places.get(i).getCenterY();
							int x2 = Handler.places.get(foreignIndex).getCenterX();
							int y2 = Handler.places.get(foreignIndex).getCenterY();
							g.drawLine(x1, y1, x2, y2);	
						}
					}
				}
			}

			g.setColor(Color.black);
			for (int i = 0; i < Handler.NUM_PLACES; i++) {
				BufferedImage img = Handler.places.get(i).getImage();
				int x = Handler.places.get(i).getX();
				int y = Handler.places.get(i).getY();

				g.drawImage(img, x, y, null);
			}

			if (Handler.mouseX != -200 && Handler.mouseY != -200 && Handler.currentStage == Handler.STAGE_LOCATE_PLACES) {
				g.setColor(Color.black);
				g.drawRect(Handler.mouseX-35, Handler.mouseY-35, 70, 70);
			}

			g.setFont(new Font("Arial",Font.BOLD,20));
			g.drawString(Handler.getStageMessage(), 10, getHeight()-35);

			getBufferStrategy().show();
			
			try {
				Thread.sleep(100);
			} catch(Exception e) { }
		}
	}
	
}
