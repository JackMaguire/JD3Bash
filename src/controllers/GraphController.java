package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import graph.*;

public class GraphController implements MouseListener, MouseMotionListener {

	private final Graph graph_;
	private final HashMap< Node, utility.Box > box_for_node_;
	
	public GraphController( Graph g ) {
		graph_ = g;
		box_for_node_ = new HashMap< Node, utility.Box >();
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

}