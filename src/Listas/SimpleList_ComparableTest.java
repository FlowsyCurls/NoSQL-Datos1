package Listas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class SimpleList_ComparableTest {
	private static SimpleList_Comparable<Integer> sorted;
	private static SimpleList_Comparable<Integer> unsorted;
	
	@Before
	public void before() {
		System.out.println("\nEmpieza un test");
		sorted = new SimpleList_Comparable<>();
		unsorted = new SimpleList_Comparable<>();

		unsorted.addLast(8); unsorted.addLast(4); unsorted.addLast(6); unsorted.addLast(1);
    	unsorted.addLast(7); unsorted.addLast(5); unsorted.addLast(0); unsorted.addLast(3);
    	unsorted.addLast(2); unsorted.addLast(9);

    	sorted.addLast(0); sorted.addLast(1); sorted.addLast(2); sorted.addLast(3);
    	sorted.addLast(4); sorted.addLast(5); sorted.addLast(6); sorted.addLast(7);
    	sorted.addLast(8); sorted.addLast(9);
    	unsorted.print();
    	sorted.print();
	}

	@After
	public void after() {
		System.out.println("Termina un test\n");
	}

	@org.junit.Test
	public void testBubbleSort() {
		System.out.println("BUBBLESORT");
		unsorted.bubbleSort();
    	SimpleList_Comparable<Integer> resultado = unsorted;
    	SimpleList_Comparable<Integer> esperado = sorted;
    	unsorted.print();
    	sorted.print();
	}

	@org.junit.Test
	public void testSelectionSort() {
		System.out.println("SELECTIONSORT");
		unsorted.selectionSort();
    	SimpleList_Comparable<Integer> resultado = unsorted;
    	SimpleList_Comparable<Integer> esperado = sorted;
    	unsorted.print();
    	sorted.print();
	}

	@org.junit.Test
	public void testInsertionSort() {
		System.out.println("INSERTIONSORT");
		unsorted.insertionSort();
    	SimpleList_Comparable<Integer> resultado = unsorted;
    	SimpleList_Comparable<Integer> esperado = sorted;
    	unsorted.print();
    	sorted.print();
	}

	@org.junit.Test
	public void testQuickSort() {
		System.out.println("QUICKSORT");
		unsorted.quickSort();
    	SimpleList_Comparable<Integer> resultado = unsorted;
    	SimpleList_Comparable<Integer> esperado = sorted;
    	unsorted.print();
    	sorted.print();
	}
	
	@org.junit.Test
	public void testMergeSort() {
		System.out.println("MERGESORT");
		unsorted.mergesort(unsorted);
		SimpleList_Comparable<Integer> resultado = unsorted;
		SimpleList_Comparable<Integer> esperado = sorted;
		unsorted.print();
		sorted.print();
	}



//	@org.junit.Test
//	public void bubbleSort() {
//				unsorted.bubbleSort();
//				unsorted.print();
//	}
//
//	@org.junit.Test
//	public void selectionSort() {
//	}
//
//	@org.junit.Test
//	public void insertionSort() {
//	}
//
//	@org.junit.Test
//	public void quickSort() {
//	}
//

}
