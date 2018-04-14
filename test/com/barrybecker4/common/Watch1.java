package com.barrybecker4.common;


public class Watch1 {

    private final long start;

    public Watch1() {
        start = System.currentTimeMillis();
    }

    /**
     * Returns the elapsed time (in seconds) since this object was created.
     */
    public double getElapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

}
