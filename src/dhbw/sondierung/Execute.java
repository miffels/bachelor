package dhbw.sondierung;

public class Execute {

	private static Integer limit = 200; // 101, 1009, 10039
	
	public static void main(String[] args) {
		Hashtable probe = new LinearProbe(limit);
		System.out.println(probe.runStatistical(10));
		probe = new QuadraticProbe(limit);
		System.out.println(probe.runStatistical(10));
		probe = new DoubleHashing(limit);
		System.out.println(probe.runStatistical(10));
	}
}