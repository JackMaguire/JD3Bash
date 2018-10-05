package undo;

public interface UndoableCommand {

	public void performUndo();
	
	public void performRedo();
	
}
