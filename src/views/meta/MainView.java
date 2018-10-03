package views.meta;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import controllers.GraphController;
import graph.*;
import views.CompileView;
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
	private Edge selected_edge_ = null;

	private NodeView node_view_;
	private EdgeView edge_view_ = null;

	private final CompileView compile_view_;

	private final JSplitPane main_panel_;
	private JTabbedPane tabs_;

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

		compile_view_ = new CompileView( graph_ );

		tabs_ = new JTabbedPane();
		tabs_.addTab( "Edit", node_view_ );
		tabs_.add( "Compile", compile_view_ );

		main_panel_ = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, graph_view_, tabs_ );
		main_panel_.setOneTouchExpandable( true );
		main_panel_.setContinuousLayout( true );

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
			Edge currently_selected_edge = graph_.selectedEdge();

			if( currently_selected_node != selected_node_ || currently_selected_edge != selected_edge_ ) {

				selected_node_ = graph_.selectedNode();
				selected_edge_ = graph_.selectedEdge();
				final int selected_index = tabs_.getSelectedIndex();

				if( node_view_ != null ) {
					tabs_.remove( node_view_ );
				} else if( edge_view_ != null ) {
					tabs_.remove( edge_view_ );
				}

				if( selected_node_ != null ) {
					// Node View
					node_view_ = new NodeView( selected_node_ );
					edge_view_ = null;
					tabs_.insertTab( "Edit", null, node_view_, "", 0 );
				} else {
					// EdgeView
					edge_view_ = new EdgeView( graph_.selectedEdge() );
					node_view_ = null;
					tabs_.insertTab( "Edit", null, edge_view_, "", 0 );
				}

				tabs_.setSelectedIndex( selected_index );
			}
		}

		super.repaint();
	}

}
