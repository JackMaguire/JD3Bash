package utility;
//Wrapper that allows you to pass by reference
public class PBRWrapper<T> {
	public T value;
	
	public PBRWrapper(){}
	public PBRWrapper( T setting ){ value = setting; }
}
/* Intended usage:
 * 
 * foo() {
 *  Wrapper< String > w = new Wrapper< String >( "I just visited a " )
 *  bar( w );
 *  System.out.println( w.value ); //-> "I just visited a bar"
 * }
 * 
 * bar( Wrapper< String > ref_to_string ) {
 * 	ref_to_string.value += "bar"
 * }
 */
