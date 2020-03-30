import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.*;


public class TileTest {
	@Test
	public void testNewBoardIsFull() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		assertFalse("Board is full", b.emptySpaces());
	}  

	public void testFillBoard() {
		GameBoard b = GameBoard.getInstance();
		assertTrue("initial board is empty", b.isEmpty());
		b.fillBoard();
		assertTrue("new game board is full", b.isFull());
	}

	@Test
	public void testClearBoard() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		assertTrue("new board is full", b.isFull());
		b.clearBoard();
		assertTrue("cleared board is empty", b.isEmpty()); 
	}

	@Test
	public void testLeftRightChain() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile one = new Tile(Color.BLUE);
		Tile two = new Tile(Color.BLUE);
		Tile three = new Tile(Color.BLUE);
		b.setTile(1, 1, one);
		b.setTile(2, 1, two);
		b.setTile(3, 1, three);
		assertTrue("horizontal chain exists one", b.checkGroup(1, 1));
		assertTrue("horizontal chain exists two", b.checkGroup(2, 1));
		assertTrue("horizontal chain exists three", b.checkGroup(3, 1));
	} 

	@Test
	public void testUpDownChain() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile one = new Tile(Color.BLUE);
		Tile two = new Tile(Color.BLUE);
		Tile three = new Tile(Color.BLUE);
		b.setTile(1, 1, one);
		b.setTile(1, 2, two);
		b.setTile(1, 3, three);
		assertTrue("vertical chain exists one", b.checkGroup(1, 1));
		assertTrue("vertical chain exists two", b.checkGroup(1, 2));
		assertTrue("vertical chain exists three", b.checkGroup(1, 3));
	}

	@Test
	public void testSwitchTiles() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile one = new Tile(Color.BLUE);
		Tile two = new Tile(Color.RED);
		b.setTile(1, 1, one);
		b.setTile(1, 2, two);
		b.switchTiles(1, 1, 1, 2);
		assertEquals("Tiles switched properly 1", b.getTile(1, 1), two);
		assertEquals("Tiles switched properly 1", b.getTile(1, 2), one);
	}

	@Test
	public void testSwitchHorizontalIsValid() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		assertTrue("switch horizontal", 
				b.validMove(1, 2, 2, 2));
	}

	@Test
	public void testSwitchVerticalIsValid() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		assertTrue("switch vertical", 
				b.validMove(1, 1, 1, 2));
	}

	@Test
	public void testSwitchFarInvalid() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		assertFalse("switch not adjacent", 
				b.validMove(1, 1, 4, 3));
	}

	@Test
	public void testSwitchDiagonalInvalid() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		assertFalse("switch diagonal", 
				b.validMove(1, 1, 2, 2));
	}

	@Test
	public void testSwitchOutOfBoundsInvalid() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		assertFalse("switch out of bounds", 
				b.validMove(1, 1, 15, 15));
	}

	@Test
	public void testRemoveVerticalChainMin() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile one = new Tile(Color.BLUE);
		Tile two = new Tile(Color.BLUE);
		Tile three = new Tile(Color.BLUE);
		b.setTile(1, 1, one);
		b.setTile(1, 2, two);
		b.setTile(1, 3, three);
		b.checkGroup(1,1);
		b.removeChains();
		assertEquals("removed Tile 1 is null", b.getTile(1, 1), null);
		assertEquals("removed Tile 2 is null", b.getTile(1, 2), null);
		assertEquals("removed Tile 3 is null", b.getTile(1, 3), null);
	}

	@Test
	public void testRemoveVerticalChainMore() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile one = new Tile(Color.BLUE);
		Tile two = new Tile(Color.BLUE);
		Tile three = new Tile(Color.BLUE);
		Tile four = new Tile(Color.BLUE);
		b.setTile(1, 1, one);
		b.setTile(1, 2, two);
		b.setTile(1, 3, three);
		b.setTile(1, 4, four);
		b.checkGroup(1,1);
		b.removeChains();
		assertEquals("removed Tile 1 is null", b.getTile(1, 1), null);
		assertEquals("removed Tile 2 is null", b.getTile(1, 2), null);
		assertEquals("removed Tile 3 is null", b.getTile(1, 3), null);
		assertEquals("removed Tile 4 is null", b.getTile(1, 4), null);
	}

	@Test
	public void testRemoveHorizontalChainMin() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile one = new Tile(Color.BLUE);
		Tile two = new Tile(Color.BLUE);
		Tile three = new Tile(Color.BLUE);
		b.setTile(1, 1, one);
		b.setTile(2, 1, two);
		b.setTile(3, 1, three);
		b.checkGroup(1,1);
		b.removeChains();
		assertEquals("removed Tile 1 is null", b.getTile(1, 1), null);
		assertEquals("removed Tile 2 is null", b.getTile(2, 1), null);
		assertEquals("removed Tile 3 is null", b.getTile(3, 1), null);
	}

	@Test
	public void testRemoveHorizontalChainMore() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile one = new Tile(Color.BLUE);
		Tile two = new Tile(Color.BLUE);
		Tile three = new Tile(Color.BLUE);
		Tile four = new Tile(Color.BLUE);
		b.setTile(1, 1, one);
		b.setTile(2, 1, two);
		b.setTile(3, 1, three);
		b.setTile(4, 1, four);
		b.checkGroup(2,1); //checks left and right of tile
		b.removeChains();
		assertEquals("removed Tile 1 is null", b.getTile(1, 1), null);
		assertEquals("removed Tile 2 is null", b.getTile(2, 1), null);
		assertEquals("removed Tile 3 is null", b.getTile(3, 1), null);
		assertEquals("removed Tile 4 is null", b.getTile(4, 1), null);
	}

	@Test
	public void testRemoveMultipleDirectionsUp() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile one = new Tile(Color.BLUE);
		Tile two = new Tile(Color.BLUE);
		Tile three = new Tile(Color.BLUE);
		Tile four = new Tile(Color.BLUE);
		b.setTile(1, 1, one); //horizontal
		b.setTile(2, 1, two); //horizontal
		b.setTile(3, 1, three); //horizontal
		b.setTile(1, 2, four); //vertical
		b.checkGroup(1,1);
		b.removeChains();
		assertEquals("removed Tile 1 is null", b.getTile(1, 1), null);
		assertEquals("removed Tile 2 is null", b.getTile(2, 1), null);
		assertEquals("removed Tile 3 is null", b.getTile(3, 1), null);
		assertEquals("removed Tile 4 is null", b.getTile(1, 2), null);
	}

	@Test
	public void testRemoveMultipleDirectionsDown() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile one = new Tile(Color.BLUE);
		Tile two = new Tile(Color.BLUE);
		Tile three = new Tile(Color.BLUE);
		Tile four = new Tile(Color.BLUE);
		b.setTile(1, 2, one); //horizontal
		b.setTile(2, 2, two); //horizontal
		b.setTile(3, 2, three); //horizontal
		b.setTile(1, 1, four); //vertical
		b.checkGroup(1,2);
		b.removeChains();
		assertEquals("removed Tile 1 is null", b.getTile(1, 2), null);
		assertEquals("removed Tile 2 is null", b.getTile(2, 2), null);
		assertEquals("removed Tile 3 is null", b.getTile(3, 2), null);
		assertEquals("removed Tile 4 is null", b.getTile(1, 1), null);
	}

	@Test
	public void testMoveDown() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile tile = new Tile(Color.BLUE);
		b.setTile(1, 0, tile);
		b.setTile(1, 1, null);
		b.moveDown();
		assertEquals("Point is empty", b.getTile(1, 0), null);
		assertEquals("Tile moved down", b.getTile(1, 1), tile);
	}

	@Test
	public void testEmptySpaces() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		Tile j1 = new Tile(Color.CYAN);
		Tile j2 = new Tile(Color.RED);
		Tile j3 = new Tile(Color.BLUE);
		b.setTile(1, 1, j1);
		b.setTile(2, 1, j2);
		b.setTile(3, 1, j3);
		b.setTile(1, 2, null);
		b.setTile(2, 2, null);
		b.setTile(3, 2, null);
		assertTrue("empty spaces exist", b.emptySpaces());
	}

	@Test
	public void testNoEmptySpacesOnNewBoard() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		assertFalse("no empty spaces", b.emptySpaces());
	}

	@Test
	public void testNoChainsRemoved() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		b.removeChains();
		assertTrue("no chains to remove", b.isFull());
	}

	@Test
	public void testResetScoreForNewGame() {
		GameBoard b = GameBoard.getInstance();
		b.newGameBoard();
		b.resetScore();
		Tile one = new Tile(Color.BLUE);
		Tile two = new Tile(Color.BLUE);
		Tile three = new Tile(Color.BLUE);
		b.setTile(1, 1, one);
		b.setTile(2, 1, two);
		b.setTile(3, 1, three);
		b.switchTiles(1, 1, 2, 1);
		b.checkGroup(1,1);
		b.removeChains();

		b.newGameBoard(); 
		assertEquals(0, b.getScore()); 	
	} 

}