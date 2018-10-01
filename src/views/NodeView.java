package views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import graph.Node;

public class NodeView extends JPanel {

	/**
	 * Generated By Eclipse
	 */
	private static final long serialVersionUID = 8094662324693569393L;

	private final Node node_;

	// Segment1
	private final JLabel title_label_ = new JLabel( "Title:   " );
	private final JTextField title_field_;

	private final JLabel command_label_ = new JLabel( "Command:   " );
	private final JTextField command_field_;

	private final JLabel script_label_ = new JLabel( "Script:   " );
	private final JTextField script_field_;

	// Segment 2
	private final JLabel user_flags_label_ = new JLabel( "Rosetta Flags" );
	private final JTextArea user_flags_area_ = new JTextArea();

	// Segment 3
	private final JLabel auto_flags_label_ = new JLabel( "Rosetta Flags (Added By Us)" );
	private final JTextArea auto_flags_area_ = new JTextArea();

	// Segment 4
	private final JLabel recommended_flags_label_ = new JLabel( "Commonly Recommended Flags" );
	private final JTextArea recommended_flags_area_ = new JTextArea();

	//Segment 5: TODO
	private final JLabel notes_label_ = new JLabel( "Notes" );
	private final JTextArea notes_area_ = new JTextArea();
	
	//Segment 6: TODO
	private final JLabel help_label_ = new JLabel( "Help" );
	private final JTextArea help_area_ = new JTextArea();
	
	public NodeView( Node n ) {
		node_ = n;

		title_label_.setHorizontalAlignment( JLabel.RIGHT );
		command_label_.setHorizontalAlignment( JLabel.RIGHT );
		script_label_.setHorizontalAlignment( JLabel.RIGHT );

		title_field_ = new JTextField( node_.title() );
		command_field_ = new JTextField( node_.command() );
		script_field_ = new JTextField( node_.getXMLScript() );

		String user_flag_string = "";
		for( String s : n.getUserRosettaFlags() ) {
			user_flag_string += s + "\n";
		}
		user_flags_area_.setText( user_flag_string );

		String auto_flag_string = "";
		for( String s : n.determineAutoFlags() ) {
			auto_flag_string += s + "\n";
		}
		auto_flags_area_.setText( auto_flag_string );

		String recommended_flag_string = "";
		for( String s : Node.commonFlags() ) {
			recommended_flag_string += s + "\n";
		}
		recommended_flags_area_.setText( recommended_flag_string );

		setFonts( 12 );

		setupView();
	}

	private void setFonts( int fontsize ) {
		Font f = new Font( "monospaced", Font.PLAIN, fontsize );
		title_field_.setFont( f );
		command_field_.setFont( f );
		script_field_.setFont( f );
		user_flags_area_.setFont( f );
		auto_flags_area_.setFont( f );
		recommended_flags_area_.setFont( f );
	}

	private void setupView() {
		this.setLayout( new GridLayout( 8, 1 ) );

		JPanel segment1 = new JPanel( new GridLayout( 3, 2 ) );
		segment1.add( title_label_ );
		segment1.add( title_field_ );
		segment1.add( command_label_ );
		segment1.add( command_field_ );
		segment1.add( script_label_ );
		segment1.add( script_field_ );
		this.add( segment1 );

		JPanel segment2 = new JPanel( new BorderLayout() );
		segment2.add( user_flags_area_, BorderLayout.CENTER );
		segment2.add( user_flags_label_, BorderLayout.NORTH );
		this.add( segment2 );

		JPanel segment3 = new JPanel( new BorderLayout() );
		segment3.add( auto_flags_area_, BorderLayout.CENTER );
		segment3.add( auto_flags_label_, BorderLayout.NORTH );
		this.add( segment3 );

		JPanel segment4 = new JPanel( new BorderLayout() );
		segment4.add( recommended_flags_area_, BorderLayout.CENTER );
		segment4.add( recommended_flags_label_, BorderLayout.NORTH );
		this.add( segment4 );
		
		JPanel segment5 = new JPanel( new BorderLayout() );
		segment5.add( notes_area_, BorderLayout.CENTER );
		segment5.add( notes_label_, BorderLayout.NORTH );
		this.add( segment5 );
		
		JPanel segment6 = new JPanel( new BorderLayout() );
		segment6.add( help_area_, BorderLayout.CENTER );
		segment6.add( help_label_, BorderLayout.NORTH );
		this.add( segment6 );
	}

}
