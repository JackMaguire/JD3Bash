package apps.proof_of_concept;

import java.awt.Color;

import javax.swing.JFrame;

import graph.Edge;
import graph.Graph;
import graph.Node;
import views.GlobalData;
import views.meta.MainView;

public class ViewSimpleGraph {

	public static void main( String[] args ) {
		Graph simple_graph = new Graph();
		Node n0 = simple_graph.addNode( new Node( 0, "Dock", 0, 30 ) );
		Node n1 = simple_graph.addNode( new Node( 1, "HBNet", 20, 10 ) );
		Node n2 = simple_graph.addNode( new Node( 2, "Design", 30, 30 ) );

		n1.setColor( Color.RED );

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

		JFrame F = new JFrame( "ViewSimpleGraph" );
		F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		F.setExtendedState( JFrame.MAXIMIZED_BOTH );

		/*
		GraphController graph_controller = new GraphController( simple_graph );
		GlobalData.top_panel = graph_controller.view();
		*/
		MainView view = new MainView( simple_graph );
		GlobalData.top_panel = view;

		F.add( GlobalData.top_panel );
		F.setVisible( true );
	}

}
