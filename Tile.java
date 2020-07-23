import java.awt.*;

public class Tile {
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	private Color[] colors = { Color.RED, Color.PINK, Color.BLUE, 
			Color.LIGHT_GRAY, Color.MAGENTA, Color.WHITE, Color.CYAN, Color.DARK_GRAY, Color.GREEN }; 
	private Color c; 
	private boolean selected; 
	private boolean toRemove; 

	public Tile() {
		int rand = (int) Math.floor(Math.random() * 9);
		this.c = colors[rand];
		this.selected = false;
		this.toRemove = false;
	}

	//for testing
	public Tile(Color c) {
		this.c = c;
		this.selected = false;
		this.toRemove = false;
	}

	public Color getColor() {
		return this.c;
	}

	public void setSelected() {
		selected = true;
	}

	public void setUnselected() {
		selected = false;
	}

	public void setToRemove() {
		toRemove = true;
	}

	public boolean isToBeRemoved() {
		return toRemove;
	}

	public void drawAt(Graphics g, int x, int y) {
		int xDraw = GameBoard.getDrawingCoord(x);
		int yDraw = GameBoard.getDrawingCoord(y);
		g.setColor(c);
		g.fillOval(xDraw, yDraw, WIDTH, HEIGHT);
		if (selected) {
			int offset = 2;
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(4));
			g.setColor(Color.WHITE);
			g.drawOval(xDraw - offset, yDraw - offset, WIDTH + 2 * offset, HEIGHT + 2 * offset);
		}
	}


}