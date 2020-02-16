package searchclient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Heuristic implements Comparator<State> {

	private HashMap<Character,Set<Pair<Integer>>> goalCells;

	public Heuristic(final State initialState) {
		System.err.println("Preprocessing heuristic");
		this.goalCells = new HashMap<Character,Set<Pair<Integer>>>();

		for(int i = 0; i < initialState.getGoals().size(); i++){
			for(int j = 0; j < initialState.getGoals().get(i).length; j++){	
				final char goalLetter = initialState.getGoals().get(i)[j];
				if(goalLetter == '\u0000'){
					continue;
				}
				System.err.println("Found goal " + goalLetter + " at "+ i+" "+j);
				final Character letter = Character.valueOf(goalLetter);
				Set<Pair<Integer>> goalList = goalCells.get(letter);
				if(goalList == null){
					goalList = new HashSet<>();
					goalCells.put(letter, goalList);
				}
				goalList.add(new Pair<Integer>(i,j));
			}
		}
	}

	public int h(final State n) {
		int h = 0;
		//
		HashMap<Character, List<Box>> boxData = n.getBoxData();
	    for(Character c : boxData.keySet()) {
	    	// Retrieve goal cells for this letter
	    	Set<Pair<Integer>> boxGoalCells = goalCells.get(c);
	    	Set<Pair<Integer>> notOccupiedGoals = new HashSet<Pair<Integer>>();
    		if(boxGoalCells == null) {
    			continue;
    		}
    		// Find occupied goal cells and add them to notOccupiedGoals
    		for(Box box : boxData.get(c)) {
	    		for(Pair<Integer> goalCell : boxGoalCells) {
	    			if(goalCell.p1 == box.getyPos() && goalCell.p2 == box.getxPos()) {
	    				notOccupiedGoals.add(goalCell);
	    			}
	    		}
	    	}
    		// Find distance from each box to the closes NOT occupied goal
	    	for(Box box : boxData.get(c)) {
		    	int boxdist = Integer.MAX_VALUE;
		    	//
	    		for(Pair<Integer> goalCell : notOccupiedGoals) {
	    			int dist = (int) Math.round(Math.sqrt(Math.pow((goalCell.p1 - box.getyPos()),2) + Math.pow((goalCell.p2 - box.getxPos()),2)));
	    			if(dist < boxdist) {
	    				boxdist = dist;
	    			}
	    		}
	    		h += boxdist;
	    	}
	    }
	    return h;
//		return 1;
	}

	public abstract int f(State n);

	@Override
	public int compare(final State n1, final State n2) {
		return this.f(n1) - this.f(n2);
	}

	public static class AStar extends Heuristic {
		public AStar(final State initialState) {
			super(initialState);
			System.err.println("Initializing AStar...");
		}

		@Override
		public int f(final State n) {
			return n.g() + this.h(n);
		}

		@Override
		public String toString() {
			return "A* evaluation";
		}
	}

	public static class WeightedAStar extends Heuristic {
		private final int W;

		public WeightedAStar(final State initialState, final int W) {
			super(initialState);
			this.W = W;
		}

		@Override
		public int f(final State n) {
			return n.g() + this.W * this.h(n);
		}

		@Override
		public String toString() {
			return String.format("WA*(%d) evaluation", this.W);
		}
	}

	public static class Greedy extends Heuristic {
		public Greedy(final State initialState) {
			super(initialState);
		}

		@Override
		public int f(final State n) {
			return this.h(n);
		}

		@Override
		public String toString() {
			return "Greedy evaluation";
		}
	}

	private int frederikHeuristic(State s) {

		int h = 0;

		for (char goalLetter : this.goalCells.keySet()) {

			for(Pair<Integer> goalLetterEntity : this.goalCells.get(goalLetter)) {

				s.getBoxData();

			}

		}

		return 1;
	}

}
