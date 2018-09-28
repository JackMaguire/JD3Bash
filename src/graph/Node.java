package graph;

import java.util.ArrayList;

public class Node {

	private int id_;
	
	//GUI position
	private int x_;
	private int y_;
	
	private ArrayList< Edge > upstream_edges_;//Connecting to nodes that occur before this node
	private ArrayList< Edge > downstream_edges_;//Connecting to nodes that occur after this node

	private String command_ = "rosetta_scripts.mpi.linuxgccrelease @ flags";
	
	public Node( int id, int x, int y ) {
		id_ = id;
		x_ = x;
		y_ = y;
		
		upstream_edges_ = new ArrayList< Edge >();
		downstream_edges_ = new ArrayList< Edge >();
	}
	
	///////////////////////
	//Getters and Setters//
	///////////////////////
	
	public int id() {
		return id_;
	}
	
	public void setId( int id ) {
		id_ = id;
	}
	
	public int x() {
		return x_;
	}
	
	public void setX( int x ) {
		x_ = x;
	}
	
	public int y() {
		return y_;
	}
	
	public void setY( int y ) {
		y_ = y;
	}
	
	public void addUpstreamEdge( Edge e ) {
		upstream_edges_.add( e );
	}
	
	public void removeUpstreamEdge( Edge e ) {
		upstream_edges_.remove( e );
	}
	
	public int numUpstreamEdges() {
		return upstream_edges_.size();
	}
	
	public Edge getUpstreamEdge( int i ) {
		//ArrayList does error handling so we don't have to
		return upstream_edges_.get( i );
	}
	
	public void addDownstreamEdge( Edge e ) {
		downstream_edges_.add( e );
	}
	
	public void removeDownstreamEdge( Edge e ) {
		downstream_edges_.remove( e );
	}
	
	public int numDownstreamEdges() {
		return downstream_edges_.size();
	}
	
	public Edge getDownstreamEdge( int i ) {
		//ArrayList does error handling so we don't have to
		return downstream_edges_.get( i );
	}
	
	public String getCommand() {
		return command_;
	}
	
	public String command() {
		return command_;
	}
	
	public void setCommand( String setting ) {
		command_ = setting;
	}
	
}
