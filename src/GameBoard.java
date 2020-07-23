import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;


import javax.swing.*;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {

	//Board specifics
	private static GameBoard instance;

	private static final int NUM_ROW_COL = 10;
	private static final int BOARD_WIDTH = 400;
	private static final int BOARD_HEIGHT = 400;
	private static final int INTERVAL = 35;
	private Tile[][] board; 
	
	//Game Specifics
	
	private boolean playing = false; 
	private int score;
	public static int counter;

	Set<Point> toDelete = new HashSet<Point>();

	private GameBoard() {
		board = new Tile[NUM_ROW_COL][NUM_ROW_COL];
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start();
		score = 0;
		setFocusable(true);
	}

	public static GameBoard getInstance() {
		if(instance == null) {
			instance = new GameBoard();
		}
		return instance;
	}
	
	//Add Tiles to Board

	public void fillBoard() { 
		for (int i = 0; i < NUM_ROW_COL; i++) {
			for (int j = 0; j < NUM_ROW_COL; j++) {
				if (board[i][j] == null) {
					Tile newTile = new Tile();
					board[i][j] = newTile; 
				}
			}
		} 
	}
	
	//Reset the board

	public void newGameBoard() { 
		clearBoard();
		fillBoard();
		resetScore();
		counter = 0;
		playing = true;
		requestFocusInWindow();
	}
	
	//Adds matching tiles to Stack

	public boolean checkMatches(int x, int y) { 
		toDelete.add(new Point(x,y));
		Color c = board[x][y].getColor();
		int xLeft = x;  
		while (xLeft - 1 >= 0 && board[xLeft - 1][y].getColor().equals(c)) {
			toDelete.add(new Point(xLeft - 1, y));
			xLeft--;
		}
		int xRight = x;
		while (xRight + 1 < NUM_ROW_COL && board[xRight + 1][y].getColor().equals(c)) {
			toDelete.add(new Point(xRight + 1, y));
			xRight++;
		}  
		int yDown = y;
		while (yDown + 1 < NUM_ROW_COL && board[x][yDown + 1].getColor().equals(c)) {
			toDelete.add(new Point(x, yDown + 1));
			yDown++;
		}
		int yUp = y;
		while (yUp - 1 >= 0 && board[x][yUp - 1].getColor().equals(c)) {
			toDelete.add(new Point(x, yUp - 1));
			yUp--;
		} if (toDelete.size() >= 3) {
			score += toDelete.size() * 10; //increments score
			return true;
		} toDelete.clear();
		return false;
	} 
	
	//Removes the matched chains of tiles

	public void removeChains() { 
		for (Point tile : toDelete) {
			board[(int) tile.getX()][(int) tile.getY()].setToRemove();
		} for (int i = 0; i < NUM_ROW_COL; i++) {
			for (int j = 0; j < NUM_ROW_COL; j++) {
				if (board[i][j].isToBeRemoved()) {
					board[i][j] = null;	 
				}
			}
		} toDelete.clear();
	}

	
	//drops above tiles down to fill in removed match
	public void moveDown() { 
		for (int i = 0; i < NUM_ROW_COL; i++) {
			for (int j = 0; j < NUM_ROW_COL; j++) {
				if (board[i][j] != null) {
					if (j + 1 < NUM_ROW_COL) {
						if (board[i][j + 1] == null) {
							board[i][j + 1] = board[i][j];
							board[i][j] = null;
						}
					}
				}

			}
		}
		if (emptySpaces()) {
			moveDown();
		}
	}
	
	//returns whether or not there are missing spots on the board

	public boolean emptySpaces() { 
		for (int i = 0; i < NUM_ROW_COL; i++) {
			for (int j = 0; j < NUM_ROW_COL; j++) {
				if (board[i][j] != null) {
					if (j + 1 < NUM_ROW_COL) {
						if (board[i][j + 1] == null) {
							return true;
						}
					}
				}

			}
		}
		return false;
	}


	//checks if switch is valid
	
	public boolean validMove(int x1, int y1, int x2, int y2) {
		if (((x1 == x2 - 1 || x1 == x2 + 1) && y1 == y2) || 
				((y1 == y2 - 1 || y1 == y2 + 1) && x1 == x2)) {
			return true;
		} else {
			return false;
		}
	}

	//switches two chosen tiles
	
	public void switchTiles(int x1, int y1, int x2, int y2) {  
		Tile temp = board[x1][y1];
		board[x1][y1] = board[x2][y2];
		board[x2][y2] = temp;
	}
	
	// Keeps time in game

	private void tick() {
		if (playing) {
			counter++;
			repaint();
		}
	}

	public boolean inPlay() {
		return playing;
	}
	
	//Score methods

	public int getScore() {
		return score;
	}

	public void resetScore() {
		score = 0;
	}
	
	//Checks if moves are in bounds

	public static boolean inBounds(double x, double y) {
		return (x >= 10 & x <= BOARD_WIDTH + 10 & 
				y >= 10 & y <= BOARD_HEIGHT + 10);
	} 

	public Tile getTile(int x, int y) {
		return board[x][y];
	}

	public void setTile(int x, int y, Tile j) {
		board[x][y] = j;
	}

	public static int getBoardCoord(double n) {
		if (n >= 10 && n < 50) {
			return 0;
		} else if (n >= 50 && n < 90) {
			return 1;
		} else if (n >= 90 && n < 130) {
			return 2;
		} else if (n >= 130 && n < 170) {
			return 3;
		} else if (n >= 170 && n < 210) {
			return 4;
		} else if (n >= 210 && n < 250) {
			return 5;
		} else if (n >= 250 && n < 290) {
			return 6;
		} else if (n >= 290 && n <= 330) {
			return 7;
		} else if (n >= 330 && n <= 370) {
			return 8;
		} else if (n >= 370 && n <= 410) {
			return 9;
		} else 
			return -1;
	}

	public static int getDrawingCoord(int n) {
		if (n == 0) {
			return 15;
		} else if (n == 1) {
			return 55;
		} else if (n == 2) {
			return 95;
		} else if (n == 3) {
			return 135;
		} else if (n == 4) {
			return 175;
		} else if (n == 5) {
			return 215;
		} else if (n == 6) {
			return 255;
		} else if (n == 7) {
			return 295;
		} else if (n == 8) {
			return 335;
		} else if (n == 9) {
			return 375;
		} else 
			return -1;
	} 

	//draws board
	
	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(10, 10, BOARD_WIDTH, BOARD_HEIGHT);
		g.setColor(Color.WHITE);
		int n = 10;
		while (n <= BOARD_WIDTH + 10) {
			g.drawLine(n, 10, n, BOARD_HEIGHT + 10);
			g.drawLine(10, n, BOARD_WIDTH + 10, n);
			n += 40;
		} for (int i = 0; i < NUM_ROW_COL; i++) {
			for (int j = 0; j < NUM_ROW_COL; j++) {
				if (board[i][j] != null) {
					board[i][j].drawAt(g, i, j);
				}
			}
		}
	}
	
	//clears entire board of tiles
	
		public void clearBoard() { 
			for (int i = 0; i < NUM_ROW_COL; i++) {
				for (int j = 0; j < NUM_ROW_COL; j++) {
					if (board[i][j] != null) {
						board[i][j] = null;
					}
				}
			}
		}

	//The following methods are used for testing purposes
	public boolean isFull() {
		boolean full = true;
		for (int i = 0; i < NUM_ROW_COL; i++) {
			for (int j = 0; j < NUM_ROW_COL; j++) {
				if (board[i][j] == null) {
					full = false;
				}
			}
		} return full;
	}

	public boolean isEmpty() {
		boolean empty = true;
		for (int i = 0; i < NUM_ROW_COL; i++) {
			for (int j = 0; j < NUM_ROW_COL; j++) {
				if (board[i][j] != null) {
					empty = false;
				}
			}
		} return empty;
	}
}
