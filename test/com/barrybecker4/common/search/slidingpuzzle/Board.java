package com.barrybecker4.common.search.slidingpuzzle;

import com.barrybecker4.common.geometry.ByteLocation;
import com.barrybecker4.common.geometry.Location;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Immutable.
 * For a long time I could not solve puzzles beyond 31. Eventually I got to about 44, but no higher.
 *
 * Here is a list of performance enhancements that I made and how important they are:
 * - calculated the changed the manhattan distance in the constructor, and cache it in a private property.
 * - use lazy calculation of the hamming distance. IOW, calculate the first time requested and cache it.
 * - made blocks use byte instead of int. short is probably best.
 * - use a 1D array instead of a 2D array for the blocks.
 * - Calculate the stringForm (from toString) in the constructor and cache it.
 *    This should not be needed, and is bad because it adds memory. But I needed it in order to use the stringForm
 *    as a hashcode for use in a HashMap (that also should not be needed, but I could not get reasonable times without)
 * - Use hamming distance as a sort of hashcode in the equals method to speed it up, since the normal way of computing
 *   equals is slow. IOW first check the hamming value, if that does not match, it cannot be equal. If it does match
 *   resort to slower comparison.
 * - When creating neighbors I use the fact that there is going to be an incremental change to the manhattan distance
 *   and do not recompute it from scrach. Hint: use a private constructor, that takes the manhattan distance as a param.
 * - Sorted the neighbors so that the most promising is delivered first. Gave a modest performance boost because
 *   fewer nodes were then added to the queue in the long run.
 * - Used System.arraycopy(src, 0, target, 0,length); to copy the internal blocks array.
 *
 * @author Barry Becker
 */
public class Board {

    private byte[] blocks;
    private byte side;
    private byte hamming;
    private short manhattan;
    private String stringForm;

    /**
     * Construct a board from an N-by-N array of blocks
     * @param blocks 0 - N^2 blocks. blocks[i][j] = block in row i, column j
     */
    public Board(int[][] blocks) {
        this(makeBlocks(blocks));
    }

    private Board(byte[] blocks) {
        this.blocks = blocks;
        byte size = (byte) blocks.length;
        this.side = (byte) Math.sqrt(size);
        this.hamming = -1;
        this.manhattan = calculateManhattan();
        this.stringForm = this.getStringForm();
    }

    /** use this version of the constructor if you already know the manhattan distance */
    private Board(byte[] blocks, byte side, short manhattan) {
        this.blocks = blocks;
        this.side = side;
        this.hamming = -1;
        this.manhattan = manhattan;
        this.stringForm = this.getStringForm();
    }

    /** @return board dimension N */
    public int dimension() {
        return side;
    }

    /** @return number of blocks out of place */
    public int hamming()  {
        if (hamming < 0) {
            hamming = calculateHamming();
        }
        return hamming;
    }

    private byte calculateHamming() {
        byte expected = 0;
        byte hamCount = 0;
        for (byte i=0; i < side; i++) {
            for (byte j=0; j < side; j++) {
                byte value = blocks[i* side + j];
                expected++;
                if (value != 0 && value != expected) {
                    hamCount++;
                }
            }
        }
        return hamCount;
    }

    /** @return sum of Manhattan distances between blocks and goal */
    public int manhattan()  {
        //if (manhattan < 0) {
        //    manhattan = calculateManhattan();
        //}
        return manhattan;
    }

    private short calculateManhattan() {
        short totalDistance = 0;
        for (byte i=0; i < side; i++) {
            for (byte j=0; j < side; j++) {
                int value = blocks[i * side + j];
                if (value != 0) {
                    int expCol = (value - 1) % side;
                    int expRow = (value - 1) / side;
                    int deltaRow = Math.abs(expRow - i);
                    int deltaCol = Math.abs(expCol - j);
                    totalDistance += deltaRow + deltaCol;
                }
            }
        }
        return totalDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;
        return board.hamming() == hamming() && Arrays.equals(blocks, board.blocks);
    }

/*
    @Override

    public int hashCode() {
        return Arrays.deepHashCode(this.blocks);
    }*/

    /** @return true if this board the goal board */
    public boolean isGoal()  {
        return hamming() == 0;
    }

