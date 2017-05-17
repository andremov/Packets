/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packets;

/**
 *
 * @author Andres
 */
public class Entry {
	String name;
	String connection;
	int cost;

	public Entry(String name, String connection, int cost) {
		this.name = name;
		this.connection = connection;
		this.cost = cost;
	}
}
