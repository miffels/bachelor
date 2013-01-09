package dhbw.sondierung;

public class DoubleHashing extends Hashtable {
	
	private static final Type type = new Type(HashType.DOUBLE);
	
	public DoubleHashing() {
		super();
	}
	
	public DoubleHashing(Integer num) {
		super(num);
	}
	
	@Override
	protected Integer getHash(Integer num, Integer i) {
		return getRest(getRest(num) + i*getRest(num, this.num - 2));
	}
	
	public String getStats() {
		String res = DoubleHashing.type.type.toString() + "\n";
		return res;
	}
}
