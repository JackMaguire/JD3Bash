package apps.proof_of_concept;

import javax.swing.JFrame;

import global_data.GlobalViewData;
import graph.Edge;
import graph.Graph;
import graph.Node;
import views.meta.MainView;

public class ViewSimpleGraph {

	public static void main( String[] args ) {
		Graph simple_graph = new Graph();
		Node n0 = simple_graph.addNode( new Node( "Dock", 20, 10 ) );
		Node n1 = simple_graph.addNode( new Node( "HBNet", 20, 30 ) );
		Edge e1 = simple_graph.addEdge( n0, n1 );

		e1.setColumnNameToSortBy( "total_score" );
		e1.setPercentageOfResultsToTransfer( 0.25 );
		e1.setUsePercentageInsteadOfCount( true );

		JFrame F = new JFrame( "ViewSimpleGraph" );
		F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		F.setExtendedState( JFrame.MAXIMIZED_BOTH );

		MainView view = new MainView( simple_graph );
		GlobalViewData.top_panel = view;

		F.add( GlobalViewData.top_panel );
		F.setVisible( true );
	}

}
