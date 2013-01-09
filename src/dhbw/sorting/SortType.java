package dhbw.sorting;

public class SortType {
	
	public static final SortType DROPSORT = new SortType("Dropsort (esoteric)");
	public static final SortType BOGOSORT = new SortType("Bogostort (esoteric)");
	public static final SortType QUICKSORT = new SortType("Quicksort");
	public static final SortType BUBBLESORT = new SortType("Bubblesort");
	public static final SortType BUBBLESORTIMP = new SortType("Bubblesort (optimized)");
	public static final SortType MERGESORT = new SortType("Mergesort");
	private String desc = null;
	private SortType(String desc) {
		this.desc = desc;
	}
}
