package global_data.undo;

public interface UndoableCommand {

	public void performUndo();

	public void performRedo();

}
