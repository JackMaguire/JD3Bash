package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import graph.*;
import views.GlobalData;
import views.GraphView;

public class GraphController implements MouseListener, MouseMotionListener, KeyListener {

	private final Graph graph_;
	private final GraphView graph_view_;

	// private boolean last_mouse_press_was_on_a_node_ = false;
	private int last_mouse_press_x_ = 0;
	private int last_mouse_press_y_ = 0;
	private boolean node_is_currently_being_dragged_ = false;
	private boolean edge_is_currently_being_created_ = false;

	public GraphController( Graph g ) {
		graph_ = g;
		graph_view_ = new GraphView( g );
		graph_view_.addMouseListener( this );
		graph_view_.addMouseMotionListener( this );
		graph_view_.addKeyListener( this );
	}

	public GraphView view() {
		return graph_view_;
	}

	@Override
	public void mouseDragged( MouseEvent e ) {
		if( node_is_currently_being_dragged_ ) {
			int x = e.getX();
			int y = e.getY();
			Node sn = graph_.selectedNode();
			sn.setX( graph_view_.getClosestPointForPoint( x ) );
			sn.setY( graph_view_.getClosestPointForPoint( y ) );
			GlobalData.top_panel.repaint();
			return;
		}

		if( edge_is_currently_being_created_ ) {
			graph_.ghostEdge().x = e.getX();
			graph_.ghostEdge().y = e.getY();
			GlobalData.top_panel.repaint();
			return;
		}
	}

	@Override
	public void mouseMoved( MouseEvent e ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked( MouseEvent e ) {
		if( e.isControlDown() ) {
			// create new node
			int x = graph_view_.getClosestPointForPoint( e.getX() );
			int y = graph_view_.getClosestPointForPoint( e.getY() );
			Node new_node = new Node( graph_.getNextNodeID(), x, y );
			graph_.addNode( new_node );
			GlobalData.top_panel.repaint();
			return;
		}
	}

	@Override
	public void mousePressed( MouseEvent e ) {
		last_mouse_press_x_ = e.getX();
		last_mouse_press_y_ = e.getY();

		if( e.isControlDown() ) {
			// potentially create an edge
			for( Node n : graph_.allNodes_const() ) {
				if( graph_view_.boxForNode_const().get( n ).pointIsInBox( last_mouse_press_x_, last_mouse_press_y_ ) ) {
					graph_.setSelectedNode( n );
					edge_is_currently_being_created_ = true;
					graph_.setGhostEdge( new PreliminaryEdge( n, last_mouse_press_x_, last_mouse_press_y_ ) );
					GlobalData.top_panel.repaint();
					return;
				}
			}
		} else {
			// Potentially Select A Node
			for( Node n : graph_.allNodes_const() ) {
				if( graph_view_.boxForNode_const().get( n ).pointIsInBox( last_mouse_press_x_, last_mouse_press_y_ ) ) {
					graph_.setSelectedNode( n );
					// if( !e.isControlDown() ) {// if control is down, we are creating an edge
					node_is_currently_being_dragged_ = true;
					// }
					GlobalData.top_panel.repaint();
					return;
				}
			}

			// Potentially Select An Edge
			for( Edge edge : graph_.allEdges_const() ) {
				if( graph_view_.boxForEdge_const().get( edge ).pointIsInBox( last_mouse_press_x_, last_mouse_press_y_ ) ) {
					graph_.setSelectedEdge( edge );
					GlobalData.top_panel.repaint();
				}
			}

		}
	}

	@Override
	public void mouseReleased( MouseEvent e ) {
		int x = e.getX();
		int y = e.getY();

		if( node_is_currently_being_dragged_ ) {
			if( Math.abs( x - last_mouse_press_x_ ) > 4 || Math.abs( y - last_mouse_press_y_ ) > 4 ) {
				Node sn = graph_.selectedNode();
				sn.setX( graph_view_.getClosestPointForPoint( x ) );
				sn.setY( graph_view_.getClosestPointForPoint( y ) );
				GlobalData.top_panel.repaint();
			}
			node_is_currently_being_dragged_ = false;
			return;
		} else if( edge_is_currently_being_created_ ) {
			edge_is_currently_being_created_ = false;
			graph_.setGhostEdge( null );
			// Check to see if xy corresponds to a node
			for( Node n : graph_.allNodes_const() ) {
				if( n == graph_.selectedNode() )
					continue;
				if( graph_view_.boxForNode_const().get( n ).pointIsInBox( x, y ) ) {
					Edge new_edge = graph_.addEdge( graph_.selectedNode(), n );
					graph_.setSelectedEdge( new_edge );
					graph_.setSelectedNode( null );
					GlobalData.top_panel.repaint();
					return;
				}
			}
			GlobalData.top_panel.repaint();
			return;
		}

	}

	@Override
	public void mouseEntered( MouseEvent e ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited( MouseEvent e ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped( KeyEvent e ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed( KeyEvent e ) {
		System.out.println( e.getKeyCode() );
		switch ( e.getKeyCode() ) {
			case KeyEvent.VK_LEFT: {
				Node sn = graph_.selectedNode();
				if( sn == null )
					return;

				int x = sn.x();
				if( x > 0 ) {
					sn.setX( x - 1 );
					GlobalData.top_panel.repaint();
				}
				break;
			}
			case KeyEvent.VK_RIGHT: {
				Node sn = graph_.selectedNode();
				if( sn == null )
					return;

				int x = sn.x();
				sn.setX( x + 1 );
				GlobalData.top_panel.repaint();
				break;
			}
		}
	}

	@Override
	public void keyReleased( KeyEvent e ) {
		// TODO Auto-generated method stub

	}

}
