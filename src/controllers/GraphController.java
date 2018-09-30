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

	}

	@Override
	public void mouseMoved( MouseEvent e ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked( MouseEvent e ) {


		
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

		if( last_mouse_press_was_on_a_node_ ) {
			if( Math.abs( x - last_mouse_press_x_ ) > 4 || Math.abs( y - last_mouse_press_y_ ) > 4 ) {
				//TODO pick up here!
				System.out.println( x + " " + last_mouse_press_x_ );
				GlobalData.top_panel.repaint();
			}		
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
				if( sn == null ) return;
				
				int x = sn.x();
				if( x > 0 ) {
					sn.setX( x - 1 );
					GlobalData.top_panel.repaint();
				}
				break;
			}
			case KeyEvent.VK_RIGHT: {
				Node sn = graph_.selectedNode();
				if( sn == null ) return;
				
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
