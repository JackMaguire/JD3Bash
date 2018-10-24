package controllers;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import views.EdgeView;

public class EdgeController implements ChangeListener, DocumentListener {

	private final EdgeView edge_view_;// circular references are okay in Java

	public EdgeController( EdgeView ev ) {
		edge_view_ = ev;
	}

	@Override
	public void stateChanged( ChangeEvent e ) {
		if( e.getSource() == edge_view_.getPositiveScoresAreBetterBox() ) {
			final boolean is_selected = edge_view_.getPositiveScoresAreBetterBox().isSelected();
			edge_view_.getEdge().setPositiveScoresAreBetter( is_selected );
		} else if( e.getSource() == edge_view_.getUsePercentageInsteadOfCountBox() ) {
			final boolean use_perc = edge_view_.getUsePercentageInsteadOfCountBox().isSelected();
			edge_view_.getEdge().setUsePercentageInsteadOfCount( use_perc );
			edge_view_.getPercentageOfResultsToTransferField().setEnabled( use_perc );
			edge_view_.getPercentageOfResultsToTransferLabel().setEnabled( use_perc );
			edge_view_.getNumResultsToTransferField().setEnabled( !use_perc );
			edge_view_.getNumResultsToTransferLabel().setEnabled( !use_perc );
		}//else if ( e.getSource() == edge_view_.getPositiveScoresAreBetterBox() )
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
		if( e.getDocument() == edge_view_.getColumnNameField().getDocument() ) {
			final String text = edge_view_.getColumnNameField().getText();
			edge_view_.getEdge().setColumnNameToSortBy( text );
		} else if( e.getDocument() == edge_view_.getNumResultsToTransferField().getDocument() ) {
			try {
				final String text = edge_view_.getNumResultsToTransferField().getText();
				int setting = Integer.parseInt( text );
				edge_view_.getEdge().setNumResultsToTransfer( setting );
			}
			catch( Exception X ) {
			}
		} else if( e.getDocument() == edge_view_
				.getPercentageOfResultsToTransferField().getDocument() ) {
			try {
				final String text = edge_view_.getPercentageOfResultsToTransferField().getText();
				final double setting = 	Double.parseDouble( text );
				edge_view_.getEdge().setPercentageOfResultsToTransfer( setting );
			}
			catch( Exception X ) {
			}
		} else if( e.getDocument() == edge_view_.getNotesArea().getDocument() ) {
			edge_view_.getEdge().setNotes( edge_view_.getNotesArea().getText() );
		}
	}

}
