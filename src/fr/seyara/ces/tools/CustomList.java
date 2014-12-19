package fr.seyara.ces.tools;

/**
 * Multiple usage list which allow elements to stay close or mapped list.
 * @author Spoke
 *
 * @param <T>
 */
public class CustomList<T> implements IPsychoList<T> {
	private int size = 0;
	private T[] items;

	/**
	 * Default construct which create a new array with defined size
	 */
	public CustomList(){
		this(92);
	}
	
	@SuppressWarnings("unchecked")
	public CustomList(int nbr){
		items = (T[]) new Object[nbr];
	}
	
	// use for elements which need to be close in the list (fast access)
	public int add(T element){
		if(size >= items.length) // si on approche de la fin, on redimensionne la liste
			resize(size + size / 2 + 1);
		
		items[size++] = element;
		return size - 1;
	}
	
	public boolean remove(T element){
		for(int i = 0; i < size; i++){
			T temp = items[i];
			
			if(temp == element){
				items[i] = items[--size];
				items[size] = null;				
				return true;
			}
		}
		return false;
	}
	
	// use of mapped list
	public void set(T item, int i){
		if(i >= items.length)
			resize(i * 2);
		items[i] = item;
		size = i + 1;
	}
	
	public void removeAll(){
		for(int i = 0; i < size; i++)
			items[i] = null;
		size = 0;
	}
	
	public void addAll(CustomList<T> elems){
		for(int i = 0; i < elems.size(); i++)
			add(elems.get(i));
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int newSize){
		T[] temp = items;
		items = (T[]) new Object[newSize];
		System.arraycopy(temp, 0, items, 0, temp.length);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public int capacity(){
		return items.length;
	}

	@Override
	public T get(int index) {
		if(index < items.length)
			return items[index];
		return null;
	}
	
	public T removeLast(){
		if(size == 0)
			return null;
		
		T temp = items[--size];
		items[size] = null;
		return temp;
	}
	
	public boolean isIn(int index){
		return index < items.length;
	}
}
