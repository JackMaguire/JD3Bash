package apps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import compiler.GraphParsing;
import compiler.GraphParsingOptions;
import exceptions.InvalidGraphException;
import exceptions.LoadFailureException;
import global_data.Options;
import graph.Graph;
import utility.Pair;

public class CreateFilesFromSaveFile {

	private static String save_file = "";
	private static String setup_out_file = "setup.sh";
	private static String run_out_file = "run.sh";
	
	public static void main( String[] args ) {
		
		parseArgs( args );
		if( save_file.length() == 0 ) {
			System.err.println( "Please provide a save file using -s" );
			System.exit( -1 );
		}
		
		Graph graph = new Graph();
		
		BufferedReader in = null;
		try {
			in = new BufferedReader( new FileReader( save_file ) );
			graph.loadSelfNodesAndEdges( in );
			Options.load( in );
		}
		catch( FileNotFoundException e1 ) {
			System.err.println( "File " + save_file + " not found!\n" + e1.getMessage() );
			System.exit( -1 );
		}
		catch( IOException e1 ) {
			System.err.println( "Problem loading file " + save_file + "!\n" + e1.getMessage() );
			System.exit( -1 );
		}
		catch( LoadFailureException e1 ) {
			System.err.println( "Problem loading importing graph from " + save_file + "!\n" + e1.getMessage() );
			System.exit( -1 );
		}
		finally {
			if( in != null ) {
				try {
					in.close();
				}
				catch( IOException e1 ) {
					e1.printStackTrace();
				}
			}
		} // finally
		
		GraphParsingOptions options = new GraphParsingOptions();
		options.serialize_intermediate_poses = global_data.Options.getSerializeIntermediatePoses();
		Pair< String, String > run_and_setup = null;
		try {
			run_and_setup = GraphParsing.parseGraph( graph, options );
		}
		catch( InvalidGraphException e ) {
			System.err.println( "Unable to parse graph\n" + e.getMessage()  );
			e.printStackTrace();
			System.exit( -1 );
		}
		String run_script = run_and_setup.first;
		String setup_script = run_and_setup.second;
		
		try {
			BufferedWriter out = new BufferedWriter( new FileWriter( setup_out_file ) );
			out.write( setup_script + "\n" );
			out.close();
		}
		catch( IOException e ) {
			System.err.println( "Unable to write to " + setup_script + "\n" + e.getMessage() );
			e.printStackTrace();
		}
		
		try {
			BufferedWriter out = new BufferedWriter( new FileWriter( run_out_file ) );
			out.write( run_script + "\n" );
			out.close();
		}
		catch( IOException e ) {
			System.err.println( "Unable to write to " + run_script + "\n" + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	private static void parseArgs( String[] args ) {
		
		for( int i=0; i<args.length; ++i ) {
			
			if( args[ i ].equals( "-s" ) ) {
				if( ++i == args.length ) {
					System.err.println( "-s requires an option" );
					System.exit( 1 );
				}
				
				save_file = args[ i ];
			}
			
			if( args[ i ].equals( "-setup" ) ) {
				if( ++i == args.length ) {
					System.err.println( "-setup requires an option" );
					System.exit( 1 );
				}
				
				setup_out_file = args[ i ];
			}
			
			if( args[ i ].equals( "-run" ) ) {
				if( ++i == args.length ) {
					System.err.println( "-run requires an option" );
					System.exit( 1 );
				}
				
				run_out_file = args[ i ];
			}
			
		}
		
	}
	
}
