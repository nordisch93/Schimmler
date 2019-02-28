package schimmler;

public abstract class View {
	protected int currentBoard[][];
	protected int currentSelection;
	protected Model model;

	
	public View(Model model) {
		this.model = model;
		this.currentBoard = model.getBoard();
		this.currentSelection = model.getCurrentSelection();
	}
	
	public abstract void update();
}
