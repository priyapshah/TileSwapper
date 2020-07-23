import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.*;

import javax.swing.*;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

//Main Java Game
public class Game implements Runnable {
	private JFrame frame;
	private GameBoard board;
	static JLabel scoreDisplay = new JLabel(""); 
	static JLabel status = null;
	static String filename = "high_scores.txt";
	private String highScores = ""; 
	static TreeMap<Integer, List<String>> scores = new TreeMap<Integer, List<String>>(); //makes link for each player
	static boolean tileSelected = false;
	static Point firstTile = null;
	private String instructions = "<html>Start a new game of Tile Swap! Earn points "
			+ "by lining up similar matching tiles of the same color. Players "
			+ "are allowed to swap two adjacent tiles in any non-diagonal direction "
			+ " by clicking on them in order to create a match of three or more. "
			+ "Once a match has been made, the tiles above will drop down to fill in "
			+ "the spaces. Hurry! Time will run out quickly!</html>T";


	public void run() {
		try { //adds scores to TreeMap at start of each run
			String line = null;
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				String[] result = line.split(":");
				LinkedList<String> names = new LinkedList<String>();
				names.add(result[0]);
				scores.put(Integer.parseInt(result[1]), names);
			}
			br.close(); 

		} catch (FileNotFoundException e) {
			System.out.println("File '" + filename + "' not found");
		} catch (IOException e) {
			System.out.println("Error in opening file '" + 
					filename + "'");
		}

		//creates game frame
		frame = new JFrame("TileSwapper");
		frame.setLocation(375, 250);
		frame.setSize(700, 500);

		board = GameBoard.getInstance();
		frame.add(board, BorderLayout.CENTER);

		//title panel
		final JPanel titlePanel = new JPanel();
		frame.add(titlePanel, BorderLayout.NORTH);
		final JLabel title = new JLabel ("PLAY TILE SWAP");
		title.setFont(new Font("Times-New-Roman", Font.BOLD, 32));
		titlePanel.add(title);

		//default panel
		final JPanel defaultPanel = new JPanel();
		frame.add(defaultPanel, BorderLayout.WEST);
		status = new JLabel("Click New Game to Begin.");
		defaultPanel.setVisible(true);
		defaultPanel.setPreferredSize(new Dimension(250, 250));

		defaultPanel.setLayout(new GridLayout(0, 1));
		defaultPanel.setBorder(BorderFactory.createTitledBorder("Controls"));

		//new game
		final JButton newGameButton = new JButton("New Game");
		newGameButton.setPreferredSize(new Dimension(100, 10));
		defaultPanel.add(newGameButton);

		//instructions
		final JPanel instructionsPanel = new JPanel();
		instructionsPanel.setLayout(new GridLayout(0, 1));
		instructionsPanel.setPreferredSize(new Dimension(250, 600));


		final JButton instructionsButton = new JButton("Instructions");
		defaultPanel.add(instructionsButton);

		final JLabel instructionsLabel = new JLabel(instructions);
		instructionsLabel.setBorder(BorderFactory.createTitledBorder("Instructions"));
		instructionsPanel.add(instructionsLabel);

		//Back
		final JButton goBackButton = new JButton("Go Back To Home");
		instructionsPanel.add(goBackButton);


		//high scores
		final JPanel highScoresPanel = new JPanel();
		highScoresPanel.setLayout(new GridLayout(0, 1));
		highScoresPanel.setPreferredSize(new Dimension(250, 600));


		final JLabel highScoresLabel = new JLabel(highScores);
		highScoresLabel.setBorder(BorderFactory.createTitledBorder("High Scores"));

		final JButton highScoresButton = new JButton("View High Scores");
		defaultPanel.add(highScoresButton);

		final JButton backHighScoresButton = new JButton("Go Back To Home");
		highScoresPanel.add(backHighScoresButton);
		highScoresPanel.add(highScoresLabel);

		defaultPanel.add(status);
		defaultPanel.add(scoreDisplay);

		Mouse mouseListener = new Mouse();
		board.addMouseListener(mouseListener);


		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.newGameBoard();
				status.setText("Make a swap!"); 
				scoreDisplay.setText("Score: "+ board.getScore());
				tileSelected = false;
				firstTile = null;
			}
		});


		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultPanel.setVisible(true);
				instructionsPanel.setVisible(false);
				frame.add(defaultPanel, BorderLayout.WEST);
			}
		});


		backHighScoresButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultPanel.setVisible(true);
				highScoresPanel.setVisible(false);
				frame.add(defaultPanel, BorderLayout.WEST);
			}
		});


		instructionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultPanel.setVisible(false);
				instructionsPanel.setVisible(true);
				frame.add(instructionsPanel, BorderLayout.WEST);
			}
		});

		highScoresButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String line = null;
				try {
					//Displays high scores
					FileReader fr = new FileReader(filename);
					BufferedReader br = new BufferedReader(fr);
					highScores = "";
					int playerNum = 1;
					while ((line = br.readLine()) != null) {
						if (playerNum <= 5) { //records top five scores
							if (playerNum == 1) {
								highScores += "<html>" + playerNum + ") " + line;
							} else {
								highScores += "<html> <br> " + playerNum + ") " + line;
							}
							playerNum++;
						}
					}
					br.close(); 

				} catch (FileNotFoundException e1) {
					System.out.println("File '" + filename + "' not found");
				} catch (IOException e2) {
					System.out.println("Error in opening file '" + 
							filename + "'");
				}
				highScoresLabel.setText(highScores);
				defaultPanel.setVisible(false);
				highScoresPanel.setVisible(true);
				frame.add(highScoresPanel, BorderLayout.WEST);
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		board.newGameBoard();
		board.clearBoard();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
