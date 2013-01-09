package dhbw.threadtime;

public class Test {

	/**
	 * @param args
	 */
	
	// Irgendeine Funktion für etwas Rechenlast
	
	private static String[] bla;
	private static int[] blubb; {
		blubb = new int[2];
		blubb[0] = 1;
		blubb[1] = 2;
	}
	private int[] blubb2; {
		blubb2 = new int[2];
		blubb2[0] = 1;
		blubb2[1] = 2;
	}
	
	// Another one
	public void iAmNotStatic() {
		Runnable r = new Runnable() {
			public void run() {
				Dummy.anyMethod(blubb2);
			}
		};
	}
	
	
	private static void rec(int[] i) {
	}
	
	// Executable
	public static void main(String[] args) {
		
		// Inhalt des Threads
		Runnable r = new Runnable() {
			public void run() {
				Dummy.anyMethod(blubb);
			}
		};
		
		// Zeit nehmen
		long time = System.currentTimeMillis();
		
		// Los gehts
		Thread t = new Thread(r);
		t.start();
		
		// Jetzt: warten!
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Zeit nehmen
		System.out.println(System.currentTimeMillis()-time);
	}
	

}
