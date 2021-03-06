package apps.proof_of_concept;

import compiler.GraphParsing;
import compiler.GraphParsingOptions;
import exceptions.InvalidGraphException;
import graph.Edge;
import graph.Graph;
import graph.Node;
import utility.Pair;

public class PrintStringsForSimpleGraph {

	public static void main( String[] args ) {
		Graph simple_graph = new Graph();
		Node n0 = simple_graph.addNode( new Node( "Dock", 0, 0 ) );
		Node n1 = simple_graph.addNode( new Node( "HBNet", 10, 10 ) );
		Node n2 = simple_graph.addNode( new Node( "Design", 20, 20 ) );

		Edge e1 = simple_graph.addEdge( n0, n1 );
		Edge e2 = simple_graph.addEdge( n1, n2 );

		e1.setColumnNameToSortBy( "interchain_cen" );
		e1.setUsePercentageInsteadOfCount( false );
		e1.setNumResultsToTransfer( 100 );

		e2.setColumnNameToSortBy( "total_score" );
		e2.setPercentageOfResultsToTransfer( 0.25 );
		e2.setUsePercentageInsteadOfCount( true );

		for( Node n : simple_graph.allNodes_const() ) {
			n.setUserRosettaFlags( n.determineAutoFlags() );
		}

		Pair< String, String > scripts = null;
		try {
			GraphParsingOptions options = new GraphParsingOptions();
			scripts = GraphParsing.parseGraph( simple_graph, options );
		}
		catch( InvalidGraphException e ) {
			System.err.println(
					"PrintStringsForSimpleGraph seems to be out of date. Its toy graph is unparsable" );
			e.printStackTrace();
			return;
		}

		String run_script = scripts.first;
		String setup_script = scripts.second;

		System.out.println( "Run Script:\n" + run_script );
		System.out.println( "Setup Script:\n" + setup_script );
	}

}
