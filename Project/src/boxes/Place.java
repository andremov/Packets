/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Andres
 */
public class Place {
	
	private final static int DELTA_X = 35;
	private final static int DELTA_Y = 35;
	
	private String name;
	private ArrayList<Path> connections;
        
	private int fontSize;
	private int fontX;
	private int fontY;
        
	private int x;
	private int y;
	private boolean box;
	private boolean taken;
	private int id;

	public Place(int id, String name, ArrayList<Path> connections) {
		this.name = name;
		this.id = id;
		this.connections = connections;
		
		this.x = -200;
		this.y = -200;
		
		fontSize = 40;
		Font font = new Font("Arial",Font.PLAIN,fontSize);
		Graphics g = new BufferedImage(300, 250, BufferedImage.TYPE_INT_ARGB).getGraphics();
		java.awt.FontMetrics metrics = g.getFontMetrics(font);
		while (70 - metrics.stringWidth(this.name) < 0 || 20 - metrics.getHeight() < 0) {
			fontSize--;
			font = new Font("Arial",Font.PLAIN,fontSize);
			metrics = g.getFontMetrics(font);
		}
		fontX = (70 - metrics.stringWidth(this.name)) / 2;
		fontY = ((20 - metrics.getHeight()) / 2) + metrics.getAscent();
	}
        
	public void takeBox() {
		this.taken = true;
	}

	public boolean isContained(int x, int y) {
		return (this.x <= x && this.x+70 >= x && this.y <= y && this.y+70 >= y);
	}
	
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		g.setColor(Color.black);
		g.drawRect(0, 0, 69, 69);
		
		BufferedImage img;
		if (Handler.hasPerson(id) && isBox()) {
			img = Data.PLACE_BOTH;
		} else if (Handler.hasPerson(id)) {
			img = Data.PLACE_PERSON;
		} else if (isBox()) {
			img = Data.PLACE_PACKET;
		} else {
			img = Data.PLACE_EMPTY;
		}
			
		g.drawImage(img,10,0,50,50,null);
             
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.PLAIN,fontSize));
		g.drawString(this.name, fontX, 50+fontY);
		
		return image;
	}

	public boolean isLocated() {
		return !(this.x == -200 && this.y == -200); 
	}
        
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	public void setPos(int x, int y) {
		this.x = x-DELTA_X;
		this.y = y-DELTA_Y;
	}

	public double getCostTo(int id) {
		double value = 0.0;
		
		for (int i = 0; i < getConnections().size(); i++) {
			if (getConnections().get(i).getGoal() == id) {
				value = getConnections().get(i).getTotal();
			}
		}
		
		return value;
	}

	public Path getPathTo(int id) {
		Path path = null;
		
		for (int i = 0; i < getConnections().size(); i++) {
			if (getConnections().get(i).getGoal() == id) {
				path = getConnections().get(i);
			}
		}
		
		return path;
	}
        
    /**
     * @return the connections
     */
    public ArrayList<Path> getConnections() {
        return connections;
    }

    /**
     * @param connections the connections to set
     */
    public void setConnections(ArrayList<Path> connections) {
        this.connections = connections;
    }

    /**
     * @param hasbox the box to set
     */
    public void setBox(boolean hasbox) {
        this.box = hasbox;
    }

    /**
     * @param taken the taken to set
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    /**
     * @return the box
     */
    public boolean isBox() {
        return box;
    }

    /**
     * @return the taken
     */
    public boolean isTaken() {
        return taken;
    }
}
