package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import views.EdgeView;

public class EdgeController implements ActionListener, ChangeListener, DocumentListener {

	private final EdgeView edge_view_;//circular references are okay in Java
	
	public EdgeController( EdgeView ev ) {
		edge_view_ = ev;
	}

	@Override
	public void actionPerformed( ActionEvent e ) {

	}

	@Override
	public void stateChanged( ChangeEvent e ) {
		if( e.getSource() == edge_view_.getPositiveScoresAreBetterBox() ) {
			edge_view_.getEdge().setPositiveScoresAreBetter( edge_view_.getPositiveScoresAreBetterBox().isSelected() );
		}
		else if( e.getSource() == edge_view_.getUsePercentageInsteadOfCountBox() ) {
			final boolean use_perc = edge_view_.getUsePercentageInsteadOfCountBox().isSelected();
			edge_view_.getEdge().setUsePercentageInsteadOfCount( use_perc );
			edge_view_.getPercentageOfResultsToTransferField().setEnabled( use_perc );
			edge_view_.getPercentageOfResultsToTransferLabel().setEnabled( use_perc );
			edge_view_.getNumResultsToTransferField().setEnabled( !use_perc );
			edge_view_.getNumResultsToTransferLabel().setEnabled( !use_perc );
		}
	}

	@Override
	public void insertUpdate( DocumentEvent e ) {
		processDocumentChange(e);
	}

	@Override
	public void removeUpdate( DocumentEvent e ) {
		processDocumentChange(e);
	}

	@Override
	public void changedUpdate( DocumentEvent e ) {
		processDocumentChange(e);
	}
	
	private void processDocumentChange( DocumentEvent e ) {
		if( e.getDocument() == edge_view_.getColumnNameField().getDocument() ) {
			edge_view_.getEdge().setColumnNameToSortBy( edge_view_.getColumnNameField().getText() );
		} else if( e.getDocument() == edge_view_.getNumResultsToTransferField().getDocument() ) {
			try {
				int setting = Integer.parseInt( edge_view_.getNumResultsToTransferField().getText() );
				edge_view_.getEdge().setNumResultsToTransfer( setting );
			}
			catch( Exception X ) {
			}
		} else if( e.getDocument() == edge_view_.getPercentageOfResultsToTransferField().getDocument() ) {
			try {
				double setting = Double.parseDouble( edge_view_.getPercentageOfResultsToTransferField().getText() );
				edge_view_.getEdge().setPercentageOfResultsToTransfer( setting );
			}
			catch( Exception X ) {
			}
		}
	}
	
}
