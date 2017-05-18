/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxes;

/**
 *
 * @author movillaf
 */
public class PlaceConnection {
    
	private int connection;
	private double cost;

	public PlaceConnection(int id, double cost) {
		this.connection = id;
		this.cost = cost;
	}

    /**
     * @return the connection
     */
    public int getConnection() {
        return connection;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return cost;
    }
}
