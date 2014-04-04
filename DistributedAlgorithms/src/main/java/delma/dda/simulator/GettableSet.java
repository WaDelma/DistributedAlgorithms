package delma.dda.simulator;

import java.util.HashMap;
import java.util.Iterator;

public class GettableSet<T>{
	private HashMap<Class<?>, T> map;

	public GettableSet() {
		map = new HashMap<>();
	}

	public boolean add(T t) {
		map.put(t.getClass(), t);
		return true;
	}

	public void clear() {
		map.clear();
	}

	public boolean contains(Class<? extends T> clazz) {
		return map.containsKey(clazz);
	}

	public boolean contains(T t) {
		return map.containsValue(t);
	}

	@SuppressWarnings("unchecked")
	public <E extends T> E get(Class<? extends E> clazz) {
		return (E) map.get(clazz);
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Iterator<T> iterator() {
		return map.values().iterator();
	}

	public boolean remove(Object o) {
		map.remove(o);
		return true;
	}

	public int size() {
		return map.size();
	}

	@Override
	public String toString() {
		return map.values().toString();
	}

}
