package schimmler;

import java.awt.Color;

public abstract class View {
	protected int currentBoard[][];
	protected int currentSelection;
	protected Model model;

	
	public View(Model model) {
		this.model = model;
		this.currentBoard = model.getBoardState();
		this.currentSelection = model.getCurrentSelection();
	}
	
	public abstract void update();
	
	public enum CustomColor {
		COLOR_DARKRED(230, 0, 0), COLOR_DARKGREEN(0, 153, 0), COLOR_DARKORANGE(255, 102, 0),
		COLOR_DARKPURPLE(204, 0, 153), COLOR_DARKBLUE(41, 41, 163), COLOR_WHITE(255, 255, 255),

		COLOR_LIGHTRED(255, 102, 102), COLOR_LIGHTGREEN(26, 255, 26), COLOR_LIGHTORANGE(255, 179, 128),
		COLOR_LIGHTPURPLE(255, 77, 210), COLOR_LIGHTBLUE(114, 114, 218);

		private int r;
		private int g;
		private int b;
		
		private Color color;

		private CustomColor(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
			this.color = new Color(r,g,b);
		}
		
		public Color getColor() { return color; }
		public int getR() { return r; }
		public int getG() { return g; }
		public int getB() { return b; }
	}
}
