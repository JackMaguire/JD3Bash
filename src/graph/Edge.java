package graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.LoadFailureException;

public class Edge {

	private Node source_node_;
	private Node destination_node_;

	private String column_name_to_sort_by_ = "total_score";
	private boolean positive_scores_are_better_ = false;

	private int num_results_to_transfer_;
	private double percentage_of_results_to_transfer_;
	private boolean use_percentage_instead_of_count_ = false;

	private String notes_ = "";

	public Edge() {

	}

	public Edge( Node source_node, Node dest_node ) {
		source_node_ = source_node;
		destination_node_ = dest_node;
	}

	public Edge( BufferedReader in, ArrayList< Node > nodes )
			throws IOException, LoadFailureException {
		final String first_line = in.readLine();
		if( !first_line.equals( "START_EDGE" ) ) {
			throw new LoadFailureException(
					"Expected 'START_EDGE' instead of '" + first_line + "'" );
		}

		for( String line = in.readLine(); !line.equals( "END_EDGE" ); line = in
				.readLine() ) {
			if( line.equals( "START_NOTES" ) ) {
				for( String line2 = in.readLine(); !line2
						.equals( "END_NOTES" ); line2 = in.readLine() ) {
					notes_ += line2 + "\n";
				}
				continue;
			}

			String[] split = line.split( "\\s+" );

			if( split[ 0 ].equals( "source" ) ) {
				int node_id = Integer.parseInt( split[ 1 ] );
				for( Node n : nodes ) {
					if( n.id() == node_id ) {
						source_node_ = n;
						break;
					}
				}
				continue;
			}

			if( split[ 0 ].equals( "destination" ) ) {
				int node_id = Integer.parseInt( split[ 1 ] );
				for( Node n : nodes ) {
					if( n.id() == node_id ) {
						destination_node_ = n;
						break;
					}
				}
				continue;
			}

			if( split[ 0 ].equals( "column" ) ) {
				column_name_to_sort_by_ = split[ 1 ];
				continue;
			}

			if( split[ 0 ].equals( "pos_is_better" ) ) {
				positive_scores_are_better_ = Boolean.parseBoolean( split[ 1 ] );
				continue;
			}

			if( split[ 0 ].equals( "num" ) ) {
				num_results_to_transfer_ = Integer.parseInt( split[ 1 ] );
				continue;
			}

			if( split[ 0 ].equals( "perc" ) ) {
				percentage_of_results_to_transfer_ = Double.parseDouble( split[ 1 ] );
				continue;
			}

			if( split[ 0 ].equals( "use_perc" ) ) {
				use_percentage_instead_of_count_ = Boolean.parseBoolean( split[ 1 ] );
				continue;
			}
		}
	}

	public final Node sourceNode() {
		return source_node_;
	}

	public final void setSourceNode( Node source_node ) {
		source_node_ = source_node;
	}

	public final Node destinationNode() {
		return destination_node_;
	}

	public final void setDestinationNode( Node destination_node ) {
		destination_node_ = destination_node;
	}

	public final String columnNameToSortBy() {
		return column_name_to_sort_by_;
	}

	public final void setColumnNameToSortBy( String setting ) {
		column_name_to_sort_by_ = setting;
	}

	public final boolean positiveScoresAreBetter() {
		return positive_scores_are_better_;
	}

	public final void setPositiveScoresAreBetter( boolean setting ) {
		positive_scores_are_better_ = setting;
	}

	public final int numResultsToTransfer() {
		return num_results_to_transfer_;
	}

	public final void setNumResultsToTransfer( int setting ) {
		num_results_to_transfer_ = setting;
	}

	public final double percentageOfResultsToTransfer() {
		return percentage_of_results_to_transfer_;
	}

	public final void setPercentageOfResultsToTransfer( double setting ) {
		percentage_of_results_to_transfer_ = setting;
	}

	public final boolean usePercentageInsteadOfCount() {
		return use_percentage_instead_of_count_;
	}

	public final void setUsePercentageInsteadOfCount( boolean setting ) {
		use_percentage_instead_of_count_ = setting;
	}

	public final String getNotes() {
		return notes_;
	}

	public final void setNotes( String notes ) {
		notes_ = notes;
	}

	// Save/load
	public void save( BufferedWriter out ) throws IOException {
		String save_string = "START_EDGE\n";
		save_string += "source " + source_node_.id() + "\n";
		save_string += "destination " + destination_node_.id() + "\n";
		save_string += "column " + column_name_to_sort_by_ + "\n";
		save_string += "pos_is_better " + positive_scores_are_better_ + "\n";

		save_string += "num " + num_results_to_transfer_ + "\n";
		save_string += "perc " + percentage_of_results_to_transfer_ + "\n";
		save_string += "use_perc " + use_percentage_instead_of_count_ + "\n";

		save_string += "START_NOTES\n";
		save_string += notes_;
		save_string += "END_NOTES\n";

		save_string += "END_EDGE\n";
		out.write( save_string );
	}
}
