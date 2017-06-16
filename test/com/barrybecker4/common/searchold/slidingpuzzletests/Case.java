package com.barrybecker4.common.searchold.slidingpuzzletests;

/**
 * @author Barry Becker
 */
public class Case {

    String filename;
    int expNumMoves;
    boolean expIsSolvable;

    Case(String fname, int expNumMoves, boolean expIsSolvable) {
        this.filename = fname;
        this.expNumMoves = expNumMoves;
        this.expIsSolvable = expIsSolvable;
    }
}
