package com.barrybecker4.common.concurrency;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


/**
 * @author Barry Becker
 */
public class RunnableParallelizerTest {

    private RunnableParallelizer parallelizer;

    @Before
    public void setUp() {
        parallelizer = new RunnableParallelizer();
    }

    @Test
    public void runParallelTasks() {

        List<Runnable> workers = new ArrayList<>();
        for (int i=1000; i<100000; i+=10000) {
            workers.add(new Worker(i));
        }

        parallelizer.invokeAllRunnables(workers);

        String result = "";
        for (Runnable worker : workers) {
            result += ((Worker) worker).getTotal() + " ";
        }

        // these results are always in the same order
        assertEquals("Unexpected result ",
                "999000 120989000 440979000 960969000 1680959000 2600949000 3720939000 5040929000 6560919000 8280909000 ",
                result);
    }

    /** do some long running task like sum up some number */
    private class Worker implements Runnable {

        int num;
        long total = 0;

        Worker(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            for (int i=0; i<num; i++) {
               total += i;
            }
        }

        public long getTotal() {
            return total;
        }
    }
}