    /** @return a board that is obtained by exchanging two adjacent blocks in the same row */
    public Board twin() {
        byte[] newBlocks = copyBlocks(this.blocks);
        if (newBlocks[0] != 0 && newBlocks[1] != 0) {
            swap(0, 0, 0, 1, newBlocks);
        }
        else {
            swap(1, 0, 1, 1, newBlocks);
        }
        return new Board(newBlocks);
    }

    public List<Transition> getNeighborTransitions() {
        List<Transition> neighbors = new LinkedList<>();
        Location spacePos = getSpacePosition();
        int i = spacePos.getRow();
        int j = spacePos.getCol();
        if (i > 0) {
            neighbors.add(new Transition(spacePos, new ByteLocation(i-1, j)));
        }
        if (i < side - 1) {
            neighbors.add(new Transition(spacePos, new ByteLocation(i+1, j)));
        }
        if (j > 0) {
            neighbors.add(new Transition(spacePos, new ByteLocation(i, j-1)));
        }
        if (j < side - 1) {
            neighbors.add(new Transition(spacePos, new ByteLocation(i, j + 1)));
        }
        return neighbors;
    }

    public Board applyTransition(Transition trans) {
        Location space = trans.getSpacePosition();
        Location tile = trans.getTilePosition();
        return move(space.getRow(), space.getCol(), tile.getRow(), tile.getCol());
    }

    /**
     * @return all neighboring boards. There are at most 4.
     */
    public Iterable<Board> neighbors()  {
        List<Board> neighbors = new LinkedList<>();
        Location spacePos = getSpacePosition();
        int i = spacePos.getRow();
        int j = spacePos.getCol();
        if (i > 0) {
            neighbors.add(move(i, j, i-1, j));
        }
        if (i < side - 1) {
            neighbors.add(move(i, j, i+1, j));
        }
        if (j > 0) {
            neighbors.add(move(i, j, i, j-1));
        }
        if (j < side - 1) {
            neighbors.add(move(i, j, i, j + 1));
        }
        Collections.sort(neighbors, new Comparator<Board> () {
            @Override
            public int compare(Board o1, Board o2) {
                return  o1.manhattan - o2.manhattan;
            }
        });
        return neighbors;
    }

    private Board move(int oldSpaceRow, int oldSpaceCol, int newSpaceRow, int newSpaceCol) {
        byte[] newBlocks = copyBlocks(blocks);
        short movingVal = blocks[newSpaceRow * side + newSpaceCol];
        int goalCol = (movingVal - 1) % side;
        int goalRow = (movingVal - 1) / side;
        int oldDist = Math.abs(newSpaceRow - goalRow) + Math.abs(newSpaceCol - goalCol);
        int newDist = Math.abs(oldSpaceRow - goalRow) + Math.abs(oldSpaceCol - goalCol);
        int distImprovement = oldDist - newDist;
        //System.out.println("dist delta = " + distImprovement);
        swap(oldSpaceRow, oldSpaceCol, newSpaceRow, newSpaceCol, newBlocks);
        return new Board(newBlocks, side, (short)(manhattan - distImprovement));
    }


    /**
     * @return row column coordinates of the space position
     */
    private Location getSpacePosition() {
        for (byte i = 0; i < side; i++) {
            for (byte j = 0; j < side; j++) {
                if (blocks[i * side + j] == 0) {
                    return new ByteLocation(i, j);
                }
            }
        }
        throw new IllegalStateException("No space position!");
    }

    private static byte[] makeBlocks(int[][] src) {
        int length = src.length;
        byte[] target = new byte[length * length];
        for (byte i = 0; i < length; i++) {
            for (byte j = 0; j < length; j++) {
                target[i*length + j] = (byte) src[i][j];
            }
        }
        return target;
    }

    private static byte[] copyBlocks(byte[] src) {
        int length = src.length;
        byte[] target = new byte[length];
        System.arraycopy(src, 0, target, 0, length);
        return target;
    }

    private void swap(int row1, int col1,  int row2, int col2, byte[] b) {
        byte p1 = (byte) (row1 * side + col1);
        byte p2 =  (byte) (row2 * side + col2);
        byte temp = b[p1];
        b[p1] = b[p2];
        b[p2] = temp;
    }

    /** @return string representation of this board  */
    public String toString() {
        return stringForm;
    }

    private String getStringForm() {
        StringBuilder s = new StringBuilder();
        s.append(side).append("\n");
        for (byte i = 0; i < side; i++) {
            for (byte j = 0; j < side; j++) {
               s.append(String.format("%2d ", blocks[i * side + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

    }
}