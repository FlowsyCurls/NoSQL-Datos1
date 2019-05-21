package Listas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class SimpleList_ComparableTest {
	SimpleList_Comparable<Integer> sorted;
	SimpleList_Comparable<Integer> unsorted;
	
	@Before
	public void before() {
		System.out.println("Before");
		sorted = new SimpleList_Comparable<>();
		unsorted = new SimpleList_Comparable<>();
		
		unsorted.addLast(8); unsorted.addLast(4); unsorted.addLast(6); unsorted.addLast(1);
    	unsorted.addLast(7); unsorted.addLast(5); unsorted.addLast(0); unsorted.addLast(3);
    	unsorted.addLast(2); unsorted.addLast(9);
    	
    	sorted.addLast(0); sorted.addLast(1); sorted.addLast(2); sorted.addLast(3);
    	sorted.addLast(4); sorted.addLast(5); sorted.addLast(6); sorted.addLast(7);
    	sorted.addLast(8); sorted.addLast(9);
	}
	
	@After
	public void after() {
		System.out.println("Termina un test");
	}

	@Test
	public void testBubbleSort() {
		unsorted.bubbleSort();
    	SimpleList_Comparable<Integer> resultado = unsorted;
    	SimpleList_Comparable<Integer> esperado = sorted;
    	unsorted.print();
    	sorted.print();
    	assertEquals(esperado, resultado);
	}
	
	@Test
	public void testSelectionSort() {
		unsorted.selectionSort();
    	SimpleList_Comparable<Integer> resultado = unsorted;
    	SimpleList_Comparable<Integer> esperado = sorted;
    	unsorted.print();
    	sorted.print();
    	assertEquals(esperado, resultado);
	}
	
	@Test
	public void testInsertionSort() {
		unsorted.insertionSort();
    	SimpleList_Comparable<Integer> resultado = unsorted;
    	SimpleList_Comparable<Integer> esperado = sorted;
    	unsorted.print();
    	sorted.print();
    	assertEquals(esperado, resultado);
	}
	
	@Test
	public void testQuickSort() {
		unsorted.quickSort();
    	SimpleList_Comparable<Integer> resultado = unsorted;
    	SimpleList_Comparable<Integer> esperado = sorted;
    	unsorted.print();
    	sorted.print();
    	assertEquals(esperado, resultado);
	}

}
