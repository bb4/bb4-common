package com.barrybecker4.common.search.slidingpuzzletests;


public class Watch {

    private final long start;

    public Watch() {
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
