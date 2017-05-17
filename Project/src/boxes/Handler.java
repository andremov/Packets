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
public abstract class Handler {
    public static int STAGE_LOADING = -1;
    public static int STAGE_LOCATE_PLACES = 0;
    public static int STAGE_ADD_BOXES = 1;
    public static int STAGE_ADD_PEOPLE = 2;
    public static int STAGE_SOLVE = 3;
    
    public static String MSG_LOAD = "Cargando...";
    public static String MSG_LOCATE = "Haga click para ubicar ";
    public static String MSG_BOXES = "Haga click en un sitio para ubicar una caja, o en otro lado para continuar.";
    public static String MSG_PEOPLE = "Haga click en un sitio para ubicar una persona, o en otro lado para continuar.";
    public static String MSG_SOLVE = "";
    
    
    public static int NUM_PLACES;

    static ArrayList<Place> places;
    static ArrayList<Person> people;
    static int totalCost;
    static int currentStage;
    static int mouseX;
    static int mouseY;
    static int currentSolveStep;
            
    public static void init() {
        currentStage = STAGE_LOADING;
        NUM_PLACES = Data.PLACE_LIST.size();
        currentSolveStep = 0;
        totalCost = 0;
        mouseX = -200;
        mouseY = -200;
        places = new ArrayList<>();
        people = new ArrayList<>();
        for (int i = 0; i < NUM_PLACES; i++) {
            places.add(new Place(i,Data.PLACE_LIST.get(i),Data.getConnections(i)));
        }
        new Window();
        createMatrix();
//        for (int j = 0; j < NUM_PLACES; j++) {
//            for (int i = 0; i < places.get(j).getConnections().size(); i++) {
//                System.out.println(places.get(j).getConnections().get(i).toString());
//            }
//        }
        currentStage = STAGE_LOCATE_PLACES;
    }
    
    public static void createMatrix() {
        for (int i = 0; i < NUM_PLACES; i++) {
//            System.out.println("Getting paths for place #"+i);
            boolean[] allDone = new boolean[NUM_PLACES];
            for (int k = 0; k < NUM_PLACES; k++) {
                allDone[k] = false;
            }
            boolean done = false;
            int search = 0;
            while (!done) {
//                System.out.println(">STARTING WHILE CYCLE WITH SEARCH "+search);
                allDone[search] = true;
                if (search == i) {
                    // ALREADY HERE
                } else {
                    Place place = places.get(i);
                    int currentPathIndex = -1;
                    double currentCost = 0.0;
//                    System.out.println(">Searching possible existing connection from "+i+" to "+search+"...");
                    for (int k = 0; k < place.getConnections().size(); k++) {
                        if (place.getConnections().get(k).getGoal() == search) {
                            currentPathIndex = k;
                            currentCost = place.getConnections().get(k).getTotal();
//                            System.out.println(">Found a connection from "+i+" to "+search+" for $"+currentCost);
                        }
                    }
//                    System.out.println(">Checking possible replacements for path from "+i+" to "+search+"...");
                    for (int k = 0; k < place.getConnections().size(); k++) {
                        Path fromPlaceToMiddle = place.getConnections().get(k);
                        if (fromPlaceToMiddle.getGoal() != search && fromPlaceToMiddle.getGoal() != i) {
                            Place middle = places.get(fromPlaceToMiddle.getGoal());
//                            System.out.println(">Extending "+i+" through "+fromPlaceToMiddle.getGoal()+"...");
                            for (int l = 0; l < middle.getConnections().size(); l++) {
                                if (middle.getConnections().get(l).getGoal() != fromPlaceToMiddle.getGoal() && middle.getConnections().get(l).getGoal() == search) {
//                                    System.out.println("->Found a connection from "+fromPlaceToMiddle.getGoal()+" to "+search);
                                    Path fromMiddleToGoal = middle.getConnections().get(l);
                                    if (currentPathIndex != -1) {
//                                        System.out.println("->Checking to replace existing connection");
                                          double newCost = fromPlaceToMiddle.getTotal() + fromMiddleToGoal.getTotal();
//                                          System.out.println("->Connection costs "+newCost);
                                          if (newCost < currentCost) {
//                                              System.out.println("->Connection is cheaper.");
                                            place.getConnections().set(currentPathIndex, new Path(fromPlaceToMiddle,fromMiddleToGoal));
                                            allDone[search] = false;
                                        } else {
//                                              System.out.println("->Connection isnt worth it");
                                          }
                                    } else {
//                                        System.out.println("->No connection exists, adding connection");
                                        place.getConnections().add(new Path(fromPlaceToMiddle,fromMiddleToGoal));
                                        currentPathIndex = place.getConnections().size()-1;
                                        currentCost = place.getConnections().get(place.getConnections().size()-1).getTotal();
                                        allDone[search] = false;
                                    }
                                }
                            }
                        }
                    }
                }
                
                // NEXT
                search++;
                if (search == NUM_PLACES) {
                    search = 0;
                    done = true;
                    for (int k = 0; k < NUM_PLACES; k++) {
                        if (!allDone[k]) {
//                            System.out.println("Place "+i+" is missing "+k);
                            done = false;
                        }
                    }
                    if (!done) {
//                        System.out.println("Not finished.");
                        for (int k = 0; k < NUM_PLACES; k++) {
                            allDone[k] = false;
                        }
                    }
                }
            }
        }
    }
    
