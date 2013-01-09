package dhbw.sondierung;

public class Status {
	
	public static final Status S0 = new Status("Table below 50%");
	public static final Status S50 = new Status("Table at 50%");
	public static final Status S90 = new Status("Table at 90%");
	public static final Status S95 = new Status("Table at 95%");
	public static final Status S100 = new Status("Table at 100%");
	
	private String desc = null;
	private Status(String desc) {
		this.desc = desc;
	}
	
	public String toString() {
		return this.desc;
	}
}
