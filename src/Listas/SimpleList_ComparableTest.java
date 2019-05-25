package Listas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class SimpleList_ComparableTest {
	private static SimpleList_Comparable<Integer> sorted1;
	private static SimpleList_Comparable<Integer> unsorted1;
	private static SimpleList_Comparable<String> sorted2;
	private static SimpleList_Comparable<String> unsorted2;
	private static SimpleList_Comparable<Double> sorted3;
	private static SimpleList_Comparable<Double> unsorted3;
	private static boolean impreso= false;
	
	@Before
	public void before() {		
		/*Prueba1*/
		sorted1 = new SimpleList_Comparable<>();
		unsorted1 = new SimpleList_Comparable<>();
    	//ordenada
		sorted1.addLast(0); sorted1.addLast(1); sorted1.addLast(2); sorted1.addLast(3); sorted1.addLast(4); sorted1.addLast(5); sorted1.addLast(6); sorted1.addLast(7); sorted1.addLast(8); sorted1.addLast(9);
    	//desordenada
		unsorted1.addLast(8); unsorted1.addLast(4); unsorted1.addLast(6); unsorted1.addLast(1); unsorted1.addLast(7); unsorted1.addLast(5); unsorted1.addLast(0); unsorted1.addLast(3); unsorted1.addLast(2); unsorted1.addLast(9);

    	/*Prueba2*/
		sorted2= new SimpleList_Comparable<>();
		unsorted2= new SimpleList_Comparable<>();
    	//ordenada
		sorted2.addLast("c++"); sorted2.addLast("hola"); sorted2.addLast("java");sorted2.addLast("mundo"); sorted2.addLast("python");
		//desordenada
		unsorted2.addLast("python"); unsorted2.addLast("c++"); unsorted2.addLast("java"); unsorted2.addLast("hola"); unsorted2.addLast("mundo");

    	/*Prueba3*/
		sorted3= new SimpleList_Comparable<>();
		unsorted3= new SimpleList_Comparable<>();
    	//ordenada
		sorted3.addLast(0.0); sorted3.addLast(20.0); sorted3.addLast(21.2);sorted3.addLast(21.201); sorted3.addLast(21.202); sorted3.addLast(40.5); sorted3.addLast(50.03);
		//desordenada
		unsorted3.addLast(21.2); unsorted3.addLast(0.0); unsorted3.addLast(21.201); unsorted3.addLast(21.202); unsorted3.addLast(20.0); unsorted3.addLast(50.03); unsorted3.addLast(40.5);

		
		/*Prints*/
		if (!impreso) { impreso =true;
		System.out.println("****LISTAS****");
		
//		sorted1.print();
		
    	System.out.print("Desordenada : ");
    	unsorted1.print();
    	
//		sorted2.print();
    	
    	System.out.print("Desordenada : ");
    	unsorted2.print();
    	
//		sorted3.print();
    	
    	System.out.print("Desordenada : ");
		unsorted3.print();
		
    	System.out.print("\n");
		}
	}

	@After
	public void after() {
    	for (int i=0; i<sorted1.getLargo(); i++) {
    		assertEquals(sorted1.get(i), unsorted1.get(i));
    	}
	for (int i=0; i<sorted2.getLargo(); i++) {
    		assertEquals(sorted2.get(i), unsorted2.get(i));
    	}
	}

	@org.junit.Test
	public void testBubbleSort() {
		System.out.println("BUBBLESORT");
		unsorted1.bubbleSort();
		unsorted2.bubbleSort();
		unsorted3.bubbleSort();
		unsorted1.print();
		unsorted2.print();
		unsorted3.print();
    	System.out.print("\n");
	}

	@org.junit.Test
	public void testSelectionSort() {
		System.out.println("SELECTIONSORT");
		unsorted1.selectionSort();
		unsorted2.selectionSort();
		unsorted3.selectionSort();
		unsorted1.print();
		unsorted2.print();
		unsorted3.print();
    	System.out.print("\n");
	}

	@org.junit.Test
	public void testInsertionSort() {
		System.out.println("INSERTIONSORT");
		unsorted1.insertionSort();
		unsorted2.insertionSort();
		unsorted3.insertionSort();
		unsorted1.print();
		unsorted2.print();
		unsorted3.print();
		System.out.print("\n");
	}

	@org.junit.Test
	public void testQuickSort() {
		System.out.println("QUICKSORT");
		unsorted1.quickSort();
		unsorted2.quickSort();
		unsorted3.quickSort();
		unsorted1.print();
		unsorted2.print();
		unsorted3.print();
		System.out.print("\n");
	}
	
	@org.junit.Test
	public void testMergeSort() {
		System.out.println("MERGESORT");
		unsorted1.mergeSort(unsorted1);
		unsorted2.mergeSort(unsorted2);
		unsorted3.mergeSort(unsorted3);
		unsorted1.print();
		unsorted2.print();
		unsorted3.print();
		System.out.print("\n");
	}
	
//	@org.junit.Test
//	public void testRadixeSort() {
//		System.out.println("RADIXSORT");
//		unsorted.radixSort(unsorted);
//	}
}
