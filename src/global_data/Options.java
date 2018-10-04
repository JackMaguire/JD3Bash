package global_data;

public class Options {

	//////////////
	//View Options
	private static boolean show_node_titles_ = false;
	public static boolean getShowNodeTitles() {
		return show_node_titles_;
	}
	public static void setShowNodeTitles( boolean show_node_titles ) {
		show_node_titles_ = show_node_titles;
	}
	
	private static boolean put_node_titles_to_side_ = false;
	public static boolean getPutNodeTitlesToSide() {
		return put_node_titles_to_side_;
	}
	public static void setPutNodeTitlesToSide( boolean put_node_titles_to_side ) {
		put_node_titles_to_side_ = put_node_titles_to_side;
	}
	
	
	/////////////////
	//Compile Options
	private static boolean serialize_intermediate_poses_ = false;
	public static boolean getSerializeIntermediatePoses() {
		return serialize_intermediate_poses_;
	}
	public static void setSerializeIntermediatePoses( boolean serialize_intermediate_poses ) {
		serialize_intermediate_poses_ = serialize_intermediate_poses;
	}
}
