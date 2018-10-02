package views;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import graph.Edge;

public class EdgeView extends JPanel {

	/**
	 * Generated By Eclipse
	 */
	private static final long serialVersionUID = -5395763224098285614L;

	private final Edge edge_;

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

		setupView();
	}

	private void setupView() {
		setLayout( new GridLayout( 2, 2 ) );
		add( column_name_label_ );
		add( column_name_field_ );

		add( positive_scores_are_better_label_ );
		add( positive_scores_are_better_box_ );
	}

}
