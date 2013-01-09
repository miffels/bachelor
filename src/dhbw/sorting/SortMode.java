package dhbw.sorting;

public class SortMode {

	public static final SortMode RAN = new SortMode("Random numbers");
	public static final SortMode SOR = new SortMode("Sorted numbers");
	public static final SortMode INV = new SortMode("Inverse-sorted numbers");
	
	private String desc = null;
	
	private SortMode(String desc) {
		this.desc = desc;
	}
	
}
