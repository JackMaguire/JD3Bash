package graph;

import java.util.ArrayList;

public class Graph {

	private ArrayList< Node > nodes_;
	private ArrayList< Edge > edges_;

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

	public int getNumEdges() {
		return edges_.size();
	}

	public Edge getEdge( int index ) {
		return edges_.get( index );
	}

}
