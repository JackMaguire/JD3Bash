package controllers;

import javax.swing.JButton;

import views.GraphViewControlPanel;

public class GraphViewControlPanelController {

	private final GraphViewControlPanel gvc_panel_;

	private final JButton organize_graph_ = new JButton( "Organize Graph" );
	private final JButton compile_button_ = new JButton( "Compile" );

	public GraphViewControlPanelController() {
		gvc_panel_ = new GraphViewControlPanel();
	}

}
