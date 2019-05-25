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
	
	private static SimpleList_Comparable<Integer> sorted4;
	private static SimpleList_Comparable<Integer> unsorted4;
	
	private static SimpleList_Comparable<Integer> sorted5;
	private static SimpleList_Comparable<Integer> unsorted5;
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

		/*Prueba 4*/
		sorted4= new SimpleList_Comparable<>();
		unsorted4= new SimpleList_Comparable<>();
		//ordenada
		sorted4.addLast(5);sorted4.addLast(12);sorted4.addLast(45);sorted4.addLast(47);sorted4.addLast(287);sorted4.addLast(299);
		//desordenada
		unsorted4.addLast(12);unsorted4.addLast(45);unsorted4.addLast(299);unsorted4.addLast(5);unsorted4.addLast(47);unsorted4.addLast(287);
		
		/*Prueba 5*/
		sorted5= new SimpleList_Comparable<>();
		unsorted5= new SimpleList_Comparable<>();
		//ordenada
		sorted5.addLast(-299);sorted5.addLast(-56);sorted5.addLast(-5);sorted5.addLast(42);sorted5.addLast(287);sorted5.addLast(300);
		//desordenada
		unsorted5.addLast(287);unsorted5.addLast(42);unsorted5.addLast(-56);unsorted5.addLast(-5);unsorted5.addLast(300);unsorted5.addLast(-299);
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
		
		System.out.print("Desordenada : ");
		unsorted4.print();
		
		System.out.print("Desordenada : ");
		unsorted5.print();
		
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
    	for (int i=0; i<sorted2.getLargo(); i++) {
    		assertEquals(sorted2.get(i), unsorted2.get(i));
    	}
    	for (int i=0; i<sorted3.getLargo(); i++) {
    		assertEquals(sorted3.get(i), unsorted3.get(i));
    	}
    	for (int i=0; i<sorted4.getLargo(); i++) {
    		assertEquals(sorted4.get(i), unsorted4.get(i));
    	}
    	for (int i=0; i<sorted5.getLargo(); i++) {
    		assertEquals(sorted5.get(i), unsorted5.get(i));
    	}
    	
	}

	@org.junit.Test
	public void testBubbleSort() {
		System.out.println("BUBBLESORT");
		unsorted1.bubbleSort();
		unsorted2.bubbleSort();
		unsorted3.bubbleSort();
		unsorted4.bubbleSort();
		unsorted5.bubbleSort();
		unsorted1.print();
		unsorted2.print();
		unsorted3.print();
		unsorted4.print();
		unsorted5.print();
    	System.out.print("\n");
	}

	@org.junit.Test
	public void testSelectionSort() {
		System.out.println("SELECTIONSORT");
		unsorted1.selectionSort();
		unsorted2.selectionSort();
		unsorted3.selectionSort();
		unsorted4.selectionSort();
		unsorted5.selectionSort();
		unsorted1.print();
		unsorted2.print();
		unsorted3.print();
		unsorted4.print();
		unsorted5.print();
    	System.out.print("\n");
	}

	@org.junit.Test
	public void testInsertionSort() {
		System.out.println("INSERTIONSORT");
		unsorted1.insertionSort();
		unsorted2.insertionSort();
		unsorted3.insertionSort();
		unsorted4.insertionSort();
		unsorted5.insertionSort();
		unsorted1.print();
		unsorted2.print();
		unsorted3.print();
		unsorted4.print();
		unsorted5.print();
		System.out.print("\n");
	}

	@org.junit.Test
	public void testQuickSort() {
		System.out.println("QUICKSORT");
		unsorted1.quickSort();
		unsorted2.quickSort();
		unsorted3.quickSort();
		unsorted4.quickSort();
		unsorted5.quickSort();
		unsorted1.print();
		unsorted2.print();
		unsorted3.print();
		unsorted4.print();
		unsorted5.print();
		System.out.print("\n");
	}
	
	@org.junit.Test
	public void testMergeSort() {
		System.out.println("MERGESORT");
		unsorted1.mergeSort(unsorted1);
		unsorted2.mergeSort(unsorted2);
		unsorted3.mergeSort(unsorted3);
		unsorted4.mergeSort(unsorted4);
		unsorted5.mergeSort(unsorted5);
		unsorted1.print();
		unsorted2.print();
		unsorted3.print();
		unsorted4.print();
		unsorted5.print();
		System.out.print("\n");
	}
	
	@org.junit.Test
	public void testRadixeSort() {
		System.out.println("RADIXSORT");
		unsorted1.radixsort(unsorted1);
		unsorted4.radixsort(unsorted4);
		//unsorted5.radixsort(unsorted5);
		unsorted1.print();
		unsorted4.print();
		//unsorted5.print();
		System.out.print("\n");
	}
}
