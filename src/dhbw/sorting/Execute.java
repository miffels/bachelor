package dhbw.sorting;

import dhbw.sorting.Sort.BasicSort;

public class Execute {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		BasicSort sort = Sort.getSort().getAlgorithm(SortType.BUBBLESORT);
		System.out.print(sort.startTests());
		sort = Sort.getSort().getAlgorithm(SortType.BUBBLESORTIMP);
		System.out.print(sort.startTests());
		sort = Sort.getSort().getAlgorithm(SortType.MERGESORT);
		System.out.print(sort.startTests());
		sort = Sort.getSort().getAlgorithm(SortType.QUICKSORT);
		System.out.print(sort.startTests());
	}

}
