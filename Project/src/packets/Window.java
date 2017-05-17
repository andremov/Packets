/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packets;

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
	static Place[] cities;
	static int numCities;
	static int totalCost;
	
	public Window() {
		setLayout(null);
		setSize(WINDOW_X+CANVAS_X,WINDOW_Y+CANVAS_Y);
		setLocationRelativeTo(null);
		setTitle("Aeropuertos [AMovilla & AVasquez]");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		init();
		
		setVisible(true);
		
		new Thread(screen).start();
	}
	
	public static void setCities(Place[] pCities) {
		if (cities != null) {
			for (int i = 0; i < Integer.min(pCities.length, cities.length); i++) {
				pCities[i].setX(cities[i].getCenterX());
				pCities[i].setY(cities[i].getCenterY());
			}
		}
		cities = pCities;
	}
	
	public static void addCity(int x, int y) {
		if (cities != null) {
			cities[numCities%cities.length].setX(x);
			cities[numCities%cities.length].setY(y);
			numCities++;
		}
	}
	
	public static void solve() {
//		int[] possibleConnections = new int[cities.length];
		System.out.println("Starting...");
		boolean solved = false;
		while(!solved){
			boolean changed = true;
			while (changed) {
//				changed = case1();
			}

//			changed = case3();
//			changed = changed || case4();

			solved = !changed;
		}
		System.out.println("Done!");
                
		totalCost = 0;
//		for (int i = 0; i < cities.length; i++) {
//			if (cities[i].isAirport()) {
//				totalCost = totalCost + cities[i].getAirportCost();
//				for (int j = 0; j < cities.length; j++) {
//					if (cities[i].getConnection(j)){
//						totalCost = totalCost + cities[i].getTravelCost(j);
//					}
//				}
//			}
//		}
	}
	
//	private static int numValidWays(int index) {
//		int validWays = 0;
//		for (int i = 0; i < cities.length; i++) {
//			if (cities[index].isValidConnection(i)) {
//				if (cities[index].getTravelCost(i) < cities[index].getAirportCost()) {
//					validWays++;
//				}
//			}
//		}
//		return validWays;
//	}
	
//	private static boolean[][] getCheapestOptions(int index) {
//		int cheapestRoad = -1;
//		boolean[][] options = new boolean[cities.length][cities.length];
//		int curOption = 0;
//		for (int i = 0; i < cities.length; i++) {
//			if (cities[index].isValidConnection(i) && !cities[index].isConnected()) {
//				int travelCost = cities[index].getTravelCost(i);
//				if (!cities[i].isAirport()) {
//					// airport needed
//					if (cities[index].getAirportCost() < cities[i].getAirportCost()) {
//						travelCost = travelCost + cities[index].getAirportCost();
//					} else {
//						travelCost = travelCost + cities[i].getAirportCost();
//					}
//				} else {
//					// no airport needed
//					if (travelCost < cheapestRoad || cheapestRoad == -1) {
//						curOption = 0;
//					}
//					
//					for (int k = 0; k < cities.length; k++) {
//						options[curOption][k] = false;
//					}
//					
//					options[curOption][i] = true;
//					curOption++;
//					cheapestRoad = cities[index].getTravelCost(i);
//				}
//			}
//		}
//		return options;
//	}
	
//	private static boolean case1() {
//		boolean changed = false;
//		for (int i = 0; i < cities.length; i++) {
//			if (!cities[i].isDone()) {
//				int validWays = numValidWays(i);
//				if (validWays == 0) {
//					// SI TODAS LAS CARRETERAS DE UNA CIUDAD
//					// CUESTAN MAS QUE UN AEROPUERTO, SE PONE
//					// UN AEROPUERTO
//					cities[i].setState(Place.STATE_PACKET);
//					System.out.println("CASE 1: "+cities[i].getName()+" is now an airport!");
//					changed = true;
//				}
//			}
//		}
//		return changed;
//	}
	
//	private static boolean case3() {
//		boolean changed = false;
//		for (int i = 0; i < cities.length; i++) {
//			if (!cities[i].isDone()) {
//				int cheapestRoad = -1;
//				boolean[] areCheapest = new boolean[cities.length];
//				for (int j = 0; j < cities.length; j++) {
//					if (cities[i].isValidConnection(j) && !cities[j].isConnected()) {
//						int travelCost = cities[i].getTravelCost(j);
//						if (!cities[j].isAirport()) {
//							travelCost = travelCost + cities[j].getAirportCost();
//						}
//						if (travelCost < cheapestRoad || cheapestRoad == -1) {
//							for (int k = 0; k < cities.length; k++) {
//								areCheapest[k] = false;
//							}
//							areCheapest[j] = true;
//							cheapestRoad = cities[i].getTravelCost(j);
//						} else if (cities[i].getTravelCost(j) == cheapestRoad) {
//							areCheapest[j] = true;
//						}
//					}
//				}
//				int airportIndex = -1;
//				int j = 0;
//				while (j < cities.length && airportIndex == -1) {
//					if (areCheapest[j] && cities[j].isAirport()) {
//						airportIndex = j;
//					}
//					j++;
//				}
//				if (airportIndex != -1) {
//					// SI LA CARRETERA MAS BARATA SE CONECTA
//					// CON UNA CIUDAD QUE YA TIENE AEROPUERTO,
//					// SE PONE ESA CARRETERA
//					cities[i].setConnection(airportIndex,true);
//					cities[airportIndex].setConnection(i,true);
//					cities[i].setState(Place.STATE_PERSON);
//					System.out.println("CASE 3: "+cities[i].getName()+" is now connected!");
//					changed = true;
//				}
//			}
//		}
//		return changed;
//	}
//	
//	private static boolean case4() {
//		boolean changed = false;
//		int highestSave = -1;
//		int lowestIndex = -1;
//		for (int i = 0; i < cities.length; i++) {
//			if (!cities[i].isDone()) {
//				int sum = 0;
//                int totalAirportCost = 0;
//				for (int j = 0; j < cities.length; j++) {
//					if (!cities[j].isDone() && cities[i].isValidConnection(j)) {
//						sum = sum + cities[i].getTravelCost(j);
//                        totalAirportCost = totalAirportCost + cities[j].getAirportCost();
//					}
//				}
//				if (totalAirportCost-sum > highestSave || highestSave == -1) {
//					lowestIndex = i;
//					highestSave = totalAirportCost-sum;
//					changed = true; 
//				}
//			}
//		}
//		if (changed) {
//			cities[lowestIndex].setState(Place.STATE_PACKET);
//			System.out.println("CASE 4: "+cities[lowestIndex].getName()+" is now an airport!");
//		}
//		return changed;
//	}
	
	private void init() {
		numCities = 0;
		totalCost = 0;
                
		screen = new Display();
		screen.setSize(WINDOW_X,WINDOW_Y);
		screen.setLocation(1,1);
		add(screen);
		
	}
	
}
