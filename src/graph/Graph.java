package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {

	private ArrayList< Node > nodes_;
	private ArrayList< Edge > edges_;
	private Node selected_node_;
	
	public Graph() {
		nodes_ = new ArrayList< Node >();
		edges_ = new ArrayList< Edge >();
	}

	public int getNumNodes() {
		return nodes_.size();
	}

	public Node getNode( int index ) {// zero-indexed
		return nodes_.get( index );
	}

	public Node addNode( Node n ) {
		nodes_.add( n );
		return n;
	}

	public List< Node > allNodes_const() {
		return Collections.unmodifiableList( nodes_ );
	}

	public int getNumEdges() {
		return edges_.size();
	}

	public Edge getEdge( int index ) {
		return edges_.get( index );
	}

	public Edge addEdge( Node source, Node dest ) {
		Edge new_edge = new Edge( source, dest );
		source.addDownstreamEdge( new_edge );
		dest.addUpstreamEdge( new_edge );
		return new_edge;
	}

	public Node selectedNode() {
		return selected_node_;
	}
	
	public void setSelectedNode( Node n ) {
		selected_node_ = n;
	}
}
