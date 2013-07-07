/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Using this class you should be able to easily parallelize a set of long running tasks.
 * Immutable.
 *
 * @author Barry Becker
 */
public class Parallelizer <T> extends CallableParallelizer<T> {

    /** Constructor */
    public Parallelizer() {}

    /**
     * Constructor
     * @param numThreads the number of threads that are assumed available on the hardware.
     */
    public Parallelizer(int numThreads) {
        super(numThreads);
    }

    /**
     * Invoke all the workers at once and blocks until they are all done.
     * Once all the separate threads have completed their assigned work, you may want to commit the results.
     */
    public void invokeAllRunnables(List<Runnable> workers)  {

        // convert the runnables to callables so the invokeAll api works
        List<Callable<T>> callables = new ArrayList<Callable<T>>(workers.size());
        for (Runnable r : workers) {
            callables.add(Executors.callable(r, (T)null));
        }

        List<Future<T>> futures = invokeAll(callables);

        // consider using ExecutorCompletionService so that the results can be processed as they become available
        // rather than blocking on one of them arbitrarily.
        for (Future<T> f : futures) {
            try {
                // blocks until the result is available.
                f.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(Parallelizer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(Parallelizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
