package global_data;

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
}
