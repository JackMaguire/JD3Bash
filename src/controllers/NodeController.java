package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import graph.Node;

public class NodeController implements KeyListener {
	private final Node node_;

	public NodeController( Node n ) {
		node_ = n;
	}

	@Override
	public void keyTyped( KeyEvent e ) {

		switch ( e.getKeyCode() ) {
			case KeyEvent.VK_LEFT: {
				int x = node_.x();
				if( x > 0 ) {
					node_.setX( x - 1 );
				}
				break;
			}
			case KeyEvent.VK_RIGHT: {
				int x = node_.x();
				//TODO do we need to make sure this does not get too large?
				node_.setX( x + 1 );
				break;
			}
		}
	}

	@Override
	public void keyPressed( KeyEvent e ) {
	}

	@Override
	public void keyReleased( KeyEvent e ) {
	}

}
