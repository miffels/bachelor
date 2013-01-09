package dhbw.hashing;

public class HashMethod {
	
	public static final HashMethod MD5 = new HashMethod("MD5");
	public static final HashMethod SHA = new HashMethod("SHA-1");
	public static final HashMethod CSU = new HashMethod("Crosssum, unsigned");
	public static final HashMethod CSS = new HashMethod("Crosssum, signed");
	public static final HashMethod CSB = new HashMethod("Crosssum, byte-wise");
	private String desc = "";
	
	private HashMethod(String desc) {
		this.desc = desc;
	}
	
	public String toString() {
		return this.desc;
	}
}
