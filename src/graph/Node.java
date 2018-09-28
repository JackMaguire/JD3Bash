package graph;

import java.util.ArrayList;

public class Node {

	private int id_;

	// GUI position
	private int x_;
	private int y_;

	private ArrayList< Edge > upstream_edges_;// Connecting to nodes that occur before this node
	private ArrayList< Edge > downstream_edges_;// Connecting to nodes that occur after this node

	private String command_ = "rosetta_scripts.mpi.linuxgccrelease @ flags";

	public Node( int id, int x, int y ) {
		id_ = id;
		x_ = x;
		y_ = y;

		upstream_edges_ = new ArrayList< Edge >();
		downstream_edges_ = new ArrayList< Edge >();
	}

	///////////////////////
	// Getters and Setters//
	///////////////////////

	public final int id() {
		return id_;
	}

	public final void setId( int id ) {
		id_ = id;
	}

	public final int x() {
		return x_;
	}

	public final void setX( int x ) {
		x_ = x;
	}

	public final int y() {
		return y_;
	}

	public final void setY( int y ) {
		y_ = y;
	}

	public final void addUpstreamEdge( Edge e ) {
		upstream_edges_.add( e );
	}

	public final void removeUpstreamEdge( Edge e ) {
		upstream_edges_.remove( e );
	}

	public final int numUpstreamEdges() {
		return upstream_edges_.size();
	}

	public final Edge getUpstreamEdge( int i ) {
		// ArrayList does error handling so we don't have to
		return upstream_edges_.get( i );
	}

	public final void addDownstreamEdge( Edge e ) {
		downstream_edges_.add( e );
	}

	public final void removeDownstreamEdge( Edge e ) {
		downstream_edges_.remove( e );
	}

	public final int numDownstreamEdges() {
		return downstream_edges_.size();
	}

	public final Edge getDownstreamEdge( int i ) {
		// ArrayList does error handling so we don't have to
		return downstream_edges_.get( i );
	}

	public final String getCommand() {
		return command_;
	}

	public final String command() {
		return command_;
	}

	public final void setCommand( String setting ) {
		command_ = setting;
	}

}
