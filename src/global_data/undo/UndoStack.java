package global_data.undo;

import java.util.LinkedList;

public class UndoStack {

	private final static LinkedList< UndoableCommand > past_commands = new LinkedList< UndoableCommand >();
	private final static LinkedList< UndoableCommand > future_commands = new LinkedList< UndoableCommand >();

	/* Say user performs actions A, B, C, D, E, F, G, and H and then hits 'undo' 3 times
	 * 
	 * past_commands: {A, B, C, D, E}
	 * 
	 * future_commands: {F, G, H}
	 */
	
	public static void undo() {
		if( past_commands.isEmpty() ) return;
		
		final UndoableCommand command = past_commands.removeLast();
		command.performUndo();
		future_commands.addFirst( command );
	}
	
	public static void redo() {
		if( future_commands.isEmpty() ) return;
		
		final UndoableCommand command = future_commands.removeFirst();
		command.performRedo();
		past_commands.addLast( command );
	}
	
	public static void addCommand( UndoableCommand command ) {
		past_commands.addLast( command );
		future_commands.clear();
	}
	
}
