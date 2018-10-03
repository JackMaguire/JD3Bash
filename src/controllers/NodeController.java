package controllers;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import graph.Node;
import views.NodeView;

public class NodeController implements DocumentListener {

	private final Node node_;
	private final NodeView node_view_;// circular references are okay in Java
	
	public NodeController( NodeView nv ) {
		node_ = nv.getNode();
		node_view_ = nv;
	}
	
	@Override
	public void insertUpdate( DocumentEvent e ) {
		processDocumentChange( e );
	}

	@Override
	public void removeUpdate( DocumentEvent e ) {
		processDocumentChange( e );
	}

	@Override
	public void changedUpdate( DocumentEvent e ) {
		processDocumentChange( e );
	}

	private void processDocumentChange( DocumentEvent e ) {

	}
	
}
