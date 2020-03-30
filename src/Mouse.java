import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Mouse extends MouseAdapter{
	@Override
	public void mouseReleased(MouseEvent arg0) {
		GameBoard board = GameBoard.getInstance();
		if (board.inPlay()) {
			Point p = arg0.getPoint();
			double board_x = p.getX();
			double board_y = p.getY();
			if (GameBoard.inBounds(board_x, board_y)) {
				if (Game.tileSelected == false) {
					Game.tileSelected = true;
					Game.firstTile = p;
					int x = GameBoard.getBoardCoord(board_x);
					int y = GameBoard.getBoardCoord(board_y);
					board.getTile(x, y).setSelected();
				} else {
					Game.tileSelected = false;
					int x1 = GameBoard.getBoardCoord(board_x);
					int y1 = GameBoard.getBoardCoord(board_y);
					int x2 = GameBoard.getBoardCoord(Game.firstTile.getX());
					int y2 = GameBoard.getBoardCoord(Game.firstTile.getY());
					board.getTile(x2, y2).setUnselected();
					board.switchTiles(x1, y1, x2, y2); //switches selected tiles

					if (board.validMove(x1, y1, x2, y2)) {
						if (board.checkGroup(x1, y1) ||
								board.checkGroup(x2, y2)) {
							Game.status.setForeground(Color.BLACK);
							Game.status.setText("Successful Swap!"); //swap is valid
							board.removeChains(); 
							board.moveDown(); 
							board.fillBoard(); 
							Game.scoreDisplay.setText("Score: "+ board.getScore());
						} else {
							Game.status.setForeground(Color.RED);
							Game.status.setText("Unsuccessful swap."); //swap is invalid
							board.switchTiles(x1, y1, x2, y2);
						}
						Game.firstTile = null;
					} else {
						Game.status.setForeground(Color.RED);
						Game.status.setText("Unsuccessful swap."); //swap is invalid
						board.switchTiles(x1, y1, x2, y2);
					}
				} 
			}
			else {
				Game.status.setForeground(Color.RED);
				Game.status.setText("Please click within the board.");
			}
		}

		if (GameBoard.counter >= 571) { //game times out after 20s
			board.clearBoard();
			Game.status.setForeground(Color.BLACK);
			Game.status.setText("GAME OVER!");

			try {
				//Takes in user's name to associate with score
				String username = (String) JOptionPane.showInputDialog(
						new JFrame(), "Please enter your name!", 
						"NEW HIGH SCORE!", JOptionPane.PLAIN_MESSAGE, 
						null, null, "");

				if (Game.scores.get(board.getScore()) == null) {
					LinkedList<String> usernameList = new LinkedList<String>();
					Game.scores.put(board.getScore(), usernameList);
					usernameList.add(username);
				} else {
					Game.scores.get(board.getScore()).add(username);
				}

				FileWriter fw = null;
				fw = new FileWriter("./high_scores.txt");
				BufferedWriter bw = new BufferedWriter(fw);

				//reverses collection to display from high to low
				Set<Map.Entry<Integer, List<String>>> entrySet = Game.scores.entrySet();
				Iterator<Map.Entry<Integer, List<String>>> reverseSet = entrySet.iterator();
				LinkedList<Map.Entry<Integer, List<String>>> reversedList = new LinkedList<Map.Entry<Integer, List<String>>>();

				while (reverseSet.hasNext()) {
					reversedList.push(reverseSet.next());
				}

				for (Map.Entry<Integer, List<String>> p : reversedList) {
					for (String name : p.getValue()) {
						bw.write(name + ":" + p.getKey());
						bw.newLine();
					}
				}

				bw.flush();
				fw.close();
				bw.close();

			} catch (FileNotFoundException e) {
				System.out.println("File '" + "high_scores.txt" + "' not found");
			} catch (IOException e) {
				System.out.println(e.toString());
				System.out.println("Error in opening file '" + "high_scores.txt" + "'");
			} catch (NullPointerException e) {
				System.out.println("Score prompt was canceled.");
			}
		}
	}
}




