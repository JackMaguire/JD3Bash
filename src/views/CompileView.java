package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import exceptions.InvalidGraphException;
import global_data.GlobalViewData;
import graph.Graph;
import output.GraphParsing;
import output.GraphParsingOptions;
import utility.Pair;

public class CompileView extends JPanel implements ActionListener {

	/**
	 * Generated By Eclipse
	 */
	private static final long serialVersionUID = 1292050406715207928L;

	private final Graph graph_;

	private final JButton compile_button_ = new JButton( "Compile" );

	private final JTextArea setup_area_ = new JTextArea();
	private final JScrollPane setup_scroll_pane_ = new JScrollPane( setup_area_ );

	private final JTextArea run_area_ = new JTextArea();
	private final JScrollPane run_scroll_pane_ = new JScrollPane( run_area_ );

	public CompileView( Graph g ) {
		graph_ = g;
		compile_button_.addActionListener( this );
		setup_area_.setEditable( false );
		run_area_.setEditable( false );

		final JPanel top_center_panel = new JPanel( new BorderLayout() );
		final JLabel top_center_title = new JLabel( "Setup Script" );
		top_center_panel.add( top_center_title, BorderLayout.NORTH );
		top_center_panel.add( setup_scroll_pane_, BorderLayout.CENTER );

		final JPanel bottom_center_panel = new JPanel( new BorderLayout() );
		final JLabel bottom_center_title = new JLabel( "Run Script" );
		bottom_center_panel.add( bottom_center_title, BorderLayout.NORTH );
		bottom_center_panel.add( run_scroll_pane_, BorderLayout.CENTER );

		final JPanel center_panel = new JPanel( new GridLayout( 2, 1 ) );
		center_panel.add( top_center_panel );
		center_panel.add( bottom_center_panel );

		final JPanel topmost_panel = new JPanel( new GridLayout( 1, 1 ) );
		topmost_panel.add( compile_button_ );

		setLayout( new BorderLayout() );
		add( center_panel, BorderLayout.CENTER );
		add( topmost_panel, BorderLayout.NORTH );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		try {
			GraphParsingOptions options = new GraphParsingOptions();
			options.serialize_intermediate_poses = global_data.Options.getSerializeIntermediatePoses();
			Pair< String, String > run_and_setup = GraphParsing.parseGraph( graph_,
					options );
			String run_script = run_and_setup.first;
			String setup_script = run_and_setup.second;
			setup_area_.setText( setup_script );
			run_area_.setText( run_script );
		}
		catch( InvalidGraphException e1 ) {
			setup_area_.setText( "Can not compile! Error:\n" + e1.getMessage() );
			run_area_.setText( "Can not compile! Error:\n" + e1.getMessage() );
		}
		GlobalViewData.top_panel.repaint();
	}

}
