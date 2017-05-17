/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxes;

import javax.swing.JFrame;

/**
 *
 * @author Andres
 */
public class Window extends JFrame {

	public static int CANVAS_X = 8;
	public static int CANVAS_Y = 31;
	public static int WINDOW_X = 800;
	public static int WINDOW_Y = 800;
	
	static Display screen;
	
	public Window() {
		setLayout(null);
		setSize(WINDOW_X+CANVAS_X,WINDOW_Y+CANVAS_Y);
		setLocationRelativeTo(null);
		setTitle("Cajas");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		init();
		
		setVisible(true);
		
		new Thread(screen).start();
	}
	
	private void init() {
		screen = new Display();
		screen.setSize(WINDOW_X,WINDOW_Y);
		screen.setLocation(1,1);
		add(screen);
	}
	
}
