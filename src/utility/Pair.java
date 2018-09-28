package utility;

//Meant to match std::pair;
public class Pair<T,V> {
	public T first;
	public V second;
	
	public Pair() {}
	public Pair( T t, V v ) {
		first = t;
		second = v;
	}
}
