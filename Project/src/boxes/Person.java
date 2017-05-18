/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxes;

import java.util.ArrayList;

/**
 *
 * @author Andres
 */
public class Person {
	
	ArrayList<Integer> history;
	ArrayList<Double> distances;

	public Person(int baseCity) {
		history = new ArrayList<>();
		distances = new ArrayList<>();
	
		history.add(baseCity);
		distances.add(0.0);
	}
	
	public void addStep(int newCity, double newDistance) {
		history.add(newCity);
		distances.add(newDistance);
	}
        
	public int getCurrent() {
		return history.get(Integer.min(history.size()-1,Handler.currentSolveStep));
	}

	public int getLast() {
		return history.get(history.size()-1);
	}
	
	public void backStep() {
		history.remove(history.size()-1);
		distances.remove(distances.size()-1);
	}
        
	public void backTo(int cityID) {
		int newSize = 1;
		
		for (int i = 0; i < history.size(); i++) {
			if (history.get(i) == cityID) {
				newSize = i+1;
			}
		}
		
		while (history.size() != newSize) {
			backStep();
		}
	}
	
}
