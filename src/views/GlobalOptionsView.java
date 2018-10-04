package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controllers.GlobalOptionsController;

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

		setLayout( new GridLayout( 2, 1 ) );
		add( view_options_panel );
		add( compile_options_panel );

		controller_ = new GlobalOptionsController( this );
		show_node_titles_checkbox_.addActionListener( controller_ );
		put_node_titles_to_side_checkbox_.addActionListener( controller_ );
		serialize_intermediate_poses_checkbox_.addActionListener( controller_ );
	}

	public JCheckBox getShowNodeTitlesCheckbox() {
		return show_node_titles_checkbox_;
	}

	public JCheckBox getPutNodeTitlesToSideCheckBox() {
		return put_node_titles_to_side_checkbox_;
	}

	public JCheckBox getSerializeIntermediatePosesCheckbox() {
		return serialize_intermediate_poses_checkbox_;
	}

}
