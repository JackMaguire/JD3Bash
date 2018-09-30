package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import graph.*;
import utility.Box;
import views.GlobalData;
import views.GraphView;

public class GraphController implements MouseListener, MouseMotionListener, KeyListener {

	private final Graph graph_;
	private final GraphView graph_view_;

	private boolean last_mouse_press_was_on_a_node_ = false;
	private int last_mouse_press_x_ = 0;
	private int last_mouse_press_y_ = 0;
	private boolean node_is_currently_being_dragged_ = false;
	
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
		// TODO Auto-generated method stub
		if( node_is_currently_being_dragged_ ) {
			int x = e.getX();
			int y = e.getY();
			Node sn = graph_.selectedNode();
			sn.setX( graph_view_.getClosestPointForPoint( x ) );
			sn.setY( graph_view_.getClosestPointForPoint( y ) );
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
			last_mouse_press_x_ = e.getX();
			last_mouse_press_y_ = e.getY();
			for( Node n : graph_.allNodes_const() ) {
				if( graph_view_.boxForNode_const().get( n ).pointIsInBox( last_mouse_press_x_, last_mouse_press_y_ ) ) {
					graph_.setSelectedNode( n );
					last_mouse_press_was_on_a_node_ = true;
					//TODO start creating edge!
					GlobalData.top_panel.repaint();
					return;
				}
			}
			
			//create new node
			int x = graph_view_.getClosestPointForPoint( e.getX() );
			int y = graph_view_.getClosestPointForPoint( e.getY() );
			Node new_node = new Node( graph_.getNextNodeID(), x ,y );
			graph_.addNode( new_node );
			GlobalData.top_panel.repaint();
			return;
		}
	}

	@Override
	public void mousePressed( MouseEvent e ) {
		// TODO Auto-generated method stub
		last_mouse_press_x_ = e.getX();
		last_mouse_press_y_ = e.getY();

		// Potentially Select A Node
		for( Node n : graph_.allNodes_const() ) {
			if( graph_view_.boxForNode_const().get( n ).pointIsInBox( last_mouse_press_x_, last_mouse_press_y_ ) ) {
				graph_.setSelectedNode( n );
				last_mouse_press_was_on_a_node_ = true;
				node_is_currently_being_dragged_ = true;
				GlobalData.top_panel.repaint();
				return;
			}
		}

		last_mouse_press_was_on_a_node_ = false;
	}

	@Override
	public void mouseReleased( MouseEvent e ) {
		int x = e.getX();
		int y = e.getY();

		if( last_mouse_press_was_on_a_node_ ) {//replace with node_is_currently_being_dragged_?
			if( Math.abs( x - last_mouse_press_x_ ) > 4 || Math.abs( y - last_mouse_press_y_ ) > 4 ) {
				Node sn = graph_.selectedNode();
				/*if( e.isShiftDown() ) {
					sn.setX( graph_view_.getClosestGridPointForPoint( x ) );
					sn.setY( graph_view_.getClosestGridPointForPoint( y ) );
				} else {
					sn.setX( graph_view_.getClosestPointForPoint( x ) );
					sn.setY( graph_view_.getClosestPointForPoint( y ) );
				}*/
				sn.setX( graph_view_.getClosestPointForPoint( x ) );
				sn.setY( graph_view_.getClosestPointForPoint( y ) );
				GlobalData.top_panel.repaint();
			}
			node_is_currently_being_dragged_ = false;
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
