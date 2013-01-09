package dhbw.hashing;

public class Bucket {
	public Integer[] value = new Integer[1];
	
	public Bucket(Integer i) {
		value[0] = i;
	}
	
	public void add(Integer i) {
		Integer[] res = new Integer[value.length + 1];
		for(int j = 0; j < value.length; j++) {
			res[j] = value[j];
		}
		res[res.length-1] = i;
		value = res;
	}
	
	public String toString() {
		String res = "";
		String t = "\t";
		for(int i = 0; i < value.length; i++) {
			res += t + value[i] + "\n";
		}
		return res;
	}
	
	public boolean contains(Integer i) {
		boolean res = false;
		for(int j = 0; j < value.length; j++) {
			if(value[j].equals(i)) {
				res = true;
			}
		}
		return res;
	}
}
