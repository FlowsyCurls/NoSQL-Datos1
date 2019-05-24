package Listas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class SimpleList_ComparableTest {
	private static SimpleList_Comparable<Integer> sorted1;
	private static SimpleList_Comparable<Integer> unsorted1;
	private static SimpleList_Comparable<String> ejemplo;
	private static SimpleList_Comparable<String> ejemplo2;
	@Before
	public void before() {
		System.out.println("\nListas");
		sorted1 = new SimpleList_Comparable<>();
		unsorted1 = new SimpleList_Comparable<>();
		ejemplo= new SimpleList_Comparable<>();
		ejemplo2= new SimpleList_Comparable<>();
		/*ordenada*/
	ejemplo.addLast("hola"); ejemplo.addLast("mundo"); ejemplo.addLast("java");
	ejemplo.addLast("python"); ejemplo.addLast("c++");
	ejemplo2.addLast("python"); ejemplo2.addLast("c++"); ejemplo2.addLast("java");
	ejemplo2.addLast("hola"); ejemplo2.addLast("mundo");
    	sorted1.addLast(0); sorted1.addLast(1); sorted1.addLast(2); sorted1.addLast(3);
    	sorted1.addLast(4); sorted1.addLast(5); sorted1.addLast(6); sorted1.addLast(7);
    	sorted1.addLast(8); sorted1.addLast(9);
    	sorted1.print();
    	/*desordenada*/
    	unsorted1.addLast(8); unsorted1.addLast(4); unsorted1.addLast(6); unsorted1.addLast(1);
    	unsorted1.addLast(7); unsorted1.addLast(5); unsorted1.addLast(0); unsorted1.addLast(3);
    	unsorted1.addLast(2); unsorted1.addLast(9);
    	unsorted1.print();
	}

	@After
	public void after() {
    	for (int i=0; i<sorted1.getLargo(); i++) {
    		assertEquals(sorted1.get(i), unsorted1.get(i));
    	}
	for (int i=0; i<ejemplo.getLargo(); i++) {
    		assertEquals(ejemplo.get(i), ejemplo2.get(i));
    	}
		System.out.println("Termina un test\n");
	}

	@org.junit.Test
	public void testBubbleSort() {
		System.out.println("BUBBLESORT");
		unsorted1.bubbleSort();
		ejemplo2.bubbleSort();
	}

	@org.junit.Test
	public void testSelectionSort() {
		System.out.println("SELECTIONSORT");
		unsorted1.selectionSort();
		ejemplo2.selectionSort();
	}

	@org.junit.Test
	public void testInsertionSort() {
		System.out.println("INSERTIONSORT");
		unsorted1.insertionSort();
		ejemplo2.insertionSort();
	}

	@org.junit.Test
	public void testQuickSort() {
		System.out.println("QUICKSORT");
		unsorted1.quickSort();
		ejemplo2.quickSort();
	}
	
	@org.junit.Test
	public void testMergeSort() {
		System.out.println("MERGESORT");
		unsorted1.mergeSort(unsorted1);
		ejemplo2.mergeSort(ejemplo2);

	}
	
//	@org.junit.Test
//	public void testRadixeSort() {
//		System.out.println("RADIXSORT");
//		unsorted.radixSort(unsorted);
//	}
}