    public static void solve() {
        int numBoxes = 0;
        for (int i = 0; i < NUM_PLACES; i++) {
            if (places.get(i).isBox()) {
                numBoxes++;
            }
        }
        while (numBoxes > 0) {
            int lowestBoxID = -1;
            int lowestPersonID = -1;
            double lowestCost = -1;
            for (int boxID = 0; boxID < NUM_PLACES; boxID++) {
                if (places.get(boxID).isBox() && !places.get(boxID).isTaken()) {
                    for (int personID = 0; personID < people.size(); personID++) {
                        Place personStation = places.get(people.get(personID).getLast());
                        double thisCost = personStation.getCostTo(boxID);
                        if (thisCost < lowestCost || lowestCost == -1) {
                            lowestCost = thisCost;
                            lowestBoxID = boxID;
                            lowestPersonID = personID;
                        }
                    }
                }
            }
            
//            System.out.println("BOX "+lowestBoxID+"   PERSON "+lowestPersonID+"   IS IN "+people.get(lowestPersonID).getLast()+"   COST "+lowestCost);
            Person thisPerson = people.get(lowestPersonID);
            Place personStation = places.get(thisPerson.getLast());
            Path path = personStation.getPathTo(lowestBoxID);
            places.get(lowestBoxID).takeBox();
            for (int i = 0; i < path.size(); i++) {
                thisPerson.history.add(path.get(i).getConnection());
                thisPerson.distances.add(path.get(i).getCost());
            }
            numBoxes--;
        }
    }
    
    public static void mouse(int x, int y) {
        mouseX = x;
        mouseY = y;
    }
    
    public static void bestConnection(int index) {
        // FINDS THE PERSON CLOSEST TO THIS box
        // AND MOVES THEM HERE
        Place thisPlace = places.get(index);
        if (thisPlace.isBox() && !thisPlace.isTaken()) {
            if (hasPerson(index)) {
                thisPlace.takeBox();
            } else {
                
            }
        }
    }
    
    public static String getStageMessage() {
        String message;
        if (currentStage == STAGE_LOCATE_PLACES) {
            int index = 0;
            while (places.get(index).isLocated()) {
                index++;
            }
            message = MSG_LOCATE + " " + places.get(index).getName();
        } else if (currentStage == STAGE_ADD_BOXES) {
            message = MSG_BOXES;
        } else if (currentStage == STAGE_ADD_PEOPLE) {
            message = MSG_PEOPLE;
        } else if (currentStage == STAGE_SOLVE) {
            message = MSG_SOLVE;
        } else if (currentStage == STAGE_LOADING) {
            message = MSG_LOAD;
        } else {
            message = "?!";
        }
        return message;
    }
    
    public static boolean hasPerson(int id) {
        boolean isThere = false;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getCurrent() == id) {
                isThere = true;
            }
        }
        return isThere;
    }
    
    public static void stageAction(int x, int y) {
        if (currentStage == STAGE_LOCATE_PLACES) {
            boolean validPosition = true;
            for (int i = 0; i < places.size(); i++) {
                boolean NW = places.get(i).isContained(x-35, y-35);
                boolean NE = places.get(i).isContained(x+35, y-35);
                boolean SW = places.get(i).isContained(x-35, y+35);
                boolean SE = places.get(i).isContained(x+35, y+35);
                if (validPosition) {
                    validPosition = !(NW || NE || SW || SE);
                }
            }
            if (validPosition) {
                int index = 0;
                while (index < places.size() && places.get(index).isLocated()) {
                    index++;
                }
                places.get(index).setPos(x,y);
                if (index == NUM_PLACES-1) {
                    currentStage = STAGE_ADD_BOXES;
                }
            }
        } else if (currentStage == STAGE_ADD_BOXES) {
            int index = 0;
            while (index < places.size() && !places.get(index).isContained(x, y)) {
                index++;
            }
            if (index < places.size()) {
                places.get(index).setBox(true);
            } else {
                boolean canGo = false;
                index = 0;
                while (index < places.size() && !canGo) {
                    if (places.get(index).isBox()) {
                        canGo = true;
                    }
                    index++;
                }
                if (canGo) {
                    currentStage = STAGE_ADD_PEOPLE;
                }
            }
        } else if (currentStage == STAGE_ADD_PEOPLE) {
            int index = 0;
            while (index < places.size() && !places.get(index).isContained(x, y)) {
                index++;
            }
            if (index < places.size()) {
                people.add(new Person(index));
//                places.get(index).setBox(true);
            } else if (people.size() > 0) {
                    currentStage = STAGE_SOLVE;
                    solve();
            }
        } else if (currentStage == STAGE_SOLVE) {
            currentSolveStep++;
            int max = 0;
            for (int i = 0; i < people.size(); i++) {
                max = Integer.max(max,people.get(i).distances.size());
            }
            if (currentSolveStep > max) {
                currentSolveStep = 0;
            }
        }
    }
}
