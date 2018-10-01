package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {

	private ArrayList< Node > nodes_;
	private ArrayList< Edge > edges_;
	private Node selected_node_;
	private Edge selected_edge_;
	private int next_id_ = 0;

	private PreliminaryEdge ghost_edge_ = null;// represents edges that are in the middle of being drawn

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

	public List< Edge > allEdges_const() {
		return Collections.unmodifiableList( edges_ );
	}

	public int getNumEdges() {
		return edges_.size();
	}

	public Edge getEdge( int index ) {
		return edges_.get( index );
	}

	public Edge addEdge( Node source, Node dest ) {
		// Make sure there is not already an edge
		for( Edge de : source.downstreamEdges_const() ) {
			if( de.destinationNode() == dest ) {
				return null;
			}
		}
		// Make sure there is no reverse Edge
		for( Edge de : dest.downstreamEdges_const() ) {
			if( de.destinationNode() == source ) {
				return null;
			}
		}

		Edge new_edge = new Edge( source, dest );
		source.addDownstreamEdge( new_edge );
		dest.addUpstreamEdge( new_edge );
		edges_.add( new_edge );
		return new_edge;
	}

	public Node selectedNode() {
		return selected_node_;
	}

	public void setSelectedNode( Node n ) {
		selected_node_ = n;
	}

	public Edge selectedEdge() {
		return selected_edge_;
	}

	public void setSelectedEdge( Edge e ) {
		selected_edge_ = e;
	}

	public int getNextNodeID() {
		return next_id_++;
	}

	public PreliminaryEdge ghostEdge() {
		return ghost_edge_;
	}

	public void setGhostEdge( PreliminaryEdge ge ) {
		ghost_edge_ = ge;
	}
}
