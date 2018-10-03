package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import views.EdgeView;
import views.GlobalData;

public class EdgeController implements ActionListener, ChangeListener {

	private final EdgeView edge_view_;//circular references are okay in Java
	
	public EdgeController( EdgeView ev ) {
		edge_view_ = ev;
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		// TODO Auto-generated method stub
		
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
	
}
