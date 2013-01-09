package dhbw.sorting;

import java.util.Random;

public class SortTest {

	
	private static int[] numberSet;
	
	public static void fillArray(int limit) {
		Random rand = new Random();
		numberSet = new int[limit];
		for(int i = 0; i < limit; i++) {
			numberSet[i] = rand.nextInt();
		}
	}
	
	private static void bubbleSort() {
		int[] numbers = numberSet.clone();
		boolean sort = false;
		int buf = 0;
		while (!sort){
			sort = true;
			for (int i=0; i < numbers.length-1; i++)
				if (numbers[i] > numbers[i+1]) {
					buf = numbers[i];
					numbers[i] = numbers[i+1];
					numbers[i+1] = buf;
					sort = false;
				}
		}
	}

		private static void quickSort() {
			final class Sort {
			    private int[] a;
			    private int n;
			    public void sort(int[] a) {
			        this.a=a;
			        n=a.length;
			        quicksort(0, n-1);
			    }

			    private void quicksort (int lo, int hi) {
			        int i=lo, j=hi;

			        // Vergleichs x
			        int x=a[(lo+hi)/2];

			        //  Aufteilung
			        while (i<=j) {    
			            while (a[i]<x) i++; 
			            while (a[j]>x) j--;
			            if (i<=j) {
			                exchange(i, j);
			                i++; j--;
			            }
			        }

			        // Rekursion
			        if (lo<j) quicksort(lo, j);
			        if (i<hi) quicksort(i, hi);
			    }

			    private void exchange(int i, int j) {
			        int t=a[i];
			        a[i]=a[j];
			        a[j]=t;
			    }
		}
}
	
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		fillArray(10000);
		bubbleSort();
		System.out.println(System.currentTimeMillis()-time);
	}

}
