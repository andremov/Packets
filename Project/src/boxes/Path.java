/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxes;

import java.util.ArrayList;

/**
 *
 * @author movillaf
 */
public class Path {
	
    private int goal;
    private ArrayList<PlaceConnection> connections;
    private double total;
    
    public Path(int goal) {
        this.connections = new ArrayList<>();
        this.goal = goal;
        this.total = 0.0;
    }
    
    public Path(ArrayList<PlaceConnection> connections) {
        this.connections = connections;
        this.goal = connections.get(connections.size()-1).getConnection();
        calcTotal();
    }
    
    public Path(Path stitch1, Path stitch2) {
        this.connections = new ArrayList<>();
        
        for (int i = 0; i < stitch1.size(); i++) {
            this.connections.add(stitch1.get(i));
        }
        
        for (int i = 0; i < stitch2.size(); i++) {
            this.connections.add(stitch2.get(i));
        }
        
        this.goal = connections.get(connections.size()-1).getConnection();
        calcTotal();
    }

    @Override
    public String toString() {
        String text = "Cost to "+goal+" is "+getRealTotal()+" following path: Origin ";
        for (int i = 0; i < connections.size(); i++) {
            text = text + " -> "+ connections.get(i).getConnection();
        }
        return text;
    }
    
    
    private void calcTotal() {
        total = 0;
        for (int i = 0; i < size(); i++) {
            total = getTotal() + get(i).getCost();
        }
    }
    
    public boolean isDirect() {
        return connections.size() == 1;
    }

    /**
     * @return the goal
     */
    public int getGoal() {
        return goal;
    }

    /**
     * @return the connections
     */
    public PlaceConnection get(int index) {
        return connections.get(index);
    }
    
    public int size() {
        return connections.size();
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }
    
    
    public double getRealTotal() {
        return total/100;
    }
    
}
