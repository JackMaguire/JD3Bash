package output;

import graph.*;
import utility.PBRWrapper;

import java.util.ArrayList;
import java.util.HashSet;

import exceptions.InvalidGraphException;

/*  This can be as slow as we want it to be.
 *  Let's focus more on clarity and correctness than speed here.
 */

public class GraphParsing {

	public static String parseGraph( Graph g ) throws InvalidGraphException {
		if( cycleExists(g) ) {
			throw new InvalidGraphException( "Cycle Detected In Graph" );
		}
		String script = "";
		PBRWrapper< String > run_script = new PBRWrapper< String >();
		
		
		ArrayList< Node > nodes_in_order = determineOrderOfNodes( g );
		for( int stage = 1; stage <= nodes_in_order.size(); ++stage ) {
			Node n = nodes_in_order.get( stage - 1 );//We are 1-indexing the stages
			createInstructionsForNode( n, stage );
		}
		
		return script;
	}
	
	public static ArrayList< Node > determineOrderOfNodes( Graph g ) {
		ArrayList< Node > unassigned_nodes = new ArrayList< Node >();
		ArrayList< Node > assigned_nodes_in_order = new ArrayList< Node >();

		for( Node n : g.Nodes() ) {
			unassigned_nodes.add( n );
		}

		while ( true ) {
			// Add nodes that do not depend on any unassigned node
			// Work backwards so that we can easily delete while we work
			for( int j = unassigned_nodes.size() - 1; j >= 0; --j ) {
				Node u_node = unassigned_nodes.get( j );
				if( u_node.inDegreeIgnoringTheseNodes( assigned_nodes_in_order ) == 0 ) {
					assigned_nodes_in_order.add( u_node );
					unassigned_nodes.remove( j );
				}
			}

			if( unassigned_nodes.size() == 0 ) {
				break;
			}
		}

		return assigned_nodes_in_order;
	}

	public static boolean cycleExists( Graph g ) {
		//Recursion
		HashSet< Node > nodes_already_visited = new HashSet< Node >();
		for( Node n : g.Nodes() ) {
			if( cycleExists(n, nodes_already_visited) ) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean cycleExists( Node starting_node, HashSet< Node > nodes_already_visited ) {
		if( nodes_already_visited.contains( starting_node ) ) {
			return true;
		}
		nodes_already_visited.add( starting_node );
		for( Edge e : starting_node.downstreamEdges() ) {
			if( cycleExists(e.destinationNode(), nodes_already_visited) ) {
				return true;
			}
		}
		nodes_already_visited.remove( starting_node );
		return false;
	}
	
	private static void createInstructionsForNode( Node n, int stage ){
		String out = "";
		out += "mkdir stage" + stage + "_" + n.title() ;
	}
	
}
