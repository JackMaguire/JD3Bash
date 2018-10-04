package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import global_data.GlobalViewData;
import global_data.Options;
import views.GlobalOptionsView;

public class GlobalOptionsController implements ActionListener {

	private final GlobalOptionsView view_;
	
	public GlobalOptionsController( GlobalOptionsView view ){
		view_ = view;
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		if( e.getSource() == view_.getShowNodeTitlesCheckbox() ) {
			Options.setShowNodeTitles( view_.getShowNodeTitlesCheckbox().isSelected() );
			GlobalViewData.top_panel.repaint();
			return;
		}
		
		if( e.getSource() == view_.getPutNodeTitlesToSideCheckBox() ) {
			Options.setPutNodeTitlesToSide( view_.getPutNodeTitlesToSideCheckBox().isSelected() );
			GlobalViewData.top_panel.repaint();
			return;
		}
		
		if( e.getSource() == view_.getSerializeIntermediatePosesCheckbox() ) {
			Options.setSerializeIntermediatePoses( view_.getSerializeIntermediatePosesCheckbox().isSelected() );
			GlobalViewData.top_panel.repaint();
			return;
		}
	}
	
}
