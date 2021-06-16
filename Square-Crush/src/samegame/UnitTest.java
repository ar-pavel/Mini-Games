package samegame;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Timeout;

import graphics.ColorDef;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTest {
	
	public static Tile blue, purple, yellow, green;
	public static ArrayList<Tile> colors;

	public static int score = 0;
	public static String result = "";
	public static String currentMethodName = null;
	ArrayList<String> methodsPassed = new ArrayList<String>();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		// sets up the colors to be used in application
		blue = new Tile(Color.decode(ColorDef.BLUE),1);
		purple = new Tile(Color.decode(ColorDef.PURPLE),1);
		yellow = new Tile(Color.decode(ColorDef.YELLOW),1);
		green = new Tile(Color.decode(ColorDef.GREEN),1);
		
		colors = new ArrayList<Tile>();
		colors.add(blue);
		colors.add(purple);
		colors.add(yellow);
		colors.add(green);
	}

	@BeforeEach
	public void setUp() throws Exception {
		currentMethodName = null;
	}

	@Test
	@Order(1)
	@Timeout(1)
	@Graded(description="SimpleSameGame.getBoard", marks=4)
	public void testSimpleGetBoard() {
		SimpleSameGame ssg = new SimpleSameGame(new Random(10),22);
		ArrayList<Tile> returnedBoard = ssg.getBoard();
		assertTrue(returnedBoard.get(0).equals(yellow));
		assertTrue(returnedBoard.get(3).equals(purple));
		assertTrue(returnedBoard.get(6).equals(blue));
		assertTrue(returnedBoard.get(9).equals(purple));
		assertEquals(22,returnedBoard.size());

		// Test Case 1: makes sure returned board is a reference copy
		returnedBoard.set(0,green);
		returnedBoard.set(3,yellow);
		returnedBoard.set(6,purple);
		assertTrue(ssg.board.get(0).equals(yellow));
		assertTrue(ssg.board.get(3).equals(purple));
		assertTrue(ssg.board.get(6).equals(blue));


		// Test Case 2:
		returnedBoard.clear();
		assertEquals(22,ssg.board.size());

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(2)
	@Timeout(1)
	@Graded(description="SimpleSameGame.isValidIndex", marks=5)
	public void testSimpleIsValidIndex() {

		SimpleSameGame ssg = new SimpleSameGame(new Random(10),22);
		// Test Case 1: invalid indices
		assertFalse(ssg.isValidIndex(-1));
		assertFalse(ssg.isValidIndex(34));
		assertFalse(ssg.isValidIndex(22));

		// Test Case 2: valid indices
		assertTrue(ssg.isValidIndex(0));
		assertTrue(ssg.isValidIndex(5));
		assertTrue(ssg.isValidIndex(7));
		assertTrue(ssg.isValidIndex(21));

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(3)
	@Timeout(1)
	@Graded(description="SimpleSameGame.isValidMove", marks=5)
	public void testSimpleIsValidMove() {
		SimpleSameGame ssg = new SimpleSameGame(new Random(10),22);

		// Test Case 1: invalid indices
		assertFalse(ssg.isValidMove(-1));
		assertFalse(ssg.isValidMove(34));
		assertFalse(ssg.isValidMove(22));

		// Test Case 2: valid moves
		assertTrue(ssg.isValidMove(1));
		assertTrue(ssg.isValidMove(10));
		assertTrue(ssg.isValidMove(16));
		assertTrue(ssg.isValidMove(20));

		// Test Case 3: invalid moves
		assertFalse(ssg.isValidMove(0));
		assertFalse(ssg.isValidMove(4));
		assertFalse(ssg.isValidMove(5));
		assertFalse(ssg.isValidMove(6));
		assertFalse(ssg.isValidMove(18));
		assertFalse(ssg.isValidMove(21));

		ArrayList<Tile> row = new ArrayList<Tile>(Arrays.asList(blue  ,null  ,null  ,blue  ,green ,yellow));
		ssg.board = row;
		for(int i=0; i < row.size(); i++) {
			assertFalse(ssg.isValidMove(i));
		}
		row = new ArrayList<Tile>(Arrays.asList(blue  ,null  ,null  ,blue  ,green ,yellow));
		ssg.board = row;
		for(int i=0; i < row.size(); i++) {
			assertFalse(ssg.isValidMove(i));
		}

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(4)
	@Timeout(1)
	@Graded(description="SimpleSameGame.noMoreValidMoves", marks=5)
	public void testSimpleNoMoreValidMoves() {

		SimpleSameGame ssg = new SimpleSameGame(new Random(10),22);

		// Test Case 1: empty board
		ArrayList<Tile> board = new ArrayList<>();
		ssg.board = board;
		assertTrue(ssg.noMoreValidMoves());

		// Test Case 2: standard board with possible moves
		ssg = new SimpleSameGame(new Random(10),22);
		assertFalse(ssg.noMoreValidMoves());

		// Test Case 3: standard board with no moves left
		board = new ArrayList<Tile>(Arrays.asList(purple,blue,yellow,purple,green,yellow));
		ssg.board = board;
		assertTrue(ssg.noMoreValidMoves());

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(5)
	@Timeout(1)
	@Graded(description="SimpleSameGame.removeTile", marks=10)
	public void testSimpleRemoveTile() {

		SimpleSameGame ssg = new SimpleSameGame(new Random(10),22);
		ssg.removeTile(0);
		assertTrue(ssg.board.get(0).equals(yellow));
		assertTrue(ssg.board.get(21).equals(green));
		assertEquals(22,ssg.getBoard().size());

		ssg.removeTile(1);
		assertTrue(ssg.board.get(0).equals(yellow));
		assertTrue(ssg.board.get(1).equals(blue));
		assertTrue(ssg.board.get(18).equals(green));
		assertEquals(19,ssg.getBoard().size());

		ssg.removeTile(14);
		assertTrue(ssg.board.get(0).equals(yellow));
		assertTrue(ssg.board.get(1).equals(blue));
		assertTrue(ssg.board.get(6).equals(purple));
		assertTrue(ssg.board.get(7).equals(purple));
		assertTrue(ssg.board.get(15).equals(green));
		assertEquals(16,ssg.getBoard().size());

		ssg = new SimpleSameGame(new Random(20),10);
		ssg.removeTile(1);
		assertEquals(8,ssg.getBoard().size());
		assertTrue(ssg.board.get(0).equals(blue));
		assertTrue(ssg.board.get(1).equals(green));

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(6)
	@Timeout(1)
	@Graded(description="SameGame constructor", marks=5)
	public void testSameGameConstructor() {

		// Test Case 1
		SameGame sg = new SameGame(new Random(10),6,6);
		assertTrue(sg.board.get(0).get(0).equals(yellow));
		assertTrue(sg.board.get(0).get(1).equals(purple));
		assertTrue(sg.board.get(0).get(2).equals(purple));
		assertTrue(sg.board.get(0).get(3).equals(purple));
		assertTrue(sg.board.get(0).get(4).equals(blue));
		assertTrue(sg.board.get(0).get(5).equals(yellow));

		// Test Case 2
		sg = new SameGame(new Random(20),17,22);
		assertTrue(sg.board.get(0).get(0).equals(yellow));
		assertTrue(sg.board.get(0).get(1).equals(yellow));
		assertTrue(sg.board.get(0).get(2).equals(blue));
		assertTrue(sg.board.get(0).get(3).equals(green));
		assertTrue(sg.board.get(0).get(4).equals(green));
		assertTrue(sg.board.get(0).get(5).equals(yellow));

		// Test Case 3
		sg = new SameGame(new Random(30),1000,1000);
		assertTrue(sg.board.get(100).get(100).equals(green));
		assertTrue(sg.board.get(200).get(300).equals(green));
		assertTrue(sg.board.get(300).get(500).equals(blue));
		assertTrue(sg.board.get(400).get(700).equals(yellow));
		assertTrue(sg.board.get(500).get(900).equals(green));
		assertTrue(sg.board.get(600).get(100).equals(purple));

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(7)
	@Timeout(1)
	@Graded(description="SameGame.calculateScore", marks=5)
	public void testCalculateScore() {

		ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();
		
		// Test Case 1:
		SameGame sg = new SameGame(new Random(10),6,6); 
		assertEquals(0,sg.calculateScore()); //originally 36 tiles on the board
		
		board.add(0,new ArrayList<Tile>(Arrays.asList(purple))); //board with a single tile
		sg.board = board; //same game board updated to single tile => 35 tiles removed
		
		assertEquals(35,sg.calculateScore());
		board.clear();
		
		// Test Case 2:
		sg = new SameGame(new Random(10),6,6);
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(yellow,purple,null  ,null)));
		sg.board = board;

		assertEquals(34,sg.calculateScore());
		board.clear();
		
		// Test Case 3:
		sg = new SameGame(new Random(10),6,6);
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,purple,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,green ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(green ,null  ,blue  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(yellow,blue  ,yellow,purple)));
		sg.board = board;

		assertEquals(28,sg.calculateScore());
		board.clear();

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(8)
	@Timeout(1)
	@Graded(description="SameGame.getBoard", marks=5)
	public void testGetBoard() {
		SameGame sg = new SameGame(new Random(10),6,6);
		
		ArrayList<ArrayList<Tile>> returnedBoard = sg.getBoard();

		assertTrue(returnedBoard.get(0).get(0).equals(yellow));
		assertTrue(returnedBoard.get(0).get(1).equals(purple));
		assertTrue(returnedBoard.get(0).get(2).equals(purple));
		assertTrue(returnedBoard.get(0).get(3).equals(purple));
		assertEquals(6,returnedBoard.size());
		assertEquals(6,returnedBoard.get(0).size());
		
		returnedBoard.get(0).set(0,green);
		returnedBoard.get(0).set(1,green);
		returnedBoard.get(0).set(2,green);
		assertTrue(sg.board.get(0).get(0).equals(yellow));
		assertTrue(sg.board.get(0).get(1).equals(purple));
		assertTrue(sg.board.get(0).get(2).equals(purple));
		
		returnedBoard.clear();
		assertEquals(6, sg.board.get(0).size());

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(9)
	@Timeout(1)
	@Graded(description="SameGame.isValidIndex", marks=5)
	public void testIsValidIndex() {
		SameGame sg = new SameGame(new Random(10),6,3);
		
		// Test Case 1: invalid indices
		assertFalse(sg.isValidIndex(7, 5));
		assertFalse(sg.isValidIndex(-1, 4));
		assertFalse(sg.isValidIndex(0, -5));
		assertFalse(sg.isValidIndex(0, 3));
		
		// Test Case 2: valid indices
		assertTrue(sg.isValidIndex(5,2));
		assertTrue(sg.isValidIndex(0,1));
		assertTrue(sg.isValidIndex(5,0));
		
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(10)
	@Timeout(1)
	@Graded(description="SameGame.isValidMove", marks=10)
	public void testIsValidMove() {
		SameGame sg = new SameGame(new Random(10),6,6);
		
		// Test Case 1: invalid indices
		assertFalse(sg.isValidMove(6, 6));
		assertFalse(sg.isValidMove(-1, 4));

		// Test Case 2: valid moves
		assertTrue(sg.isValidMove(0, 1));
		assertTrue(sg.isValidMove(0, 2));
		assertTrue(sg.isValidMove(0, 3));
		assertTrue(sg.isValidMove(2, 0));
		assertTrue(sg.isValidMove(2, 1));
		assertTrue(sg.isValidMove(2, 3));
		assertTrue(sg.isValidMove(2, 4));
		assertTrue(sg.isValidMove(2, 5));
		assertTrue(sg.isValidMove(5, 5));
		
		// Test Case 3: invalid moves
		assertFalse(sg.isValidMove(0, 0));
		assertFalse(sg.isValidMove(3, 3));
		assertFalse(sg.isValidMove(4, 3));
		assertFalse(sg.isValidMove(5, 0));
		
		ArrayList<ArrayList<Tile>> board = new ArrayList<>();
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,null  ,null  ,blue  ,green ,yellow)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(yellow,purple,blue  ,yellow,blue  ,purple)));
		sg.board = board;
		assertFalse(sg.isValidMove(0, 0));
		assertFalse(sg.isValidMove(0, 1));
		assertFalse(sg.isValidMove(0, 2));
		assertFalse(sg.isValidMove(0, 3));
		assertFalse(sg.isValidMove(0, 4));
		assertFalse(sg.isValidMove(0, 5));
		
		assertFalse(sg.isValidMove(1, 0));
		assertFalse(sg.isValidMove(1, 1));
		assertFalse(sg.isValidMove(1, 2));
		assertFalse(sg.isValidMove(1, 3));
		assertFalse(sg.isValidMove(1, 4));
		assertFalse(sg.isValidMove(1, 5));
		
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(11)
	@Timeout(1)
	@Graded(description="SameGame.noMoreValidMoves", marks=5)
	public void testNoMoreValidMoves() {
		SameGame sg = new SameGame(new Random(10),1,1);

		// Test Case 1: empty board
		ArrayList<ArrayList<Tile>> board = new ArrayList<>();
		sg.board = board;
		assertTrue(sg.noMoreValidMoves());
		
		board.clear();

		// Test Case 2: standard board with possible moves
		sg = new SameGame(new Random(10),6,6);
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null  ,null  ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(green ,null  ,purple,null  ,null  ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,yellow,green ,null  ,green ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,yellow,blue  ,green ,green ,yellow)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(yellow,blue  ,yellow,blue  ,blue  ,yellow)));


		sg.board = board;
		assertFalse(sg.noMoreValidMoves());
		board.clear();
		
		// Test Case 3: standard board with no moves left
		sg = new SameGame(new Random(10),2,6);
		board.clear();
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,null  ,null  ,blue  ,green ,yellow)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(yellow,purple,blue  ,yellow,blue  ,purple)));

		sg.board = board;
		assertTrue(sg.noMoreValidMoves());
		board.clear();
		
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(12)
	@Timeout(1)
	@Graded(description="SameGame.trickleDown", marks=5)
	public void testTrickleDown() {
		// the original arrays, which we'll use to test the modified ArrayLists
		Tile[] origTop1 = {purple,purple,blue,blue,yellow,yellow};
		Tile[] origTop2 = {blue, null, blue, null, green, blue};
		Tile[] origBottom = {purple,null,null,purple,null,green}; 
		
		// convert the above arrays into ArrayLists for the method call
		ArrayList<Tile> top1 = new ArrayList<Tile>(Arrays.asList(origTop1));
		ArrayList<Tile> top2 = new ArrayList<Tile>(Arrays.asList(origTop2));
		ArrayList<Tile> bottom = new ArrayList<Tile>(Arrays.asList(origBottom));
		
		// the expected results:
		ArrayList<Tile> top1End = new ArrayList<Tile>(Arrays.asList(purple,null,null,blue,null,yellow));
		ArrayList<Tile> bottom1End = new ArrayList<Tile>(Arrays.asList(purple,purple,blue,purple,yellow,green));
		ArrayList<Tile> top2End = new ArrayList<Tile>(Arrays.asList(blue,null,null,null,null,blue));
		ArrayList<Tile> bottom2End = new ArrayList<Tile>(Arrays.asList(purple,null,blue,purple,green,green));

		// we're not actually going to use the board in this test, just need it to call
		// the trickleDown method;
		SameGame sg = new SameGame(new Random(10),1,1);

		// these three calls should not modify the ArrayLists
		sg.trickleDown(top1,bottom);
		sg.trickleDown(null,bottom);
		sg.trickleDown(new ArrayList<Tile>(), top1);
		for(int i = 0; i < top1.size(); i++) {
			System.out.println(origTop1[i] + " , " + top1.get(i));
			assertEquals(origTop1[i],top1.get(i));
			assertEquals(origBottom[i],bottom.get(i));
		}
		
		// Test Case 1: top1 and bottom
		sg.trickleDown(bottom,top1);
		for(int i = 0; i < top1.size(); i++) {
			assertEquals(top1End.get(i),top1.get(i));
			assertEquals(bottom1End.get(i),bottom.get(i));
		}
		
		// Test Case 2: top2 and bottom
		bottom = new ArrayList<Tile>(Arrays.asList(origBottom));
		sg.trickleDown(bottom,top2);
		for(int i = 0; i < top2.size(); i++) {
			assertEquals(top2End.get(i),top2.get(i));
			assertEquals(bottom2End.get(i),bottom.get(i));
		}
		
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(13)
	@Timeout(1)
	@Graded(description="SameGame.rearrangeBoard", marks=5)
	public void testRearrangeBoard() {
		// Test generated using SameGameBoard(10,6,6);

		// Test Case 1
		ArrayList<ArrayList<Tile>> board = new ArrayList<>();
		board.add(0,new ArrayList<Tile>(Arrays.asList(green ,yellow,purple,green ,green ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,yellow,green ,blue  ,green ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(green ,blue  ,blue  ,green ,null  ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(green ,green ,yellow,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,null  ,green ,null  ,null  ,yellow)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(yellow,null  ,null  ,null  ,blue  ,yellow)));
		
		ArrayList<ArrayList<Tile>> expected = new ArrayList<>();
		expected.add(0,new ArrayList<Tile>(Arrays.asList(green ,null  ,null  ,null  ,null  ,null)));
		expected.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,null  ,purple,null  ,null  ,blue)));
		expected.add(0,new ArrayList<Tile>(Arrays.asList(green ,yellow,green ,null  ,null ,blue)));
		expected.add(0,new ArrayList<Tile>(Arrays.asList(green ,yellow,blue  ,green ,green ,blue)));
		expected.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,blue  ,yellow,blue  ,green ,yellow)));
		expected.add(0,new ArrayList<Tile>(Arrays.asList(yellow,green ,green ,green ,blue  ,yellow)));
		
		SameGame sg = new SameGame(new Random(10),6,6);
		sg.board = board;
		sg.rearrangeBoard();
		for(int i = 0; i < board.size(); i++) {
			for(int j = 0; j < board.size(); j++) {
				assertEquals(expected.get(i).get(j),board.get(i).get(j));
			}
		}
		board.clear();
		expected.clear();

		// Test Case 2
		board.add(0,new ArrayList<Tile>(Arrays.asList(green ,null  ,null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,null  ,purple,null  ,null  ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,yellow,green ,null  ,null ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,yellow,blue  ,green ,green ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,blue  ,yellow,blue  ,green ,yellow)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(yellow,null  ,null  ,null  ,blue  ,yellow)));

		expected.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null  ,null  ,null)));
		expected.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null  ,null  ,blue)));
		expected.add(0,new ArrayList<Tile>(Arrays.asList(green ,null  ,purple,null  ,null  ,blue)));
		expected.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,yellow,green ,null  ,green ,blue)));
		expected.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,yellow,blue  ,green ,green ,yellow)));
		expected.add(0,new ArrayList<Tile>(Arrays.asList(yellow,blue  ,yellow,blue  ,blue  ,yellow)));
		
		sg.board = board;
		sg.rearrangeBoard();
		for(int i = 0; i < board.size(); i++) {
			for(int j = 0; j < board.size(); j++) {
				assertEquals(expected.get(i).get(j),board.get(i).get(j));
			}
		}
		
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(14)
	@Timeout(1)
	@Graded(description="SameGame.deleteEmptyColumns", marks=10)
	public void testDeleteEmptyColumns() {
		SameGame sg = new SameGame(new Random(10),6,6);
		
		// Test case 1: remove a single column
		ArrayList<ArrayList<Tile>> board = new ArrayList<>();
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null  ,null  ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(green ,null  ,purple,null  ,null  ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,yellow,green ,null  ,green ,blue)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,yellow,blue  ,null  ,green ,yellow)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(yellow,blue  ,yellow,null  ,blue  ,yellow)));
		
		sg.board = board;
		sg.rearrangeBoard();
		assertEquals(5,sg.board.get(0).size());
		assertTrue(sg.board.get(0).get(2).equals(yellow));
		assertTrue(sg.board.get(0).get(3).equals(blue));
		board.clear();
		
		// Test case 2: remove multiple columns
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null  ,null  ,null  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(green ,null  ,purple,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,null  ,green ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(blue  ,null  ,blue  ,null  ,null)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(yellow,null  ,yellow,null  ,null)));
		sg.rearrangeBoard();
		assertEquals(2,sg.board.get(0).size());
		assertTrue(sg.board.get(0).get(0).equals(yellow));
		assertTrue(sg.board.get(0).get(1).equals(yellow));
		board.clear();
		
		// Test case 3: remove multiple columns
		board.add(0,new ArrayList<Tile>(Arrays.asList(null ,purple)));
		board.add(0,new ArrayList<Tile>(Arrays.asList(null ,null  )));
		sg.rearrangeBoard();
		assertEquals(1,sg.board.get(0).size());
		assertTrue(sg.board.get(0).get(0).equals(purple));

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(15)
	@Timeout(1)
	@Graded(description="SameGame.removeTile (small)", marks=11)
	public void testRemoveTile() {
		// Test Case 1
		SameGame sg = new SameGame(new Random(10),6,6);
		sg.removeTile(5,0);
		sg.removeTile(2,0);
		sg.removeTile(0,2);
		sg.removeTile(0,2);
		sg.removeTile(0,2);
		
		// Test Case 1: check the bottom row:
		assertTrue(sg.board.get(0).get(0).equals(yellow));
		assertTrue(sg.board.get(0).get(1).equals(blue));
		assertTrue(sg.board.get(0).get(2).equals(yellow));
		assertTrue(sg.board.get(0).get(3).equals(blue));
		assertTrue(sg.board.get(0).get(4).equals(blue));
		assertTrue(sg.board.get(0).get(5).equals(yellow));
		
		assertNull(sg.board.get(4).get(0));
		assertNull(sg.board.get(5).get(5));

		// Test Case 2: remove more tiles, and check board size
		sg.removeTile(0, 3);
		sg.removeTile(0, 3);
		assertEquals(4,sg.board.get(0).size());
		
		// Test Case 3: remove more tiles, and check board size and tile colours
		sg.removeTile(0, 3);
		sg.removeTile(0, 3);
		assertEquals(3,sg.board.get(0).size());
		
		assertNull(sg.board.get(3).get(1));
		assertNull(sg.board.get(4).get(0));
		assertTrue(sg.board.get(0).get(0).equals(yellow));
		assertTrue(sg.board.get(0).get(1).equals(yellow));
		assertTrue(sg.board.get(3).get(0).equals(green));
		
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	@Test
	@Order(16)
	@Graded(description="SameGame.removeTile (large)", marks=5)
	public void testRemoveTileLarge() {
		SameGame sg = new SameGame(new Random(30), 1000, 1000); 
		for(int i = 0; i < 1000; i++) {
			sg.board.get(i).set(3, purple);
			sg.board.get(i).set(30, green);
		}

		assertTimeoutPreemptively(Duration.ofMillis(10000), () -> {sg.removeTile(4, 3);});

		assertTrue(sg.board.get(800).get(0).equals(green));
		assertTrue(sg.board.get(800).get(1).equals(purple));
		assertNull(sg.board.get(800).get(2));
		assertNull(sg.board.get(800).get(3));
		assertTrue(sg.board.get(800).get(4).equals(green));
		assertEquals(999,sg.board.get(0).size());

		assertTimeoutPreemptively(Duration.ofMillis(10000), () -> {sg.removeTile(9, 29);});

		assertTrue(sg.board.get(800).get(27).equals(yellow));
		assertNull(sg.board.get(800).get(28));
		assertNull(sg.board.get(800).get(29));
		assertTrue(sg.board.get(800).get(30).equals(yellow));
		assertTrue(sg.board.get(800).get(31).equals(yellow));
		assertEquals(998,sg.board.get(0).size());

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}



	@AfterEach
	public void logSuccess() throws NoSuchMethodException, SecurityException {
		if(currentMethodName != null && !methodsPassed.contains(currentMethodName)) {
			methodsPassed.add(currentMethodName);
			Method method = getClass().getMethod(currentMethodName);
			Graded graded = method.getAnnotation(Graded.class);
			score+=graded.marks();
			result+=graded.description()+" passed. Marks awarded: "+graded.marks()+"\n";
		}
	}
	
	@AfterAll
	public static void wrapUp() throws IOException {
		if(result.length() != 0) {
			result = result.substring(0, result.length()-1); //remove the last "\n"
		}
		System.out.println(result);
		System.out.println("Indicative mark: "+ score);
		System.out.println();
	}
}
