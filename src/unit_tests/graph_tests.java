package unit_tests;

import graph.Graph;
import graph.Node;

public class graph_tests {

	public final static boolean run_all_graph_tests() {
		boolean success = true;

		if( !test_SimpleGeometry() ) {
			System.err.println( "test_SimpleGeometry() failed" );
			success = false;
		}

		return success;
	}

	private final static boolean test_SimpleGeometry() {
		boolean success = true;

		{// Simple case
			Graph test_graph = new Graph();
			Node node0 = new Node( 0, 0, 0 );
			Node node1 = new Node( 1, 1, 1 );
			Node node2 = new Node( 2, 2, 2 );
			test_graph.addNode( node0 );
			test_graph.addNode( node1 );
			test_graph.addNode( node2 );

			// 2 -> 0 -> 1

			test_graph.addEdge( test_graph.getNode( 2 ), test_graph.getNode( 0 ) );
			test_graph.addEdge( test_graph.getNode( 0 ), test_graph.getNode( 1 ) );

			if( node0.numUpstreamEdges() != 1 ) {
				System.err.println( "node0.numUpstreamEdges() == " + node0.numUpstreamEdges() + " instead of 1" );
				success = false;
			}

			if( node0.numDownstreamEdges() != 1 ) {
				System.err.println( "node0.numDownstreamEdges() == " + node0.numDownstreamEdges() + " instead of 1" );
				success = false;
			}

			if( node1.numUpstreamEdges() != 1 ) {
				System.err.println( "node1.numUpstreamEdges() == " + node1.numUpstreamEdges() + " instead of 1" );
				success = false;
			}

			if( node1.numDownstreamEdges() != 0 ) {
				System.err.println( "node1.numDownstreamEdges() == " + node1.numDownstreamEdges() + " instead of 0" );
				success = false;
			}

			if( node2.numUpstreamEdges() != 0 ) {
				System.err.println( "node2.numUpstreamEdges() == " + node2.numUpstreamEdges() + " instead of 0" );
				success = false;
			}

			if( node2.numDownstreamEdges() != 1 ) {
				System.err.println( "node2.numDownstreamEdges() == " + node2.numDownstreamEdges() + " instead of 1" );
				success = false;
			}

			if( !success ) {
				return false;
			}
		}

		return success;
	}// test_SimpleGeometry
	
}
