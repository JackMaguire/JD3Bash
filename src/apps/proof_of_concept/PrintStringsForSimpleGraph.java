package apps.proof_of_concept;

import exceptions.InvalidGraphException;
import graph.*;
import output.GraphParsing;
import utility.Pair;

public class PrintStringsForSimpleGraph {

	public static void main( String[] args ) {
		Graph simple_graph = new Graph();
		Node n0 = simple_graph.addNode( new Node( 0, "Dock" ) );
		Node n1 = simple_graph.addNode( new Node( 1, "HBNet" ) );
		Node n2 = simple_graph.addNode( new Node( 2, "Design" ) );

		Edge e1 = simple_graph.addEdge( n0, n1 );
		Edge e2 = simple_graph.addEdge( n1, n2 );

		e1.setColumnNameToSortBy( "interchain_cen" );
		e1.setUsePercentageInsteadOfCount( false );
		e1.setNumResultsToTransfer( 100 );

		e2.setColumnNameToSortBy( "total_score" );
		e2.setPercentageOfResultsToTransfer( 0.25 );
		e2.setUsePercentageInsteadOfCount( true );

		for( Node n : simple_graph.allNodes_const() ) {
			n.setAllRosettaFlags( n.determineAutoFlags() );
		}

		Pair< String, String > scripts = null;
		try {
			scripts = GraphParsing.parseGraph( simple_graph );
		}
		catch( InvalidGraphException e ) {
			System.err.println( "PrintStringsForSimpleGraph seems to be out of date. Its toy graph is unparsable" );
			e.printStackTrace();
			return;
		}

		String run_script = scripts.first;
		String setup_script = scripts.second;

		System.out.println( "Run Script:\n" + run_script );
		System.out.println( "Setup Script:\n" + setup_script );
	}

}
