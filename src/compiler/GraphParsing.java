package compiler;

import graph.*;
import info.VersionInfo;
import utility.PBRWrapper;
import utility.Pair;

import java.util.ArrayList;
import java.util.HashSet;

import exceptions.InvalidGraphException;
import exceptions.UndefinedValueException;
import global_data.Options;

/*  This can be as slow as we want it to be.
 *  Let's focus more on clarity and correctness than speed here.
 */

public class GraphParsing {

	// First string is the "run" script, second string is the "setup" script
	// This can return null if an error occurs!
	public static Pair< String, String > parseGraph( Graph g,
			GraphParsingOptions options ) throws InvalidGraphException {

		// Check for easy things
		if( cycleExists( g ) ) {
			throw new InvalidGraphException( "Cycle Detected In Graph" );
		}

		if( Options.getNumProcessors() == 0 ) {
			throw new InvalidGraphException(
					"Number of processors is not set. Please go to the 'Options' tab and select a number larger than 0" );
		}

		if( Options.getNumProcessors() < 0 ) {
			throw new InvalidGraphException(
					"Number of processors is less than zero: " + Options.getNumProcessors()
							+ ". Please go to the 'Options' tab and select a number larger than 0" );
		}

		PBRWrapper< String > run_script = new PBRWrapper< String >( "#!/bin/bash\n\n" );
		addGlobalIntroToScript( run_script );
		addGlobalVariablesToRunScript( run_script );

		PBRWrapper< String > setup_script = new PBRWrapper< String >( "#!/bin/bash\n\n" );
		addGlobalIntroToScript( setup_script );

		ArrayList< Node > nodes_in_order = determineOrderOfNodes( g );
		// Assign stages to Nodes
		for( int stage = 1; stage <= nodes_in_order.size(); ++stage ) {
			nodes_in_order.get( stage - 1 ).setStage( stage );
			nodes_in_order.get( stage - 1 ).setStageValidity( true );
		}
		for( int stage = 1; stage <= nodes_in_order.size(); ++stage ) {
			Node n = nodes_in_order.get( stage - 1 );// We are 1-indexing the stages
			try {
				createSetupInstructionsForNode( n, setup_script, options );
				createRunInstructionsForNode( n, run_script, options );
			}
			catch( UndefinedValueException e ) {
				System.err.println( "Node for stage " + stage + " of "
						+ nodes_in_order.size() + " somehow did not get initialized" );
				e.printStackTrace();
				return null;
			}
		}
		for( int stage = 1; stage <= nodes_in_order.size(); ++stage ) {
			nodes_in_order.get( stage - 1 ).setStageValidity( false );
		}
		return new Pair< String, String >( run_script.value, setup_script.value );
	}

	public static ArrayList< Node > determineOrderOfNodes( Graph g ) {
		ArrayList< Node > unassigned_nodes = new ArrayList< Node >();
		ArrayList< Node > assigned_nodes_in_order = new ArrayList< Node >();

		for( Node n : g.allNodes_const() ) {
			unassigned_nodes.add( n );
		}

		/*for( int j = unassigned_nodes.size() - 1; j >= 0; --j ) {
			Node u_node = unassigned_nodes.get( j );
			if( u_node.inDegreeIgnoringTheseNodes( assigned_nodes_in_order ) == 0 ) {
				System.out.println( j + " " + u_node.numDownstreamEdges() + " " + u_node.numUpstreamEdges() );
			}
		}*/

		while ( true ) {
			// Add nodes that do not depend on any unassigned node
			// Work backwards so that we can easily delete while we work
			for( int j = unassigned_nodes.size() - 1; j >= 0; --j ) {
				Node u_node = unassigned_nodes.get( j );
				if( u_node.inDegreeIgnoringTheseNodes( assigned_nodes_in_order ) == 0 ) {
					assigned_nodes_in_order.add( u_node );
					unassigned_nodes.remove( j );
				}
			}

			if( unassigned_nodes.size() == 0 ) {
				break;
			}
		}

		return assigned_nodes_in_order;
	}

	public static boolean cycleExists( Graph g ) {
		// Recursion
		HashSet< Node > nodes_already_visited = new HashSet< Node >();
		for( Node n : g.allNodes_const() ) {
			if( cycleExists( n, nodes_already_visited ) ) {
				return true;
			}
		}
		return false;
	}

	private static boolean cycleExists( Node starting_node,
			HashSet< Node > nodes_already_visited ) {
		if( nodes_already_visited.contains( starting_node ) ) {
			return true;
		}
		nodes_already_visited.add( starting_node );
		for( Edge e : starting_node.downstreamEdges_const() ) {
			if( cycleExists( e.destinationNode(), nodes_already_visited ) ) {
				return true;
			}
		}
		nodes_already_visited.remove( starting_node );
		return false;
	}

