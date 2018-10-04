package controllers;

import java.util.ArrayList;

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
		if( e.getDocument() == node_view_.getTitleField().getDocument() ) {
			node_.setTitle( node_view_.getTitleField().getText() );
			return;
		}

		if( e.getDocument() == node_view_.getCommandField().getDocument() ) {
			node_.setCommand( node_view_.getCommandField().getText() );
			return;
		}

		if( e.getDocument() == node_view_.getScriptField().getDocument() ) {
			node_.setXMLScriptFilename( node_view_.getScriptField().getText() );
			return;
		}

		if( e.getDocument() == node_view_.getUserFlagsArea().getDocument() ) {
			ArrayList< String > new_flags = new ArrayList< String >();
			String[] split = node_view_.getUserFlagsArea().getText().split( "\n" );
			for( String flag : split ) {
				new_flags.add( flag );
			}
			node_.setUserRosettaFlags( new_flags );
			return;
		}

		if( e.getDocument() == node_view_.getNotesArea().getDocument() ) {
			node_.setNotes( node_view_.getNotesArea().getText() );
			return;
		}
	}

}
