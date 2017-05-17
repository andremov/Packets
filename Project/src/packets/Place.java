/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andres
 */
public class Place {
	
	public static int STATE_EMPTY = 0;
	public static int STATE_PERSON = 1;
	public static int STATE_PACKET = 2;
	public static int STATE_BOTH = 2;
	
	private final static int DELTA_X = 37;
	private final static int DELTA_Y = 29;
	
	private String name;
	private int[] costs;
	private int state;
	private int fontSize;
	private int fontX;
	private int fontY;
	private int x;
	private int y;
	private boolean taken;
	private int id;

	public Place(int id, String name) {
		this.name = name;
		this.id = id;
		this.costs = new int[Data.PLACE_LIST.size()];
		for (int i = 0; i < Data.PLACE_LIST.size(); i++) {
			this.costs[i] = -1;
		}
		
		this.x = -200;
		this.y = -200;
		this.state = STATE_EMPTY;
		
		fontSize = 40;
		Font font = new Font("Arial",Font.PLAIN,fontSize);
		Graphics g = new BufferedImage(300, 250, BufferedImage.TYPE_INT_ARGB).getGraphics();
		java.awt.FontMetrics metrics = g.getFontMetrics(font);
		while (60 - metrics.stringWidth(this.name) < 0 || 20 - metrics.getHeight() < 0) {
			fontSize--;
			if (fontSize <= 12) {
				fontSize = 40;
				this.name = this.name.substring(0, this.name.length()-1);
			}
			font = new Font("Arial",Font.PLAIN,fontSize);
			metrics = g.getFontMetrics(font);
		}
		fontX = (60 - metrics.stringWidth(this.name)) / 2;
		fontY = ((20 - metrics.getHeight()) / 2) + metrics.getAscent();
		
	}
	
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(60, 70, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		g.setColor(Color.white);
		g.fillOval(5, 0, 50, 50);
		
        if (state == STATE_PACKET) {
			g.drawImage(Data.PLACE_PACKET, 6, 1, 48, 48, null);
		} else if (state == STATE_PERSON) {
			g.drawImage(Data.PLACE_PERSON, 6, 1, 48, 48, null);
		} else if (state == STATE_BOTH) {
			g.drawImage(Data.PLACE_BOTH, 6, 1, 48, 48, null);
		} else if (state == STATE_EMPTY) {
			g.drawImage(Data.PLACE_EMPTY, 6, 1, 48, 48, null);
		}
		g.setColor(Color.black);
		g.fillRect(0, 50, 60, 20);
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.PLAIN,fontSize));
		g.drawString(this.name, fontX, 50+fontY);
		
		return image;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the x
	 */
	public int getCenterX() {
		return x+DELTA_X;
	}
	
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x-DELTA_X;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @return the y
	 */
	public int getCenterY() {
		return y+DELTA_Y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y-DELTA_Y;
	}
	
}
