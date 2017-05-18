/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public abstract class Data {
	
	public static BufferedImage PLACE_EMPTY;
	public static BufferedImage PLACE_PACKET;
	public static BufferedImage PLACE_PERSON;
	public static BufferedImage PLACE_BOTH;
	public static ArrayList<DataEntry> INFO;
	public static ArrayList<String> PLACE_LIST;
		
	public static void load() {
		
		try {
			PLACE_EMPTY = ImageIO.read(new File("assets/place.png"));
			PLACE_PACKET = ImageIO.read(new File("assets/packet.png"));
			PLACE_PERSON = ImageIO.read(new File("assets/person.png"));
			PLACE_BOTH = ImageIO.read(new File("assets/both.png"));
			
			PLACE_LIST = new ArrayList<>();
			INFO = new ArrayList<>();
			File archivo = new File("info.txt");
			
			for (int i = 0; i < Files.readAllLines(archivo.toPath()).size(); i++) {
				String[] line = Files.readAllLines(archivo.toPath()).get(i).split(",");
				addPlace(line[0]);
				addPlace(line[1]);
				INFO.add(new DataEntry(line[0],line[1],Double.parseDouble(line[2])*100));
				INFO.add(new DataEntry(line[1],line[0],Double.parseDouble(line[2])*100));
			}
		
		} catch (IOException ex) { }
		
	}
        
	public static ArrayList<Path> getConnections(int index) {
		String indexName = PLACE_LIST.get(index);
		ArrayList<Path> connections = new ArrayList<>();

		for (int i = 0; i < INFO.size(); i++) {
			if (INFO.get(i).getName().compareTo(indexName) == 0) {
				String connectionName = INFO.get(i).getConnection();
				double cost = INFO.get(i).getCost();
				int connectionIndex = getIndexFor(connectionName);
				ArrayList<PlaceConnection> thisConnection = new ArrayList<>();
				thisConnection.add(new PlaceConnection(connectionIndex, cost));
				connections.add(new Path(thisConnection));
			}
		}
		connections.add(new Path(index));
		
		return connections;
	}
        
	public static int getIndexFor(String cityName) {
		int index = -1;
		
		for (int i = 0; i < PLACE_LIST.size(); i++) {
			if (PLACE_LIST.get(i).compareTo(cityName)==0) {
				index = i;
			}
		}
		
		return index;
	}
	
	public static void addPlace(String name) {
		boolean added = false;
		
		for (int i = 0; i < PLACE_LIST.size(); i++) {
			if (PLACE_LIST.get(i).compareTo(name) == 0) {
				added = true;
			}
		}
		
		if (!added) {
			PLACE_LIST.add(name);
		}
	}
	
}
