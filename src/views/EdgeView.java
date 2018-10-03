package views;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controllers.EdgeController;
import graph.Edge;

public class EdgeView extends JPanel {

	/**
	 * Generated By Eclipse
	 */
	private static final long serialVersionUID = -5395763224098285614L;

	private final Edge edge_;
	private final EdgeController edge_controller_;// circular references are okay in Java

	private final JLabel column_name_label_ = new JLabel( "Column Name To Sort By:   " );
	private final JTextField column_name_field_;

	private final JLabel positive_scores_are_better_label_ = new JLabel( "" );
	private final JCheckBox positive_scores_are_better_box_ = new JCheckBox();

	private final JLabel num_results_to_transfer_label_ = new JLabel( "Num Results To Transfer:   " );
	private final JTextField num_results_to_transfer_field_;

	private final JLabel percentage_of_results_to_transfer_label_ = new JLabel( "Percentage Of Results To Transfer:   " );
	private final JTextField percentage_of_results_to_transfer_field_;

	private final JLabel use_percentage_instead_of_count_label_ = new JLabel( "" );
	private final JCheckBox use_percentage_instead_of_count_box_ = new JCheckBox( "Use Percentage Instead Of Count" );

	public EdgeView( Edge e ) {
		edge_ = e;
		column_name_field_ = new JTextField( edge_.columnNameToSortBy() );
		positive_scores_are_better_box_.setText( "Positive Scores Are Better" );

		num_results_to_transfer_field_ = new JTextField( "" + edge_.numResultsToTransfer() );
		percentage_of_results_to_transfer_field_ = new JTextField( "" + edge_.percentageOfResultsToTransfer() );

		// Percent Vs Num
		final boolean use_perc = edge_.usePercentageInsteadOfCount();
		use_percentage_instead_of_count_box_.setSelected( use_perc );
		num_results_to_transfer_label_.setEnabled( !use_perc );
		num_results_to_transfer_field_.setEnabled( !use_perc );
		percentage_of_results_to_transfer_label_.setEnabled( use_perc );
		percentage_of_results_to_transfer_field_.setEnabled( use_perc );

		// Label Formatting
		column_name_label_.setHorizontalAlignment( JLabel.RIGHT );
		num_results_to_transfer_label_.setHorizontalAlignment( JLabel.RIGHT );
		percentage_of_results_to_transfer_label_.setHorizontalAlignment( JLabel.RIGHT );

		// Listeners
		edge_controller_ = new EdgeController( this );// circular references are okay in Java
		column_name_field_.getDocument().addDocumentListener( edge_controller_ );
		num_results_to_transfer_field_.getDocument().addDocumentListener( edge_controller_ );
		percentage_of_results_to_transfer_field_.getDocument().addDocumentListener( edge_controller_ );
		positive_scores_are_better_box_.addChangeListener( edge_controller_ );
		use_percentage_instead_of_count_box_.addChangeListener( edge_controller_ );

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

		top_view.add( percentage_of_results_to_transfer_label_ );
		top_view.add( percentage_of_results_to_transfer_field_ );

		top_view.add( use_percentage_instead_of_count_label_ );
		top_view.add( use_percentage_instead_of_count_box_ );

		setLayout( new GridLayout( 3, 1 ) );
		add( top_view );
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
		return percentage_of_results_to_transfer_field_;
	}

	public JLabel getPercentageOfResultsToTransferLabel() {
		return percentage_of_results_to_transfer_label_;
	}

	public JCheckBox getUsePercentageInsteadOfCountBox() {
		return use_percentage_instead_of_count_box_;
	}

	public Edge getEdge() {
		return edge_;
	}
}
