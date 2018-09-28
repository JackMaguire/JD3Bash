package unit_tests;

import java.util.ArrayList;

import graph.*;
import output.*;

public class output_tests {

	public final static boolean run_all_output_tests() {
		boolean success = true;

		if( !test_determineOrderOfNodes() ) {
			System.err.println( "\ttest_determineOrderOfNodes failed" );
			success = false;
		} else {
			System.out.println( "\ttest_determineOrderOfNodes passed" );
		}

		if( !test_cycleDetection() ) {
			System.err.println( "\ttest_cycleDetection failed" );
			success = false;
		} else {
			System.out.println( "\ttest_cycleDetection passed" );
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
	}// test_determineOrderOfNodes

	private final static boolean test_cycleDetection() {
		boolean success = true;

		{// simple no cycle
			Graph test_graph = new Graph();
			// layout: 2->0->1->3->4
			test_graph.addNode( new Node( 0, 0, 0 ) );
			test_graph.addNode( new Node( 1, 0, 0 ) );
			test_graph.addNode( new Node( 2, 0, 0 ) );
			test_graph.addNode( new Node( 3, 0, 0 ) );
			test_graph.addNode( new Node( 4, 0, 0 ) );

			test_graph.addEdge( test_graph.getNode( 2 ), test_graph.getNode( 0 ) );
			test_graph.addEdge( test_graph.getNode( 0 ), test_graph.getNode( 1 ) );
			test_graph.addEdge( test_graph.getNode( 1 ), test_graph.getNode( 3 ) );
			test_graph.addEdge( test_graph.getNode( 3 ), test_graph.getNode( 4 ) );

			if( GraphParsing.cycleExists( test_graph ) ) {
				System.err.println( "Cycle Incorrectly Detected in 2->0->1->3->4" );
				success = false;
			}
		}

		{// simple cycle
			Graph test_graph = new Graph();
			// layout: 0->1->2->3->1
			test_graph.addNode( new Node( 0, 0, 0 ) );
			test_graph.addNode( new Node( 1, 0, 0 ) );
			test_graph.addNode( new Node( 2, 0, 0 ) );
			test_graph.addNode( new Node( 3, 0, 0 ) );

			test_graph.addEdge( test_graph.getNode( 0 ), test_graph.getNode( 1 ) );
			test_graph.addEdge( test_graph.getNode( 1 ), test_graph.getNode( 2 ) );
			test_graph.addEdge( test_graph.getNode( 2 ), test_graph.getNode( 3 ) );
			test_graph.addEdge( test_graph.getNode( 3 ), test_graph.getNode( 1 ) );

			if( !GraphParsing.cycleExists( test_graph ) ) {
				System.err.println( "Cycle Not Detected in 0->1->2->3->1" );
				success = false;
			}
		}

		return success;
	}

}
