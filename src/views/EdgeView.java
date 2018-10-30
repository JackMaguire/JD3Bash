package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controllers.EdgeController;
import graph.Edge;
import graph.Node;

public class EdgeView extends JPanel {

	/**
	 * Generated By Eclipse
	 */
	private static final long serialVersionUID = -5395763224098285614L;

	private final Edge edge_;
	private final EdgeController edge_controller_;
	// circular references are okay in Java

	private final JLabel column_name_label_ = new JLabel(
			"Column Name To Sort By:   " );
	private final JTextField column_name_field_;

	private final JLabel positive_scores_are_better_label_ = new JLabel( "" );
	private final JCheckBox positive_scores_are_better_box_ = new JCheckBox();

	private final JLabel num_results_to_transfer_label_ = new JLabel(
			"Num Results To Transfer:   " );
	private final JTextField num_results_to_transfer_field_;

	private final JLabel fraction_of_results_to_transfer_label_ = new JLabel(
			"Fraction Of Results To Transfer:   " );
	private final JTextField fraction_of_results_to_transfer_field_;

	private final JLabel use_fraction_instead_of_count_label_ = new JLabel(
			"" );
	private final JCheckBox use_fraction_instead_of_count_box_ = new JCheckBox(
			"Use Fraction Instead Of Count" );

	private final JLabel notes_label_ = new JLabel( "Notes" );
	private final JTextArea notes_area_ = new JTextArea();
	private final JScrollPane notes_scroll_pane_ = new JScrollPane( notes_area_ );

	private final JLabel help_label_ = new JLabel( "Help" );
	private final JTextArea help_area_ = new JTextArea( Node.getHelp() );
	private final JScrollPane help_scroll_pane_ = new JScrollPane( help_area_ );

	public EdgeView( Edge e ) {
		edge_ = e;
		column_name_field_ = new JTextField( edge_.columnNameToSortBy() );
		positive_scores_are_better_box_.setText( "Positive Scores Are Better" );
		positive_scores_are_better_box_.setSelected( edge_.positiveScoresAreBetter() );
		notes_area_.setText( e.getNotes() );

		num_results_to_transfer_field_ = new JTextField(
				"" + edge_.numResultsToTransfer() );
		fraction_of_results_to_transfer_field_ = new JTextField(
				"" + edge_.percentageOfResultsToTransfer() );

		// Percent Vs Num
		final boolean use_frac = edge_.usePercentageInsteadOfCount();
		use_fraction_instead_of_count_box_.setSelected( use_frac );
		num_results_to_transfer_label_.setEnabled( !use_frac );
		num_results_to_transfer_field_.setEnabled( !use_frac );
		fraction_of_results_to_transfer_label_.setEnabled( use_frac );
		fraction_of_results_to_transfer_field_.setEnabled( use_frac );

		// Label Formatting
		column_name_label_.setHorizontalAlignment( JLabel.RIGHT );
		num_results_to_transfer_label_.setHorizontalAlignment( JLabel.RIGHT );
		fraction_of_results_to_transfer_label_
				.setHorizontalAlignment( JLabel.RIGHT );

		// Listeners
		edge_controller_ = new EdgeController( this );// circular references are
																									// okay in Java
		column_name_field_.getDocument().addDocumentListener( edge_controller_ );
		num_results_to_transfer_field_.getDocument()
				.addDocumentListener( edge_controller_ );
		fraction_of_results_to_transfer_field_.getDocument()
				.addDocumentListener( edge_controller_ );
		positive_scores_are_better_box_.addChangeListener( edge_controller_ );
		use_fraction_instead_of_count_box_.addChangeListener( edge_controller_ );
		notes_area_.getDocument().addDocumentListener( edge_controller_ );

		// TextAreas
		help_area_.setEditable( false );

		setupView();
	}

	private void setupView() {
		JPanel top_view = new JPanel( new GridLayout( 5, 2 ) );
		top_view.add( column_name_label_ );
		top_view.add( column_name_field_ );

		top_view.add( positive_scores_are_better_label_ );
		top_view.add( positive_scores_are_better_box_ );

		top_view.add( num_results_to_transfer_label_ );
		top_view.add( num_results_to_transfer_field_ );

		top_view.add( fraction_of_results_to_transfer_label_ );
		top_view.add( fraction_of_results_to_transfer_field_ );

		top_view.add( use_fraction_instead_of_count_label_ );
		top_view.add( use_fraction_instead_of_count_box_ );

		JPanel notes_panel = new JPanel( new BorderLayout() );
		notes_panel.add( notes_scroll_pane_, BorderLayout.CENTER );
		notes_panel.add( notes_label_, BorderLayout.NORTH );

		JPanel help_panel = new JPanel( new BorderLayout() );
		help_panel.add( help_scroll_pane_, BorderLayout.CENTER );
		help_panel.add( help_label_, BorderLayout.NORTH );

		setLayout( new GridLayout( 3, 1 ) );
		add( top_view );
		add( notes_panel );
		add( help_panel );
	}

	public JTextField getColumnNameField() {
		return column_name_field_;
	}

	public JCheckBox getPositiveScoresAreBetterBox() {
		return positive_scores_are_better_box_;
	}

	public JTextField getNumResultsToTransferField() {
		return num_results_to_transfer_field_;
	}

	public JLabel getNumResultsToTransferLabel() {
		return num_results_to_transfer_label_;
	}

	public JTextField getPercentageOfResultsToTransferField() {
		return fraction_of_results_to_transfer_field_;
	}

	public JLabel getPercentageOfResultsToTransferLabel() {
		return fraction_of_results_to_transfer_label_;
	}

	public JCheckBox getUsePercentageInsteadOfCountBox() {
		return use_fraction_instead_of_count_box_;
	}

	public Edge getEdge() {
		return edge_;
	}

	public JTextArea getNotesArea() {
		return notes_area_;
	}
}
