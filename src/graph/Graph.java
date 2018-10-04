package graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exceptions.LoadFailureException;

public class Graph {

	private ArrayList< Node > nodes_;
	private ArrayList< Edge > edges_;
	private Node selected_node_;
	private Edge selected_edge_;
	private int next_node_id_ = 0;

	private PreliminaryEdge ghost_edge_ = null;// represents edges that are in the
																							// middle of being drawn

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
		n.setId( getNextNodeID() );
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
		if( n != null ) {
			selected_edge_ = null;
		}
	}

	public Edge selectedEdge() {
		return selected_edge_;
	}

	public void setSelectedEdge( Edge e ) {
		selected_edge_ = e;
		if( e != null ) {
			selected_node_ = null;
		}
	}

	public int getNextNodeID() {
		return next_node_id_++;
	}

	public PreliminaryEdge ghostEdge() {
		return ghost_edge_;
	}

	public void setGhostEdge( PreliminaryEdge ge ) {
		ghost_edge_ = ge;
	}
	
	public void removeNodeAndDeleteItsEdges( Node n ) {
		for( int i = n.numUpstreamEdges() - 1; i >= 0; --i ) {
			removeEdgeAndNotifyItsNodes( n.getUpstreamEdge( i ) );
		}
		
		for( int i = n.numDownstreamEdges() - 1; i >= 0; --i ) {
			removeEdgeAndNotifyItsNodes( n.getDownstreamEdge( i ) );
		}
		
		nodes_.remove( n );
	}
	
	public void removeEdgeAndNotifyItsNodes( Edge e ) {
		e.destinationNode().removeUpstreamEdge( e );
		e.sourceNode().removeDownstreamEdge( e );
		edges_.remove( e );
	}

	// Save/Load
	public void saveSelfNodesAndEdges( BufferedWriter out ) throws IOException {
		String save_string = "START_GRAPH\n";
		save_string += "next_node_id " + next_node_id_ + "\n";
		save_string += "num_nodes " + nodes_.size() + "\n";
		out.write( save_string );

		for( Node n : nodes_ ) {
			n.save( out );
		}

		out.write( "num_edges " + edges_.size() + "\n" );
		for( Edge e : edges_ ) {
			e.save( out );
		}

		out.write( "END_GRAPH\n" );
	}

	public void loadSelfNodesAndEdges( BufferedReader in )
			throws IOException, LoadFailureException {
		// First, Delete everything!
		selected_node_ = null;
		selected_edge_ = null;
		next_node_id_ = 0;
		nodes_.clear();
		edges_.clear();

		final String first_line = in.readLine();
		if( !first_line.equals( "START_GRAPH" ) ) {
			throw new LoadFailureException(
					"Expected 'START_GRAPH' instead of '" + first_line + "'" );
		}

		for( String line = in.readLine(); !line.equals( "END_GRAPH" ); line = in
				.readLine() ) {
			String[] split = line.split( "\\s+" );
			if( split.length == 0 )
				continue;
			if( split[ 0 ].equals( "next_node_id" ) ) {
				next_node_id_ = Integer.parseInt( split[ 1 ] );
				continue;
			}
			if( split[ 0 ].equals( "num_nodes" ) ) {
				int num_nodes = Integer.parseInt( split[ 1 ] );
				for( int i = 0; i < num_nodes; ++i ) {
					nodes_.add( new Node( in ) );
				}
				continue;
			}
			if( split[ 0 ].equals( "num_edges" ) ) {
				int num_edges = Integer.parseInt( split[ 1 ] );
				for( int i = 0; i < num_edges; ++i ) {
					edges_.add( new Edge( in, nodes_ ) );
				}
				continue;
			}
		} // for string line

		if( nodes_.isEmpty() ) {
			throw new LoadFailureException( "No Nodes Were Loaded" );
		}
		selected_node_ = nodes_.get( 0 );
	}// load self
}
