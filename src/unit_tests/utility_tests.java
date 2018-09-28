package unit_tests;

import utility.PBRWrapper;

public class utility_tests {

	public final static boolean run_all_output_tests() {
		boolean success = true;
		
		if( !test_PBRWrapper() ) {
			System.err.println( "\ttest_PBRWrapper failed" );
			success = false;
		} else {
			System.out.println( "\ttest_PBRWrapper passed" );
		}
		
		return success;
	}
	
	private final static boolean test_PBRWrapper() {
		boolean success = true;
		
		PBRWrapper< String > string_pbrw = new PBRWrapper< String >( "1" );
		test_PBRWrapper_helper1( string_pbrw );
		if( ! string_pbrw.value.equals( "12" ) ) {
			System.err.println( "string_pbrw gave " + string_pbrw.value + " instead of 12" );
			success = false;
		}
		
		PBRWrapper< Integer > int_pbrw = new PBRWrapper< Integer >( 1 );
		test_PBRWrapper_helper2( int_pbrw );
		if( int_pbrw.value != 3 ) {
			System.err.println( "int_pbrw gave " + int_pbrw.value + " instead of 3" );
			success = false;
		}
		
		return success;
	}

	private final static void test_PBRWrapper_helper1(PBRWrapper< String > string_pbrw) {
		string_pbrw.value += "2";
	}
	
	private final static void test_PBRWrapper_helper2(PBRWrapper< Integer > string_pbrw) {
		string_pbrw.value += 2;
	}
	
}
