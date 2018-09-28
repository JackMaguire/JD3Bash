package unit_tests;

public class run_unit_tests {

	public static void main( String[] args ) {
		if( !graph_tests.run_all_graph_tests() ) {
			System.err.println( "graph_tests failed. Stopping unit tests" );
			return;
		} else {
			System.out.println( "graph_tests passed." );
		}

		if( !output_tests.run_all_output_tests() ) {
			System.err.println( "output_tests failed. Stopping unit tests" );
			return;
		} else {
			System.out.println( "output_tests passed." );
		}
		
		if( !utility_tests.run_all_output_tests() ) {
			System.err.println( "utility_tests failed. Stopping unit tests" );
			return;
		} else {
			System.out.println( "utility_tests passed." );
		}

	}

}