	private static void createSetupInstructionsForNode( Node n,
			PBRWrapper< String > setup_script, GraphParsingOptions options )
			throws UndefinedValueException {

		addStageIntroToScript( n.stage(), setup_script );
		final String dirname = n.dirname();

		setup_script.value += "mkdir " + dirname + "\n";
		if( n.numUpstreamEdges() > 0 ) {
			setup_script.value += "touch " + dirname + "/input_files\n";
		}

		for( String flag : n.getAllRosettaFlags() ) {
			setup_script.value += "echo \"" + flag + "\" >> " + dirname + "/flags\n";
		}

		if( !n.getUseScriptFile() ) {
			// Write out custom file
			String[] split = n.getScript().split( "\n" );
			for( String s : split ) {
				final String reformatted_quotes = s.replaceAll( "'", "\"" );// Using double quotes for
																																		// everything so that we can use
																																		// single quotes
				setup_script.value += "echo '" + reformatted_quotes + "' >> " + dirname + "/script.xml\n";
			}
		}

		if( options.serialize_intermediate_poses ) {
			if( n.numUpstreamEdges() > 0 ) {
				setup_script.value += "echo \"-in:file:srlz 1\" >> " + dirname + "/flags\n";
			}

			if( n.numDownstreamEdges() > 0 ) {
				setup_script.value += "echo \"-out:file:srlz 1\" >> " + dirname + "/flags\n";
			}
		}
	}

	private static void createRunInstructionsForNode( Node n,
			PBRWrapper< String > run_script, GraphParsingOptions options )
			throws UndefinedValueException {

		addStageIntroToScript( n.stage(), run_script );
		final String dirname = n.dirname();

		run_script.value += "\ncd " + dirname + "\n";

		// THE COMMAND

		run_script.value += "if " + n.getEffectiveCommand() + " ;then \n"
				+ "    echo \"Done running " + dirname + "\" >> ../JD3BASH_runlog.txt\n"
				+ "else\n"
				+ "    echo \"Failed to run " + dirname + "\" >> ../JD3BASH_runlog.txt\n"
				+ "    exit 1\n"
				+ "fi\n";

		if( n.numDownstreamEdges() > 0 ) {
			run_script.value += "grep -v 'SEQUENCE:' score.sc > no_first_line.score.sc\n";
			for( Edge de : n.downstreamEdges_const() ) {
				final String name_of_next_stage_directory = de.destinationNode().dirname();
				final String sort_column = de.columnNameToSortBy();
				run_script.value += "\n#####\n";
				run_script.value += "# Extract the best results for stage \""
						+ de.destinationNode().getTitle() + "\"\n";
				run_script.value += "# This awk command prints the data for the column with header "
						+ sort_column + " along with the title for each result\n";
				run_script.value += "awk -v c1=\"" + sort_column
						+ "\" 'NR==1 {for (i=1; i<=NF; i++) {ix[$i] = i}}NR>1 {print $ix[c1] \" \" $NF}' no_first_line.score.sc > temp\n";

				if( de.positiveScoresAreBetter() ) {
					run_script.value += "sort -nrk1 temp > temp2\n";
				} else {
					run_script.value += "sort -nk1 temp > temp2\n";
				}

				run_script.value += "x=`cat no_first_line.score.sc | wc -l`\n";
				if( de.usePercentageInsteadOfCount() ) {
					run_script.value += "perc=\"" + de.percentageOfResultsToTransfer() + "\"\n";
					run_script.value += "nresults=`echo \"($x - 1) * $perc / 1\" | bc`\n";
				} else {
					run_script.value += "nresults=\"" + de.numResultsToTransfer() + "\"\n";
				}
				run_script.value += "# Extract structures that will survive until the next stage\n";
				run_script.value += "head -n $nresults temp2 | awk '{print $2}' > temp3\n";
				run_script.value += "cat temp3 | while read line; do echo `pwd`/$line.* ; done >> ../"
						+ name_of_next_stage_directory + "/input_files\n";
			}

		}

		run_script.value += "\ncd ..\n";
		run_script.value += "echo \"Done With " + dirname + "\" >> JD3BASH_runlog.txt\n";
	}

	private static void addGlobalIntroToScript( PBRWrapper< String > script ) {
		script.value += "# Script was created using JD3BASH\n";
		script.value += "# Version number: " + VersionInfo.current_version + "\n";
		script.value += "# Visit github.com/JackMaguire/JD3Bash for details\n\n";
	}

	private static void addStageIntroToScript( int stage, PBRWrapper< String > script ) {
		script.value += "\n###########\n";
		script.value += "# STAGE " + stage + " #\n";
		script.value += "###########\n\n";
	}

	private static void addGlobalVariablesToRunScript( PBRWrapper< String > script ) {
		script.value += "nproc=" + Options.getNumProcessors() + "\n";
	}

}
