package com.barrybecker4.common.concurrency;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static junit.framework.TestCase.assertEquals;


/**
 * @author Barry Becker
 */
public class CallableParallelizerTest {

    private CallableParallelizer<Result> parallelizer;

    @Before
    public void setUp() {
        parallelizer = new CallableParallelizer<>();
    }


    @Test
    public void runParallelTasksWithDoneHandler() {

        final StringBuilder finalResult = new StringBuilder();

        List<Callable<Result>> workers = new ArrayList<>();
        for (int i = 1000; i < 100000; i += 10000) {
            workers.add(new Worker(i));
        }
        parallelizer.invokeAllWithCallback(workers, new DoneHandler<Result>() {
            @Override
            public void done(Result result) {
                finalResult.append(result.getSum()).append(" ");
            }
        });

        // these results could be in an y order, but yhe string length shoud be constant
        assertEquals("Unexpected result ",
                "499500 60494500 220489500 840479500 480484500 1300474500 2520464500 1860469500 3280459500 4140454500 ".length(),
                finalResult.length());
    }

    /** do some long running task like sum up some number */
    private class Worker implements Callable<Result> {

        int num;
        Worker(int num) {
            this.num = num;
        }

        @Override
        public Result call() {
            long total = 0;
            for (int i=0; i<num; i++) {
               total += i;
            }
            return new Result(total);
        }
    }
}
