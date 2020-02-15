package searchclient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public abstract class Heuristic implements Comparator<State> {

	private HashMap<Character,List<Pair<Integer>>> goalCells;


	enum type {
		WALL, BOX, GOAL, AGENT
	}

	public Heuristic(final State initialState) {
		System.err.println("Preprocessing heuristic");
		this.goalCells = new HashMap<Character,List<Pair<Integer>>>();

		for(int i = 0; i < initialState.getGoals().size(); i++){
			for(int j = 0; j < initialState.getGoals().get(i).length; j++){	
				final char goalLetter = initialState.getGoals().get(i)[j];
				if(goalLetter == '\u0000'){
					continue;
				}
				System.err.println("Found goal " + goalLetter + " at "+ i+" "+j);
				final Character letter = Character.valueOf(goalLetter);
				List<Pair<Integer>> goalList = goalCells.get(letter);
				if(goalList == null){
					goalList = new ArrayList<>();
					goalCells.put(letter, goalList);
				}
				goalList.add(new Pair<Integer>(i,j));
			}
		}
	}

	public int h(final State n) {
		int h = 0;
		//
	    for(int i = 0; i < n.boxes.length; i++) {
	    	int boxdist = Integer.MAX_VALUE;
	    	//
	    	for(int j = 0; j < n.boxes[i].length; j++) {
	    		if(n.boxes[i][j] == '\u0000') {
	    			continue;
	    		}
	    		//System.err.println("Box letter: " + n.boxes[i][j] + "at "+i+","+j);
	    		Character boxLetter = Character.valueOf(n.boxes[i][j]);
	    		List<Pair<Integer>> boxGoalCells = goalCells.get(boxLetter);
	    		if(boxGoalCells == null) {
	    			continue;
	    		}
	    		System.err.println("Checking box: " + boxLetter);
	    		for(Pair<Integer> goalCell : boxGoalCells) {
	    			int dist = (int) Math.round(Math.sqrt((goalCell.p1 - i) * (goalCell.p1 - i) + (goalCell.p2 - j) * (goalCell.p2 - j)));
	    			if(dist < boxdist) {
	    				boxdist = dist;
	    			}
	    		}
	    	}
	    	h += boxdist;
	    }
	    return h;
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
}
