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

public class GraphController implements MouseListener, MouseMotionListener, KeyListener {

	private final Graph graph_;
	private final HashMap< Node, Box > box_for_node_;

	public GraphController( Graph g ) {
		graph_ = g;
		box_for_node_ = new HashMap< Node, Box >();
	}

	public HashMap< Node, utility.Box > boxForNode() {
		return box_for_node_;
	}

	public Map< Node, utility.Box > boxForNode_const() {
		return Collections.unmodifiableMap( box_for_node_ );
	}

	public void setBoxForNode( Node n, Box b ) {
		box_for_node_.put( n, b );
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
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();

		for( Node n : graph_.allNodes_const() ) {
			if( box_for_node_.get( n ).pointIsInBox( x, y ) ) {
				graph_.setSelectedNode( n );
				GlobalData.top_panel.repaint();
				return;
			}
		}

		// Potentally Select A Node
	}

	@Override
	public void mousePressed( MouseEvent e ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased( MouseEvent e ) {
		// TODO Auto-generated method stub

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
