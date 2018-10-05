package graph.undo;

import graph.Graph;
import graph.Node;
import undo.UndoableCommand;

public class AddNodeCommand implements UndoableCommand {
	
	private final Graph graph_;
	private final Node node_;
	
	public AddNodeCommand( Graph g, Node n ) {
		graph_ = g;
		node_ = n;
	}

	@Override
	public void performUndo() {
		if( node_.numDownstreamEdges() > 0 || node_.numUpstreamEdges() > 0 ) {
			System.err.println( "Node being removed has edges that 'redo' cannot bring back." );
		}
		graph_.removeNodeAndDeleteItsEdges( node_ );
	}

	@Override
	public void performRedo() {
		graph_.addNode( node_ );
	}

	
	
}
