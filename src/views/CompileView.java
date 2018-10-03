package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import graph.Graph;

public class CompileView extends JPanel {

	private final Graph graph_;
	
	private final JButton compile_button_ = new JButton( "Compile" );
	private final JTextArea setup_area_ = new JTextArea();
	private final JTextArea run_area_ = new JTextArea();
	
	public CompileView( Graph g ) {
		graph_ = g;
		
		JPanel top_center_panel = new JPanel( new BorderLayout() );
		JLabel top_center_title = new JLabel( "Setup Script" );
		top_center_panel.add( top_center_title, BorderLayout.NORTH );
		top_center_panel.add( setup_area_, BorderLayout.CENTER );
		
		JPanel bottom_center_panel = new JPanel( new BorderLayout() );
		JLabel bottom_center_title = new JLabel( "Run Script" );
		bottom_center_panel.add( bottom_center_title, BorderLayout.NORTH );
		bottom_center_panel.add( run_area_, BorderLayout.CENTER );
		
		JPanel center_panel = new JPanel( new GridLayout(2,1) );
		center_panel.add( top_center_panel );
		center_panel.add( bottom_center_panel );
		
		setLayout( new BorderLayout() );
		add( center_panel, BorderLayout.CENTER );
		add( compile_button_, BorderLayout.NORTH );
	}
	
}
