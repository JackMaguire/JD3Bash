package graph;

public class PreliminaryEdge {
	public Node source_node;
	public int x;
	public int y;

	public PreliminaryEdge( Node source, int cursor_x, int cursor_y ) {
		source_node = source;
		x = cursor_x;
		y = cursor_y;
	}
}
