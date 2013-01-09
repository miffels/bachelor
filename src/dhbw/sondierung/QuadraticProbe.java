package dhbw.sondierung;

public class QuadraticProbe extends Hashtable {
	
	private static final Type type = new Type(HashType.QUATRATIC);

	public QuadraticProbe() {
		super();
	}
	
	public QuadraticProbe(Integer num) {
		super(num);
	}
	
	public String getStats() {
		String res = QuadraticProbe.type.type.toString() + "\n";
		return res;
	}

	@Override
	public Integer getHash(Integer num, Integer i) {
		Double fac = Math.pow(-1, i);
		return getRest(getRest(num) + fac.intValue() * i * i);
	}

}
