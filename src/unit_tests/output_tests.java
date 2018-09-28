package unit_tests;

import java.util.ArrayList;

import graph.*;
import output.*;

public class output_tests {

	public final static boolean run_all_output_tests() {
		boolean success = true;

		if( !test_determineOrderOfNodes() ) {
			System.err.println( "test_determineOrderOfNodes failed" );
			success = false;
		}

		return success;
	}

	private final static boolean test_determineOrderOfNodes() {
		boolean success = true;

		{// Simple case
			Graph test_graph = new Graph();
			// layout: 2->0->1, should return {2,0,1}
			test_graph.addNode( new Node( 0, 0, 0 ) );
			test_graph.addNode( new Node( 1, 0, 0 ) );
			test_graph.addNode( new Node( 2, 0, 0 ) );

			test_graph.addEdge( test_graph.getNode( 2 ), test_graph.getNode( 0 ) );
			test_graph.addEdge( test_graph.getNode( 0 ), test_graph.getNode( 1 ) );

			ArrayList< Node > list = GraphParsing.determineOrderOfNodes( test_graph );
			if( list.get( 0 ).id() != 2 || list.get( 1 ).id() != 0 || list.get( 2 ).id() != 1 ) {
				System.err.print( "test_determineOrderOfNodes Simple Case failed. Order given is" );
				for( Node n : list ) {
					System.err.print( " " + n.id() );
				}
				System.err.println( " instead of 2 0 1" );
				success = false;
			}
		}

		return success;
	}

}
