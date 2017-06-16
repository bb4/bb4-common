package com.barrybecker4.common.concurrency;

/**
 * @author Barry Becker
 */
class Result {

    private long sum;

    Result(long sum) {
        this.sum = sum;
    }

    long getSum() {
        return sum;
    }
}
