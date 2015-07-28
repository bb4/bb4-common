package com.barrybecker4.common.search.slidingpuzzle;

import com.barrybecker4.common.search.slidingpuzzle.Board;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Read a puzzle board of any size
 * @author Barry Becker
 */
public class BoardReader {

    public Board read(String filename) {

        InputStream str = getClass().getResourceAsStream("resources/"+ filename);
        assert str != null;
        Scanner in = new Scanner(str);

        int sidLen = in.nextInt();
        int[][] blocks = new int[sidLen][sidLen];
        for (int i = 0; i < sidLen; i++) {
            for (int j = 0; j < sidLen; j++) {
                blocks[i][j] = in.nextInt();
            }
        }
        return new Board(blocks);
    }
}
