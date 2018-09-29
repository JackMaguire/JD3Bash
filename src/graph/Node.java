package graph;

import java.util.ArrayList;

import exceptions.UndefinedValueException;

public class Node {

	private int id_;

	// GUI position
	private int x_;
	private int y_;

	private ArrayList< Edge > upstream_edges_;// Connecting to nodes that occur before this node
	private ArrayList< Edge > downstream_edges_;// Connecting to nodes that occur after this node

	private String command_ = "mpirun -n $nproc rosetta_scripts.mpi.linuxgccrelease @ flags";
	private String title_;

	// The graph parser will assign a stage to this node, set stage_is_valid_ to
	// true, run methods that call stage_, and set stage_is_valid_ to false
	// stage_is_valid_ is meant to prevent other methods from calling getStage() and
	// assuming it is the current stage when it is in fact unassigned
	private int stage_ = 0;
	private boolean stage_is_valid_ = false;

	
	
	public Node( int id, int x, int y ) {
		id_ = id;
		x_ = x;
		y_ = y;
		title_ = "[title " + id_ + "]";

		upstream_edges_ = new ArrayList< Edge >();
		downstream_edges_ = new ArrayList< Edge >();
	}

	public Node( int id, String title ) {
		id_ = id;
		x_ = 0;
		y_ = 0;
		title_ = title;

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

	public final ArrayList< Edge > upstreamEdges() {
		return upstream_edges_;
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

	public final ArrayList< Edge > downstreamEdges() {
		return downstream_edges_;
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

	public final String getTitle() {
		return title_;
	}

	public final String title() {
		return title_;
	}

	public final void setTitle( String setting ) {
		title_ = setting;
	}

	public final int getStage() throws UndefinedValueException {
		return stage();
	}

	public final int stage() throws UndefinedValueException {
		if( !stage_is_valid_ ) {
			throw new UndefinedValueException( "Node is prompted for stage_ which is in an undefined state" );
		}
		return stage_;
	}

	public final void setStage( int stage ) {
		stage_ = stage;
	}

	public final void setStageValidity( boolean setting ) {
		stage_is_valid_ = setting;
	}

	///////////////////////////
	// Graph Parsing Utilities//
	///////////////////////////
	public final int inDegreeIgnoringTheseNodes( ArrayList< Node > nodes_to_ignore ) {
		int degree = 0;
		for( Edge e : upstream_edges_ ) {
			Node upstream_node = e.sourceNode();
			if( !nodes_to_ignore.contains( upstream_node ) ) {
				++degree;
			}
		}
		return degree;
	}

	public final String dirname() throws UndefinedValueException {
		return "stage" + stage() + "_" + title_;
	}

}
