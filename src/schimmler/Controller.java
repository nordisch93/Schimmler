package schimmler;

public class Controller {
	
	private Model model;
	
	public Controller(Model model) {
		this.model = model;
	}
	
	public boolean setSelection(int BlockId) {
		model.setCurrentSelection(BlockId);		
		return true;
	}
	
	public boolean moveBlock(int direction) {
		int currentBlock = model.getCurrentSelection();
		if(currentBlock == 0)
			return false;
		else
			return model.moveBlock(currentBlock, direction);
	}
	
	
}
