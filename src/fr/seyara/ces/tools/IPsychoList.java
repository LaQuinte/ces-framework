package fr.seyara.ces.tools;

public interface IPsychoList<T> {
	public int size();
	public T get(int index);
	public void set(T elem, int i);
	public int add(T elem);
	public int capacity();
}
