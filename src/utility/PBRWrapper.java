package utility;
//Wrapper that allows you to pass by reference
public class PBRWrapper<T> {
	public T value;
}
/* Intended usage:
 * 
 * foo() {
 *  Wrapper< String > w = new Wrapper< String >()
 *  bar( w );
 *  System.out.println( w.value ); //-> "I just visited bar"
 * }
 * 
 * bar( Wrapper< String > ref_to_string ) {
 * 	ref_to_string.value = "I just visited bar"
 * }
 */
