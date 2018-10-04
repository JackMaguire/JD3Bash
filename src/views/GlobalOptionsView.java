package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controllers.GlobalOptionsController;
import global_data.Options;

public class GlobalOptionsView extends JPanel {

	/**
	 * Generated By Eclipse
	 */
	private static final long serialVersionUID = 3360233999493743568L;

	private final JLabel view_options_label_ = new JLabel( "View" );
	private final JCheckBox show_node_titles_checkbox_ = new JCheckBox( "Show node titles" );
	private final JCheckBox put_node_titles_to_side_checkbox_ = new JCheckBox(
			"Move node titles to side" );

	private final JLabel compile_options_label_ = new JLabel( "Compile" );
	private final JCheckBox serialize_intermediate_poses_checkbox_ = new JCheckBox(
			"Serialize Intermediate Poses" );
	private final JTextArea serialize_intermediate_poses_explanation_ = new JTextArea( "TODO" );
	private final JScrollPane serialize_intermediate_poses_explanation_scroll_pane_ = new JScrollPane(
			serialize_intermediate_poses_explanation_ );

	private final JLabel run_options_label_ = new JLabel( "Run" );
	private final JLabel n_proc_label_ = new JLabel( "Num Processors:" );
	private final JTextField n_proc_field_ = new JTextField( Options.getNumProcessors() + "" );
	private final JLabel default_command_label_ = new JLabel( "Default Run Command:" );
	private final JTextField default_command_field_ = new JTextField( Options.getDefaultRunCommand() );
	
	
	private final GlobalOptionsController controller_;

	public GlobalOptionsView() {
		// View
		final JPanel inner_view_options_panel = new JPanel( new GridLayout( 1, 4 ) );
		inner_view_options_panel.add( show_node_titles_checkbox_ );
		inner_view_options_panel.add( put_node_titles_to_side_checkbox_ );

		final JPanel view_options_panel = new JPanel( new BorderLayout() );
		view_options_label_.setHorizontalAlignment( JLabel.CENTER );
		view_options_panel.add( view_options_label_, BorderLayout.NORTH );
		view_options_panel.add( inner_view_options_panel, BorderLayout.CENTER );

		// Compile
		final JPanel inner_compile_options_panel = new JPanel( new GridLayout( 1, 2 ) );
		serialize_intermediate_poses_explanation_.setEditable( false );
		inner_compile_options_panel.add( serialize_intermediate_poses_checkbox_ );
		inner_compile_options_panel.add( serialize_intermediate_poses_explanation_scroll_pane_ );

		final JPanel compile_options_panel = new JPanel( new BorderLayout() );
		compile_options_label_.setHorizontalAlignment( JLabel.CENTER );
		compile_options_panel.add( compile_options_label_, BorderLayout.NORTH );
		compile_options_panel.add( inner_compile_options_panel, BorderLayout.CENTER );

		//Run
		final JPanel inner_run_options_panel = new JPanel( new GridLayout( 1, 4 ) );
		inner_run_options_panel.add( n_proc_label_ );
		inner_run_options_panel.add( n_proc_field_ );
		inner_run_options_panel.add( default_command_label_ );
		inner_run_options_panel.add( default_command_field_ );

		final JPanel run_options_panel = new JPanel( new BorderLayout() );
		run_options_label_.setHorizontalAlignment( JLabel.CENTER );
		run_options_panel.add( run_options_label_, BorderLayout.NORTH );
		run_options_panel.add( inner_run_options_panel, BorderLayout.CENTER );
		
		//Main
		setLayout( new GridLayout( 10, 1 ) );
		add( view_options_panel );
		add( compile_options_panel );
		add( run_options_panel );

		controller_ = new GlobalOptionsController( this );
		show_node_titles_checkbox_.addActionListener( controller_ );
		put_node_titles_to_side_checkbox_.addActionListener( controller_ );
		serialize_intermediate_poses_checkbox_.addActionListener( controller_ );
		
		n_proc_field_.getDocument().addDocumentListener( controller_ );
	}

	public final JCheckBox getShowNodeTitlesCheckbox() {
		return show_node_titles_checkbox_;
	}

	public final JCheckBox getPutNodeTitlesToSideCheckBox() {
		return put_node_titles_to_side_checkbox_;
	}

	public final JCheckBox getSerializeIntermediatePosesCheckbox() {
		return serialize_intermediate_poses_checkbox_;
	}
	
	public final JTextField getNumProcessorField() {
		return n_proc_field_;
	}
	
	public final JTextField getDefaultCommandField() {
		return default_command_field_;
	}

}
