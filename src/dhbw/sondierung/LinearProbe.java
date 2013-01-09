package dhbw.sondierung;

public class LinearProbe extends Hashtable {
	
	private static final Type type = new Type(HashType.LINEAR);

	public LinearProbe() {
		super();
	}
	
	public LinearProbe(Integer num) {
		super(num);
	}
	
	@Override
	protected Integer getHash(Integer num, Integer i) {
		return getRest(getRest(num) + i);
	}
	
	public String getStats() {
		String res = LinearProbe.type.type.toString() + "\n";
		return res;
	}
}
