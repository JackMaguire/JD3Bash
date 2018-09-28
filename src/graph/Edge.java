package graph;

public class Edge {

	private Node source_node_;
	private Node destination_node_;
	
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
}
