package graph;

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
}
