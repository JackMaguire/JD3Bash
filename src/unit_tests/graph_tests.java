package unit_tests;

import java.awt.Color;

import graph.Edge;
import graph.Graph;
import graph.Node;

public class graph_tests {

	public final static boolean run_all_graph_tests() {
		boolean success = true;

		if( !test_SimpleGeometry() ) {
			System.err.println( "\ttest_SimpleGeometry() failed" );
			success = false;
		} else {
			System.out.println( "\ttest_SimpleGeometry() passed" );
		}
		
		if( !test_SaveAndLoad() ) {
			System.err.println( "\ttest_SaveAndLoad() failed" );
			success = false;
		} else {
			System.out.println( "\ttest_SaveAndLoad() passed" );
		}

		return success;
	}

	private final static boolean test_SimpleGeometry() {
		boolean success = true;

		{// Simple case
			Graph test_graph = new Graph();
			Node node0 = new Node( 0, 0 );
			Node node1 = new Node( 1, 1 );
			Node node2 = new Node( 2, 2 );
			test_graph.addNode( node0 );
			test_graph.addNode( node1 );
			test_graph.addNode( node2 );

			// 2 -> 0 -> 1

			test_graph.addEdge( node2, node0 );
			test_graph.addEdge( node0, node1 );

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
		}

		return success;
	}// test_SimpleGeometry

	
	private final static boolean test_SaveAndLoad() {
		boolean success = true;
		
		// 0->2
		// 1->2
		// 2->3
		// 2->4
		
		final Graph original_graph = new Graph();
		final Node[] nodes = new Node[ 4 ];
		for( int i=0; i< nodes.length; ++i ) {
			nodes[ i ] = new Node( i, i );
			original_graph.addNode( nodes[ i ] );
		}
		
		final Edge edge02 = original_graph.addEdge( nodes[ 0 ], nodes[ 2 ] );
		final Edge edge12 = original_graph.addEdge( nodes[ 1 ], nodes[ 2 ] );
		final Edge edge23 = original_graph.addEdge( nodes[ 2 ], nodes[ 3 ] );
		final Edge edge24 = original_graph.addEdge( nodes[ 2 ], nodes[ 4 ] );
		
		edge02.setColumnNameToSortBy( "UNIT_TEST" );
		edge12.setNotes( "UNIT\nTEST" );
		edge23.setNumResultsToTransfer( 23 );
		edge23.setPositiveScoresAreBetter( true );
		edge24.setPercentageOfResultsToTransfer( 0.25 );
		edge24.setUsePercentageInsteadOfCount( true );
		
		nodes[ 0 ].setColor( new Color( 1, 2, 3 ) );
		nodes[ 1 ].setCommand( "../unit_test.sh" );
		nodes[ 2 ].setNotes( "UNIT TEST" );
		nodes[ 3 ].setScript( "unit_test.xml" );
		nodes[ 4 ].setTitle( "unit_test" );
		nodes[ 0 ].addUserRosettaFlag( "-unit test" );
		nodes[ 1 ].setX( 100 );
		nodes[ 1 ].setY( 200 );
		
		return success;
	}
}
