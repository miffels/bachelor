package dhbw.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Sort {
	
	private static SortMode mode = SortMode.RAN;
	private static Sort sorter = new Sort();
	
	public static Sort getSort() {
		return Sort.sorter;
	}
	
	private Sort() {
	}
	
	public Integer[] ran1k; {
		ran1k = new Integer[1000];
		Random rand = new Random();
		for(Integer i = 0; i < ran1k.length; i++) {
			ran1k[i] = rand.nextInt();
		}
	}
	public static Integer[] ran10k; {
		ran10k = new Integer[10000];
		Random rand = new Random();
		for(Integer i = 0; i < ran10k.length; i++) {
			ran10k[i] = rand.nextInt();
		}
	}
	public Integer[] ran100k; {
		ran100k = new Integer[100000];
		Random rand = new Random();
		for(Integer i = 0; i < ran100k.length; i++) {
			ran100k[i] = rand.nextInt();
		}
	}
	public Integer[] sor1k; {
		sor1k = new Integer[1000];
		for(Integer i = 0; i < sor1k.length; i++) {
			sor1k[i] = i;
		}
	}
	public Integer[] sor10k; {
		sor10k = new Integer[10000];
		for(Integer i = 0; i < sor10k.length; i++) {
			sor10k[i] = i;
		}
	}
	public Integer[] sor100k; {
		sor100k = new Integer[100000];
		for(Integer i = 0; i < sor100k.length; i++) {
			sor100k[i] = i;
		}
	}
	public Integer[] inv1k; {
		inv1k = new Integer[1000];
		for(Integer i = inv1k.length - 1; i >= 0; i--) {
			inv1k[999-i] = i;
		}
	}
	public Integer[] inv10k; {
		inv10k = new Integer[10000];
		for(Integer i = inv10k.length - 1; i >= 0; i--) {
			inv10k[9999-i] = i;
		}
	}
	public Integer[] inv100k; {
		inv100k = new Integer[100000];
		for(Integer i = inv100k.length - 1; i >= 0; i--) {
			inv100k[99999-i] = i;
		}
	}
	
	public void setMode(SortMode mode) {
		Sort.mode = mode;
	}
	
	public SortMode getMode() {
		return Sort.mode;
	}
	
	public BasicSort getAlgorithm(SortType alg) {
		BasicSort res = null;
		if(alg.equals(SortType.BOGOSORT)) {
			res = new Bogosort();
		} else if(alg.equals(SortType.BUBBLESORT)) {
			res = new Bubblesort();
		} else if(alg.equals(SortType.BUBBLESORTIMP)) {
			res = new BubblesortImp();
		} else if(alg.equals(SortType.DROPSORT)) {
			res = new Dropsort();
		} else if(alg.equals(SortType.MERGESORT)) {
			res = new Mergesort();
		} else if(alg.equals(SortType.QUICKSORT)) {
			res = new Quicksort();
		}
		return res;
	}
	
	public static abstract class BasicSort {
		protected Integer[] src = null;
		protected double t = 0;
		protected Thread thread = null;
		
		public String startTests() throws InterruptedException {
			String res = this.getClass().getSimpleName() + "\n";
			startSort(Sort.getSort().ran1k);
			thread.join();
			res += this.t + "\t";
			startSort(Sort.getSort().ran10k);
			thread.join();
			res += this.t + "\t";
			startSort(Sort.getSort().ran100k);
			thread.join();
			res += this.t + "\t";
			res += "\n";
			startSort(Sort.getSort().sor1k);
			thread.join();
			res += this.t + "\t";
			startSort(Sort.getSort().sor10k);
			thread.join();
			res += this.t + "\t";
			startSort(Sort.getSort().sor100k);
			thread.join();
			res += this.t + "\t";
			res += "\n";
			startSort(Sort.getSort().inv1k);
			thread.join();
			res += this.t + "\t";
			startSort(Sort.getSort().inv10k);
			thread.join();
			res += this.t + "\t";
			startSort(Sort.getSort().inv100k);
			thread.join();
			res += this.t + "\t";
			res += "\n";
			return res;
		}
		
		public void startSort(Integer[] source) {
			this.src = source;
			
			Runnable r = new Runnable() {
				public void run() {
					t = System.currentTimeMillis();
					sort(src.clone());
					t = System.currentTimeMillis() - t;
					t /= 1000;
				}
			};
			thread = new Thread(r);
			thread.start();
		}
		protected abstract void sort(Integer[] source);
	}
	
	private static class Dropsort extends BasicSort {
		 public void sort(Integer[] src) {};
	}
	private static class Bogosort extends BasicSort {
		private Integer[] src = null;
		
		public void sort(Integer[] src) {
			Integer i = 1;
			boolean res = true;
			while(res && i < src.length) {
				if(src[i-1] > src[i]) {
					res = false;
				}
				i++;
			}
		}
		
		private void shuffle() {
			List<Integer> l = new ArrayList<Integer>();
			for(Integer i : src) {
				l.add(i);
			}
			Collections.shuffle(l);
			Iterator<Integer> it = l.iterator();
			for(Integer i = 0; i < src.length; i++) {
				src[i] = it.next();
			}
		}
	}
	private static class Quicksort extends BasicSort {
		
		public void sort(Integer[] src) {
			this.sort(0, src.length - 1, src);
		}
		
		private void sort(int low, int high, Integer[] src) {
			int i = low, j = high, temp = 0;
			
			int pivot = src[(low + high) / 2];

			while (i <= j) {

				while (src[i] < pivot) {
					i++;
				}

				while (src[j] > pivot) {
					j--;
				}

				if (i <= j) {
					src[i] = temp;
					i = j;
					j = temp;
					i++;
					j--;
				}
			}
			
			if (low < j)
				sort(low, j, src);
			if (i < high)
				sort(i, high, src);
		}
	}
	
	private static class Bubblesort extends BasicSort {
		public void sort(Integer[] src) {
	        int temp, counter;
	        for(Integer j=0; j<src.length-1; j++) {
	            for(Integer i=j; i >= 0; i--) {
	                if(src[i] > src[i+1]) {
	                    temp = src[i];
	                    src[i] = src[i+1];
	                    src[i+1] = temp;
	                }
	            }
	        }
	    }
	}
	
	private static class BubblesortImp extends BasicSort {
		public void sort(Integer[] src) {
			boolean unsorted = true;
			while(unsorted) {
				unsorted = false;
				for(int i = 0; i < src.length - 1; ++i) {
					if(src[i] > src[i + 1]) {
						int temp = src[i];
						src[i] = src[i + 1];
						src[i + 1] = temp;
						unsorted = true;
					}
				}
			}
		}
	}
	
	private static class Mergesort extends BasicSort {
	    public void sort(Integer[] a) {
	        Integer[] tmpArray = new Integer[a.length];
	        sort( a, tmpArray, 0, a.length - 1 );
	    }
	    
	    private static void sort(Integer[] a, Integer[] tmpArray,
	            int left, int right) {
	        if(left < right) {
	            int center = (left + right) / 2;
	            sort(a, tmpArray, left, center);
	            sort(a, tmpArray, center + 1, right);
	            merge(a, tmpArray, left, center + 1, right);
	        }
	    }
	    
	    private static void merge(Integer[] a, Integer[] tmpArray,
	            int leftPos, int rightPos, int rightEnd) {
	        int leftEnd = rightPos - 1;
	        int tmpPos = leftPos;
	        int numElements = rightEnd - leftPos + 1;
	        
	        while(leftPos <= leftEnd && rightPos <= rightEnd)
	            if(a[ leftPos ].compareTo(a[rightPos]) <= 0)
	                tmpArray[tmpPos++] = a[leftPos++];
	            else
	                tmpArray[tmpPos++] = a[rightPos++];
	        
	        while(leftPos <= leftEnd)
	            tmpArray[tmpPos++] = a[leftPos++];
	        
	        while(rightPos <= rightEnd)
	            tmpArray[tmpPos++] = a[rightPos++];
	        
	        for(int i = 0; i < numElements; i++, rightEnd--)
	            a[rightEnd] = tmpArray[rightEnd];
	    }
	}
}