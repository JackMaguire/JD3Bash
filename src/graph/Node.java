package graph;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exceptions.UndefinedValueException;

public class Node {

	private int id_;

	// GUI
	private int x_;
	private int y_;
	private Color color_ = Color.gray;

	private final ArrayList< Edge > upstream_edges_;// Connecting to nodes that occur before this node
	private final ArrayList< Edge > downstream_edges_;// Connecting to nodes that occur after this node

	private String command_ = "mpirun -n $nproc rosetta_scripts.mpiserialization.linuxgccrelease @ flags";
	private String title_;
	private String xml_script_ = "script.xml";

	// The graph parser will assign a stage to this node, set stage_is_valid_ to
	// true, run methods that call stage_, and set stage_is_valid_ to false
	// stage_is_valid_ is meant to prevent other methods from calling getStage() and
	// assuming it is the current stage when it is in fact unassigned
	private int stage_ = 0;
	private boolean stage_is_valid_ = false;

	private ArrayList< String > user_rosetta_flags_ = new ArrayList< String >();

	private String notes_ = "";

	public Node( int x, int y ) {
		x_ = x;
		y_ = y;
		title_ = "Node_" + id_;
		upstream_edges_ = new ArrayList< Edge >();
		downstream_edges_ = new ArrayList< Edge >();
		init();
	}

	public Node( String title, int x, int y ) {
		x_ = x;
		y_ = y;
		title_ = title;
		upstream_edges_ = new ArrayList< Edge >();
		downstream_edges_ = new ArrayList< Edge >();
		init();
	}

	private void init() {
		user_rosetta_flags_.add( "# Keep in mind that all commands will be run one directory deeper." );
		user_rosetta_flags_.add( "# You would need to pass '-s ../pose.pdb' instead of '-s pose.pdb'" );
		user_rosetta_flags_.add( "" );
		user_rosetta_flags_.add( "# -nstruct 1" );
	}

	///////////////////////
	// Getters and Setters//
	///////////////////////

	public final int id() {
		return id_;
	}

	public final void setId( int id ) {
		id_ = id;
		if( title_.equals( "Node_0" ) ) {
			title_ = "Node_" + id_;
		}
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

	public final Color color() {
		return color_;
	}

	public final Color getColor() {
		return color_;
	}

	public final void setColor( Color setting ) {
		color_ = setting;
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
		// ArrayList does bounds checking so we don't have to
		return upstream_edges_.get( i );
	}

	public final List< Edge > upstreamEdges_const() {
		return Collections.unmodifiableList( upstream_edges_ );
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

	public final List< Edge > downstreamEdges_const() {
		return Collections.unmodifiableList( downstream_edges_ );
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

	public final String getXMLScript() {
		return xml_script_;
	}

	public final String getScript() {
		return xml_script_;
	}

	public final void setXMLScript( String setting ) {
		xml_script_ = setting;
	}

	public final void setScript( String setting ) {
		xml_script_ = setting;
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

	public final void addUserRosettaFlag( String s ) {
		user_rosetta_flags_.add( s );
	}

	public final void setUserRosettaFlags( ArrayList< String > new_flags ) {
		/*int count = 0;
		for( StackTraceElement[] ste_array : Thread.getAllStackTraces().values() ) {
			System.out.println( count++ );
			for( StackTraceElement ste : ste_array ) {
				System.out.println( ste.toString() );
			}
		}
		System.exit( 0 );*/
		user_rosetta_flags_ = new_flags;
	}

	public final List< String > getUserRosettaFlags() {
		return Collections.unmodifiableList( user_rosetta_flags_ );
	}

	public final ArrayList< String > getAllRosettaFlags() {
		ArrayList< String > all_flags = new ArrayList< String >();

		all_flags.add( "# Generated Flags:" );
		for( String s : this.determineAutoFlags() ) {
			all_flags.add( s );
		}

		all_flags.add( "" );
		all_flags.add( "# Your Additional Flags:" );
		for( String s : user_rosetta_flags_ ) {
			all_flags.add( s );
		}

		return all_flags;
	}

	public String getNotes() {
		return notes_;
	}

	public void setNotes( String notes ) {
		notes_ = notes;
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

	public final ArrayList< String > determineAutoFlags() {
		ArrayList< String > list = new ArrayList< String >();

		if( upstream_edges_.size() > 0 ) {
			list.add( "-l input_files" );
			// list.add( "# -in:file:srlz 1 # Activate this by checking the 'Serialize
			// Intermediate Poses' box in the compile tab" );
		} else {
			list.add( "# Don't forget to add input file flags (-s, -l, etc)" );
		}

		if( downstream_edges_.size() > 0 ) {
			// list.add( "# -out:file:srlz 1 # Activate this by checking the 'Serialize
			// Intermediate Poses' box in the compile tab" );
		}

		list.add( "-parser:protocol ../" + xml_script_ );

		return list;
	}

	public final static ArrayList< String > commonFlags() {
		ArrayList< String > list = new ArrayList< String >();
		list.add( "-linmem_ig 10                   # save time and memory during packing" );
		list.add( "-ignore_unrecognized_res true   # false by default" );
		list.add( "-ignore_waters false            # true by default" );
		list.add( "-mpi_tracer_to_file mpi_" );
		return list;
	}
	
	///Save/Load
	public void save( BufferedWriter out ) throws IOException {
		String save_string = "START_NODE\n";
		save_string += "id " + id_ + "\n";
		save_string += "x " + x_ + "\n";
		save_string += "y " + y_ + "\n";
		save_string += "r " + color_.getRed() + "\n";
		save_string += "g " + color_.getGreen() + "\n";
		save_string += "b " + color_.getBlue() + "\n";
		save_string += "command " + command_ + "\n";
		save_string += "title " + title_ + "\n";
		save_string += "script " + xml_script_ + "\n";

		save_string += "START_FLAGS\n";
		for( String flag : user_rosetta_flags_ ) {
			save_string += flag + "\n";
		}
		save_string += "END_FLAGS\n";

		save_string += "START_NOTES\n";
		save_string += notes_ + "\n";
		save_string += "END_NOTES\n";
		
		save_string += "END_NODE\n";
		out.write( save_string );
	}
	
}
