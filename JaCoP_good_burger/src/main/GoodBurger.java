package main;

import org.jacop.constraints.*;
import org.jacop.core.*;
import org.jacop.search.*;

public class GoodBurger {
	public static void main(String[] args) {
		long T1, T2, T;
		T1 = System.currentTimeMillis();
		burger();
		T2 = System.currentTimeMillis();
		T = T2 - T1;
		System.out.println("\n\t*** Execution time = " + T + " ms");
	}

	static void burger() {
		Store store = new Store();
		IntVar beefPatty = new IntVar(store, "Beef Patty", 1, 5);
		IntVar bun = new IntVar(store, "Bun", 1, 5);
		IntVar cheese = new IntVar(store, "Cheese", 1, 5);
		IntVar onions = new IntVar(store, "Onions", 1, 5);
		IntVar pickles = new IntVar(store, "Pickles", 1, 5);
		IntVar lettuce = new IntVar(store, "Lettuce", 1, 5);
		IntVar letchup = new IntVar(store, "Ketchup", 1, 5);
		IntVar tomato = new IntVar(store, "Tomato", 1, 5);

		IntVar[] items = new IntVar[] { beefPatty, bun, cheese, onions,
				pickles, lettuce, letchup, tomato };
		int[] price = new int[] { 25, 15, 10, 9, 3, 4, 2, 4 };
		int[] sodium = new int[] { 50, 330, 310, 1, 260, 3, 160, 3 };
		int[] fat = new int[] { 17, 9, 6, 2, 0, 0, 0, 0 };
		int[] calories = new int[] { 220, 260, 70, 10, 5, 4, 20, 9 };

		store.impose(new Linear(store, items, sodium, "<", 3000));
		store.impose(new Linear(store, items, fat, "<", 150));
		store.impose(new Linear(store, items, calories, "<", 3000));

		Search<IntVar> label = new DepthFirstSearch<IntVar>();
		SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(items,
				new SmallestDomain<IntVar>(), new IndomainMin<IntVar>());
		label.setSolutionListener(new PrintOutListener<IntVar>());
		label.getSolutionListener().searchAll(true);
		boolean Result = label.labeling(store, select);
		if (Result) {
			System.out.println("\n*** Yes");
			System.out.println("Solution : " + java.util.Arrays.asList(items));
		} else
			System.out.println("\n*** No");

	}
}
