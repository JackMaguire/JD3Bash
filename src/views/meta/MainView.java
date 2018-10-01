package views.meta;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import controllers.GraphController;
import graph.*;
import views.EdgeView;
import views.GraphView;
import views.NodeView;

public class MainView extends JPanel {

	/**
	 * Generated By Eclipse
	 */
	private static final long serialVersionUID = -4453877907614869696L;

	private final Graph graph_;
	private final GraphView graph_view_;

	private Node selected_node_;

	private NodeView node_view_;
	private EdgeView edge_view_ = null;

	private final JSplitPane main_panel_;

	public MainView( Graph g ) {
		graph_ = g;
		GraphController graph_controller = new GraphController( g );
		graph_view_ = graph_controller.view();
		Dimension minimum_size_for_graph_view = new Dimension( 500, getHeight() / 2 );
		graph_view_.setMinimumSize( minimum_size_for_graph_view );

		if( graph_.selectedNode() == null ) {
			if( graph_.getNumNodes() == 0 ) {
				graph_.addNode( new Node( 0, "First Node" ) );
			}
			graph_.setSelectedNode( graph_.getNode( 0 ) );
		}
		selected_node_ = graph_.selectedNode();
		node_view_ = new NodeView( selected_node_ );

		main_panel_ = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, graph_view_, node_view_ );
		main_panel_.setOneTouchExpandable( true );
		main_panel_.setContinuousLayout( true );

		// Look into this:
		// main_panel_.setAutoscrolls( true );

		this.setLayout( new GridLayout( 1, 1 ) );
		this.add( main_panel_ );
	}

	@Override
	public void repaint() {

		if( graph_view_ != null ) {
			Dimension minimum_size_for_graph_view = new Dimension( getWidth() / 4, getHeight() / 2 );
			graph_view_.setMinimumSize( minimum_size_for_graph_view );
		}

		if( graph_ != null ) {
			Node currently_selected_node = graph_.selectedNode();
			if( currently_selected_node != selected_node_ ) {

				selected_node_ = graph_.selectedNode();
				int current_divider_loc = main_panel_.getDividerLocation();

				if( selected_node_ != null ) {
					main_panel_.remove( main_panel_.getRightComponent() );
					node_view_ = new NodeView( selected_node_ );
					main_panel_.setRightComponent( node_view_ );
					main_panel_.setDividerLocation( current_divider_loc );
				} else {
					// EdgeView
					main_panel_.remove( main_panel_.getRightComponent() );
					edge_view_ = new EdgeView( graph_.selectedEdge() );
					main_panel_.setRightComponent( edge_view_ );
					main_panel_.setDividerLocation( current_divider_loc );

				}
			}
		}

		super.repaint();
	}

}
