/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxes;

/**
 *
 * @author Andres
 */
public class DataEntry {
	
	private String name;
	private String connection;
	private double cost;

	public DataEntry(String name, String connection, double cost) {
		this.name = name;
		this.connection = connection;
		this.cost = cost;
	}

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the connection
     */
    public String getConnection() {
        return connection;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return cost;
    }
}
