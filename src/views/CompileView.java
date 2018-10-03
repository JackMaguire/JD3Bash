package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import exceptions.InvalidGraphException;
import graph.Graph;
import output.GraphParsing;
import utility.Pair;

public class CompileView extends JPanel implements ActionListener {

	private final Graph graph_;

	private final JButton compile_button_ = new JButton( "Compile" );
	private final JTextArea setup_area_ = new JTextArea();
	private final JTextArea run_area_ = new JTextArea();

	public CompileView( Graph g ) {
		graph_ = g;
		compile_button_.addActionListener( this );
		setup_area_.setEditable( false );
		run_area_.setEditable( false );
		
		JPanel top_center_panel = new JPanel( new BorderLayout() );
		JLabel top_center_title = new JLabel( "Setup Script" );
		top_center_panel.add( top_center_title, BorderLayout.NORTH );
		top_center_panel.add( setup_area_, BorderLayout.CENTER );

		JPanel bottom_center_panel = new JPanel( new BorderLayout() );
		JLabel bottom_center_title = new JLabel( "Run Script" );
		bottom_center_panel.add( bottom_center_title, BorderLayout.NORTH );
		bottom_center_panel.add( run_area_, BorderLayout.CENTER );

		JPanel center_panel = new JPanel( new GridLayout( 2, 1 ) );
		center_panel.add( top_center_panel );
		center_panel.add( bottom_center_panel );

		setLayout( new BorderLayout() );
		add( center_panel, BorderLayout.CENTER );
		add( compile_button_, BorderLayout.NORTH );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		try {
			Pair< String, String > run_and_setup = GraphParsing.parseGraph( graph_ );
			String run_script = run_and_setup.first;
			String setup_script = run_and_setup.second;
			setup_area_.setText( setup_script );
			run_area_.setText( run_script );
		}
		catch( InvalidGraphException e1 ) {
			//e1.printStackTrace();
			setup_area_.setText( "Can not compile! Error:\n" + e1.getMessage() );
			run_area_.setText( "Can not compile! Error:\n" + e1.getMessage() );
		}
		GlobalData.top_panel.repaint();
	}

}
