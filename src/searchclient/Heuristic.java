package searchclient;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class Heuristic implements Comparator<State> {

	private HashMap<Character, Set<Pair<Integer>>> goalCells;

	public Heuristic(final State initialState) {
		this.goalCells = new HashMap<Character, Set<Pair<Integer>>>();

		for (int i = 0; i < initialState.getGoals().size(); i++) {
			for (int j = 0; j < initialState.getGoals().get(i).length; j++) {
				final char goalLetter = initialState.getGoals().get(i)[j];
				if (goalLetter == '\u0000') {
					continue;
				}
				final Character letter = Character.valueOf(goalLetter).toUpperCase(goalLetter);
				Set<Pair<Integer>> goalList = goalCells.get(letter);
				if (goalList == null) {
					goalList = new HashSet<>();
					System.err.println("Add letter " + letter);
					goalCells.put(letter, goalList);
				}
				goalList.add(new Pair<Integer>(i, j));
			}
		}
	}

	public int h(final State n) {
		int h = 0;
		//
		HashMap<Character, Set<Box>> boxData = n.getBoxData();
		for (Character c : boxData.keySet()) {
			// Retrieve goal cells for this letter

			Set<Pair<Integer>> boxGoalCells = goalCells.get(c);
			Set<Pair<Integer>> notOccupiedGoals = new HashSet<Pair<Integer>>();
			if (boxGoalCells == null) {
				continue;
			}
			// Find occupied goal cells and add them to notOccupiedGoals
			HashSet<Box> availableBoxes = (HashSet) ((HashSet) boxData.get(c)).clone();
			for (Box box : availableBoxes) {
				for (Pair<Integer> goalCell : boxGoalCells) {
					if (!(goalCell.p1 == box.getyPos() && goalCell.p2 == box.getxPos())) {
						notOccupiedGoals.add(goalCell);
					}
				}
			}

			Set<Box> assignedBoxes = new HashSet<Box>();
			for (Pair<Integer> goalPosition : notOccupiedGoals) {
				int boxGoalDist = Integer.MAX_VALUE;
				Box currentClosestBox = null;
				for (Box box : availableBoxes) {
					int dist = (int) Math.round(Math.sqrt(Math.pow((goalPosition.p1 - box.getyPos()), 2)
							+ Math.pow((goalPosition.p2 - box.getxPos()), 2)));
					if (dist < boxGoalDist) {
						boxGoalDist = dist;
						currentClosestBox = box;
					}
				}
				availableBoxes.remove(currentClosestBox);
				assignedBoxes.add(currentClosestBox);
//				System.err
//						.println("Assigned goal at " + currentClosestBox.getxPos() + "" + currentClosestBox.getyPos());
				h += boxGoalDist;
			}

			for (Box box : assignedBoxes) {
				// System.err.println("Assigned boxes " + assignedBoxes.size());
				h += (int) Math.round(Math
						.sqrt(Math.pow((n.agentRow - box.getyPos()), 2) + Math.pow((n.agentRow - box.getxPos()), 2)));
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

			for (Pair<Integer> goalLetterEntity : this.goalCells.get(goalLetter)) {

				s.getBoxData();

			}

		}

		return 1;
	}

}
