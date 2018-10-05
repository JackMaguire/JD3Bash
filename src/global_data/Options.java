package global_data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import exceptions.LoadFailureException;

public class Options {

	//////////////
	// View Options
	private static boolean show_node_titles_ = true;

	public static boolean getShowNodeTitles() {
		return show_node_titles_;
	}

	public static void setShowNodeTitles( boolean show_node_titles ) {
		show_node_titles_ = show_node_titles;
	}

	private static boolean put_node_titles_to_side_ = true;

	public static boolean getPutNodeTitlesToSide() {
		return put_node_titles_to_side_;
	}

	public static void setPutNodeTitlesToSide( boolean put_node_titles_to_side ) {
		put_node_titles_to_side_ = put_node_titles_to_side;
	}

	private static int grid_size_ = 10;

	public static int getGridSize() {
		return grid_size_;
	}

	public static void setGridSize( int setting ) {
		grid_size_ = setting;
	}

	/////////////////
	// Compile Options
	private static boolean serialize_intermediate_poses_ = false;

	public static boolean getSerializeIntermediatePoses() {
		return serialize_intermediate_poses_;
	}

	public static void setSerializeIntermediatePoses( boolean serialize_intermediate_poses ) {
		serialize_intermediate_poses_ = serialize_intermediate_poses;
	}

	//////////////
	// Run Options
	private static int num_processors_ = 0;

	public static int getNumProcessors() {
		return num_processors_;
	}

	public static void setNumProcessors( int nproc ) {
		num_processors_ = nproc;
	}

	private static String default_run_command_ = "mpirun -n $nproc rosetta_scripts.mpiserialization.linuxgccrelease @ flags";

	public static String getDefaultRunCommand() {
		return default_run_command_;
	}

	public static void setDefaultRunCommand( String setting ) {
		default_run_command_ = setting;
	}

	private static boolean delete_unused_intermediate_poses_ = true;
	
	public static boolean getDeleteUnusedIntermediatePoses() {
		return delete_unused_intermediate_poses_;
	}

	public static void setDeleteUnusedIntermediatePoses( boolean delete_unused_intermediate_poses ) {
		delete_unused_intermediate_poses_ = delete_unused_intermediate_poses;
	}
	
	////////////
	// Save/Load
	public static void save( BufferedWriter out ) throws IOException {
		out.write( "START_OPTIONS\n" );
		out.write( "show_node_titles " + show_node_titles_ + "\n" );
		out.write( "put_node_titles_to_side " + put_node_titles_to_side_ + "\n" );
		out.write( "num_processors " + num_processors_ + "\n" );
		out.write( "default_run_command " + default_run_command_ + "\n" );
		out.write( "delete_unused_intermediate_poses " + delete_unused_intermediate_poses_ + "\n" );
		out.write( "grid_size " + grid_size_ + "\n" );
		out.write( "END_OPTIONS\n" );
	}

	public static void load( BufferedReader in ) throws IOException, LoadFailureException {
		final String first_line = in.readLine();
		if( !first_line.equals( "START_OPTIONS" ) ) {
			throw new LoadFailureException( "Expected 'START_OPTIONS' instead of '" + first_line + "'" );
		}

		for( String line = in.readLine(); !line.equals( "END_OPTIONS" ); line = in.readLine() ) {
			final String[] split = line.split( "\\s+" );
			if( split.length == 0 )
				continue;

			if( split[ 0 ].equals( "show_node_titles" ) ) {
				show_node_titles_ = Boolean.parseBoolean( split[ 1 ] );
				continue;
			}

			if( split[ 0 ].equals( "put_node_titles_to_side" ) ) {
				put_node_titles_to_side_ = Boolean.parseBoolean( split[ 1 ] );
				continue;
			}

			if( split[ 0 ].equals( "num_processors" ) ) {
				num_processors_ = Integer.parseInt( split[ 1 ] );
				continue;
			}

			if( split[ 0 ].equals( "grid_size" ) ) {
				grid_size_ = Integer.parseInt( split[ 1 ] );
				continue;
			}

			if( split[ 0 ].equals( "default_run_command" ) ) {
				default_run_command_ = split[ 1 ];
				for( int i = 2; i < split.length; ++i ) {
					default_run_command_ += " " + split[ i ];
				}
				continue;
			}

			if( split[ 0 ].equals( "delete_unused_intermediate_poses" ) ) {
				delete_unused_intermediate_poses_ = Boolean.parseBoolean( split[ 1 ] );
				continue;
			}
		}
	}
}
