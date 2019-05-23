package Arboles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolBPlus<t extends Comparable<t>, v>{
	public static enum RangePolicy {
		EXCLUSIVE, INCLUSIVE
	}
	private static final int FactorDeriv = 128;//el valor base que le de claro en el constructor 
	private int branchingFactor;//el factor de derivacion que me maneja la capacidad de nodos internos del arbol
	private Node root;
	
	public ArbolBPlus() {
		this(FactorDeriv);//le damos al constructor el valor base
	}
	public ArbolBPlus(int FactDerivacion) {
		if (FactDerivacion <= 2)//Siempre tiene que ser mayor que 2
			throw new IllegalArgumentException("Error con el factor de derivacion: "+ FactDerivacion);
		this.branchingFactor = FactDerivacion;
		root = new NodoHoja();
	}
	public v search(t key) {
		return root.getValue(key);
	}
	public void insert(t key, v refe) {
		root.insertValue(key, refe);
	}
	private abstract class Node {//por que lo hacemos abstract? para que sean redefinidos por las clases de nodoInterno y NodoHoja
		List<t> keys;//y tambien por que el mae del video lo explicaba asi :v

		int keyNumber() {
			return keys.size();
		}
		abstract v getValue(t key);
		abstract void insertValue(t key, v value);
		abstract t getPrimerHoja();//getPrimerHoja
		abstract List<v> getRange(t key1, RangePolicy policy1, t key2,RangePolicy policy2);
		abstract void merge(Node sibling);//unir nodo
		abstract Node split();//corte del nodo
		abstract boolean isOverflow();//si esta sobre cargado
		abstract boolean isUnderflow();//si le falta
	}

	private class NodoInterno extends Node {
		List<Node> children;

		NodoInterno() {
			this.keys = new ArrayList<t>();
			this.children = new ArrayList<Node>();
		}

		@Override
		void insertValue(t key, v value) {
			Node child = getChild(key);//insertamos el nodo en el hijo
			child.insertValue(key, value);//pero tenenmos que ver si no se sobrecarga
			if (child.isOverflow()) {
				Node sibling = child.split();
				insertChild(sibling.getPrimerHoja(), sibling);
			}
			if (root.isOverflow()) {//si se nos sobrecarga el array entero, hay que hacerle un corte
				Node cortado = split();
				NodoInterno newRoot = new NodoInterno();
				newRoot.keys.add(cortado.getPrimerHoja());
				newRoot.children.add(this);
				newRoot.children.add(cortado);
				root = newRoot;
			}
		}
		@Override
		t getPrimerHoja() {
			return children.get(0).getPrimerHoja();
		}
		@Override
		v getValue(t key) {
			return getChild(key).getValue(key);
		}
		@Override
		List<v> getRange(t key1, RangePolicy policy1, t key2,RangePolicy policy2) {
			return getChild(key1).getRange(key1, policy1, key2, policy2);
		}
		@Override
		Node split() {
			int from = keyNumber() / 2 + 1, to = keyNumber();
			NodoInterno cortado = new NodoInterno();
			cortado.keys.addAll(keys.subList(from, to));
			cortado.children.addAll(children.subList(from, to + 1));
			keys.subList(from - 1, to).clear();
			children.subList(from, to + 1).clear();
			return cortado;
		}

		@Override
		boolean isOverflow() {
			return children.size() > branchingFactor;
		}

		@Override
		boolean isUnderflow() {
			return children.size() < (branchingFactor + 1) / 2;
		}
		@Override
		void merge(Node cortado) {
			@SuppressWarnings("unchecked")
			NodoInterno node = (NodoInterno) cortado;
			keys.add(node.getPrimerHoja());
			keys.addAll(node.keys);
			children.addAll(node.children);
		}

		Node getChild(t key) {
			int loc = Collections.binarySearch(keys, key);
			int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
			return children.get(childIndex);
		}
		void insertChild(t key, Node child) {
			int loc = Collections.binarySearch(keys, key);
			int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
			if (loc >= 0) {
				children.set(childIndex, child);
			} else {
				keys.add(childIndex, key);
				children.add(childIndex + 1, child);
			}
		}
	}
	

	private class NodoHoja extends Node {
		List<v> values;
		NodoHoja next;

		NodoHoja() {
			keys = new ArrayList<t>();
			values = new ArrayList<v>();
		}

		@Override
		v getValue(t key) {
			int loc = Collections.binarySearch(keys, key);
			return loc >= 0 ? values.get(loc) : null;
		}
		@Override
		void insertValue(t key, v refe) {
			int loc = Collections.binarySearch(keys, key);
			int valueIndex = loc >= 0 ? loc : -loc - 1;
			if (loc >= 0) {
				values.set(valueIndex, refe);
			} else {
				keys.add(valueIndex, key);
				values.add(valueIndex, refe);
			}
			if (root.isOverflow()) {
				Node cortado = split();
				NodoInterno newRoot = new NodoInterno();
				newRoot.keys.add(cortado.getPrimerHoja());
				newRoot.children.add(this);
				newRoot.children.add(cortado);
				root = newRoot;
			}
		}

		@Override
		t getPrimerHoja() {
			return keys.get(0);
		}

		@Override
		List<v> getRange(t key1, RangePolicy policy1, t key2,RangePolicy policy2) {
			List<v> result = new LinkedList<v>();
			NodoHoja node = this;
			while (node != null) {
				Iterator<t> kIt = node.keys.iterator();
				Iterator<v> vIt = node.values.iterator();
				while (kIt.hasNext()) {//mae esta vara si la si esta bien fumada
					t key = kIt.next();
					v value = vIt.next();
					int cmp1 = key.compareTo(key1);
					int cmp2 = key.compareTo(key2);
					if (((policy1 == RangePolicy.EXCLUSIVE && cmp1 > 0) || (policy1 == RangePolicy.INCLUSIVE && cmp1 >= 0))
							&& ((policy2 == RangePolicy.EXCLUSIVE && cmp2 < 0) || (policy2 == RangePolicy.INCLUSIVE && cmp2 <= 0)))
						result.add(value);
					else if ((policy2 == RangePolicy.EXCLUSIVE && cmp2 >= 0)
							|| (policy2 == RangePolicy.INCLUSIVE && cmp2 > 0))
						return result;
				}
				node = node.next;
			}
			return result;
		}
		@Override
		void merge(Node cortado) {
			@SuppressWarnings("unchecked")
			NodoHoja node = (NodoHoja) cortado;
			keys.addAll(node.keys);
			values.addAll(node.values);
			next = node.next;
		}
		@Override
		Node split() {
			NodoHoja cortado = new NodoHoja();
			int from = (keyNumber() + 1) / 2, to = keyNumber();
			cortado.keys.addAll(keys.subList(from, to));
			cortado.values.addAll(values.subList(from, to));
			keys.subList(from, to).clear();
			values.subList(from, to).clear();
			cortado.next = next;
			next = cortado;
			return cortado;
		}
		@Override
		boolean isOverflow() {
			return values.size() > branchingFactor - 1;
		}
		@Override
		boolean isUnderflow() {
			return values.size() < branchingFactor / 2;
		}
	}
}
