package dhbw.sondierung;

public class HashType {
	
	private String desc = null;
	public static final HashType GENERAL = new HashType("General abstract type");
	public static final HashType LINEAR = new HashType("Linear Probing");
	public static final HashType DOUBLE = new HashType("Double hashing");
	public static final HashType QUATRATIC = new HashType("Quadratic Probing");
	
	private HashType(String desc) {
		this.desc = desc;
	}
	
	public String toString() {
		return this.desc;
	}

}
