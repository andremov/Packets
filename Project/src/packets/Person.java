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
public class Person {
	
	int[] history;
	int[] distances;
	int numSteps;

	public Person(int baseCity) {
		history = new int[20];
		distances = new int[20];
		for (int i = 0; i < 20; i++) {
			history[i] = -1;
			distances[i] = -1;
		}
	
		history[0] = baseCity;
		distances[0] = 0;
		numSteps=1;
	}
	
	public void addStep(int newCity, int newDistance) {
		history[numSteps] = newCity;
		distances[numSteps] = newDistance;
		numSteps++;
	}
	
	public void removeStep() {
		numSteps--;
		history[numSteps] = -1;
		distances[numSteps] = -1;
	}
	
}
