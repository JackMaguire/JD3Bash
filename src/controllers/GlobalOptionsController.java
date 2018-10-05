package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import global_data.GlobalViewData;
import global_data.Options;
import views.GlobalOptionsView;

public class GlobalOptionsController implements ActionListener, DocumentListener {

	private final GlobalOptionsView view_;

	public GlobalOptionsController( GlobalOptionsView view ) {
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
			Options.setSerializeIntermediatePoses(
					view_.getSerializeIntermediatePosesCheckbox().isSelected() );
			GlobalViewData.top_panel.repaint();
			return;
		}

		if( e.getSource() == view_.getDeleteUnusedIntermediatePosesCheckbox() ) {
			Options.setDeleteUnusedIntermediatePoses(
					view_.getDeleteUnusedIntermediatePosesCheckbox().isSelected() );
			GlobalViewData.top_panel.repaint();
			return;
		}

		if( e.getSource() == view_.getDecreaseGridSizeButton() ) {
			final int current_size = Options.getGridSize();
			if( current_size > 1 ) {
				Options.setGridSize( current_size - 1 );
				GlobalViewData.top_panel.repaint();
			}
			return;
		}

		if( e.getSource() == view_.getIncreaseGridSizeButton() ) {
			final int current_size = Options.getGridSize();
			Options.setGridSize( current_size + 1 );
			GlobalViewData.top_panel.repaint();
			return;
		}
	}

	@Override
	public void insertUpdate( DocumentEvent e ) {
		docUpdate( e );
	}

	@Override
	public void removeUpdate( DocumentEvent e ) {
		docUpdate( e );
	}

	@Override
	public void changedUpdate( DocumentEvent e ) {
		docUpdate( e );
	}

	private void docUpdate( DocumentEvent e ) {
		if( e.getDocument() == view_.getNumProcessorField().getDocument() ) {
			try {
				int setting = Integer.parseInt( view_.getNumProcessorField().getText() );
				Options.setNumProcessors( setting );
			}
			catch( NumberFormatException nfe ) {
			}
			return;
		}

		if( e.getDocument() == view_.getDefaultCommandField().getDocument() ) {
			Options.setDefaultRunCommand( view_.getDefaultCommandField().getText() );
			return;
		}
	}

}
