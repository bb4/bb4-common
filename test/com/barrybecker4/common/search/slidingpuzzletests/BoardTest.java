package com.barrybecker4.common.search.slidingpuzzletests;

import com.barrybecker4.common.search.slidingpuzzle.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Barry on 7/11/2015.
 */
public class BoardTest {

    private Board board;

    private int[][] SOLVED_3 = new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    private int[][] ALMOST_SOLVED_3 = new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 0, 8}
    };

    private int[][] SPACE_TOP_LEFT = new int[][] {
            {0, 2, 3},
            {4, 5, 6},
            {7, 1, 8}
    };

    private int[][] SPACE_TOP_MIDDLE = new int[][] {
            {2, 0, 3},
            {4, 5, 6},
            {7, 1, 8}
    };

    private int[][] SPACE_BOTTOM_RIGHT = new int[][] {
            {1, 5, 3},
            {4, 2, 6},
            {7, 8 ,0}
    };

    private int[][] SPACE_IN_MIDDLE = new int[][] {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
    };

    private int[][] RANDOM_BOARD = new int[][] {
            {3, 2, 6},
            {8, 0, 1},
            {7, 5, 4}
    };


    @Test
    public void testDimension() {
        board = new Board(ALMOST_SOLVED_3);
        assertEquals("Unexpected dimension (size)", 3, board.dimension());
    }

    @Test
    public void testEquality() {
        board = new Board(ALMOST_SOLVED_3);
        Board board2 = new Board(ALMOST_SOLVED_3);
        assertEquals("Boards unexpectedly not equal", board2, board);
    }

    @Test
    public void testInequality() {
        board = new Board(ALMOST_SOLVED_3);
        Board board2 = new Board(SPACE_TOP_LEFT);
        assertNotEquals("Boards unexpectedly equal", board2, board);
    }

    @Test
    public void testTwinSpaceOnFirstRow() {
        board = new Board(SPACE_TOP_MIDDLE);
        Board twin = board.twin();
        assertEquals("Unexpected twin", "3\n" +
                " 2  0  3 \n" +
                " 5  4  6 \n" +
                " 7  1  8 \n", twin.toString());
    }

    @Test
    public void testTwinSpaceOnSecondRow() {
        board = new Board(RANDOM_BOARD);
        Board twin = board.twin();
        assertEquals("Unexpected twin", "3\n" +
                " 2  3  6 \n" +
                " 8  0  1 \n" +
                " 7  5  4 \n", twin.toString());
    }

    @Test
    public void testBoardIsNotSolved() {
        board = new Board(ALMOST_SOLVED_3);
        assertEquals("Unexpected hamming distance", 1, board.hamming());
        assertFalse("Unexpectedly solved", board.isGoal());
    }

    @Test
    public void testBoardIsSolved() {
        board = new Board(SOLVED_3);
        assertEquals("Unexpected hamming distance", 0, board.hamming());
        assertTrue("Unexpectedly not solved", board.isGoal());
    }

    @Test
     public void testOneStepDistance() {
        board = new Board(ALMOST_SOLVED_3);
        assertEquals("Unexpected hamming distance", 1, board.hamming());
        assertEquals("Unexpected manhattan distance", 1, board.manhattan());
    }

    @Test
    public void testFindNeighbors() {
        board = new Board(SPACE_TOP_LEFT);
        assertEquals("Unexpected num neighbors for top left", 2, getSize(board.neighbors()));

        board = new Board(SPACE_TOP_MIDDLE);
        assertEquals("Unexpected num neighbors for top left", 3, getSize(board.neighbors()));

        board = new Board(SPACE_BOTTOM_RIGHT);
        assertEquals("Unexpected num neighbors for top left", 2, getSize(board.neighbors()));

        board = new Board(SPACE_IN_MIDDLE);
        assertEquals("Unexpected num neighbors for top left", 4, getSize(board.neighbors()));
    }


    @Test
    public void testFindTopLeftNeighbors() {
        board = new Board(SPACE_TOP_LEFT);
        assertEquals("Unexpected neighbors for top left", "[3\n" +
                " 4  2  3 \n" +
                " 0  5  6 \n" +
                " 7  1  8 \n" +
                ", 3\n" +
                " 2  0  3 \n" +
                " 4  5  6 \n" +
                " 7  1  8 \n" +
                "]", board.neighbors().toString());

    }

    private int getSize(Iterable<Board> iterable) {
        int ct  = 0;
        for (Board b : iterable) {
            ct++;
        }
        return ct;
    }

}
