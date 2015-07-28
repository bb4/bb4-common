/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Using this class you should be able to easily parallelize a set of long running tasks.
 * Immutable.
 *
 * @author Barry Becker
 */
class CallableParallelizer<T> {

    /** The number of processors available on this computer */
    public static final int NUM_PROCESSORS = Runtime.getRuntime().availableProcessors();

    /** Recycle threads so we do not create thousands and eventually run out of memory. */
    protected ExecutorService executor;

    /** optional done handler to call on results as they become ready */
    private DoneHandler<T> doneHandler;

    /**
     * By default, the number of threads we use is equal to the number of processors
     * (in some cases I read it may be better to add 1 to this, but I have not seen better results doing that.)
     */
    private static final int DEFAULT_NUM_THREADS = NUM_PROCESSORS;

    /** Number of threads to distribute the tasks among. */
    private int numThreads;

    /**
     * Constructs with default number of threads.
     */
    public CallableParallelizer() {

        this(DEFAULT_NUM_THREADS);
    }

    /**
     * Construct with specified number of threads.
     * @param numThreads number of thread. Must be 1 or greater. One means not parallelism.
     */
    public CallableParallelizer(int numThreads) {
        assert numThreads > 0;
        this.numThreads = numThreads;
        executor = Executors.newFixedThreadPool(numThreads);
    }


    /** @param doneHandler gets called on the result when processing of a task is done */
    public void setDoneHandler(DoneHandler<T> doneHandler) {
        this.doneHandler = doneHandler;
    }

    /** @return number of threads in the executor thread pool. */
    public int getNumThreads() {
        return numThreads;
    }

    /**
     * Invoke all the workers at once and block until they are all done
     * Once all the separate threads have completed their assigned work, you may want to commit the results.
     * @return list of Future tasks.
     */
    public List<Future<T>> invokeAll(Collection<? extends Callable<T>> callables)  {

        List<Future<T>> futures = null;
        try {
            futures = executor.invokeAll(callables);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return futures;
    }


    /**
     * Invoke all the workers at once and optionally call doneHandler on the results as they complete.
     * Once all the separate threads have completed their assigned work, you may want to commit the results.
     * @param callables list of workers to execute in parallel.
     */
    public void invokeAllWithCallback(List<Callable<T>> callables)  {

        List<Future<T>> futures = invokeAll(callables);
        ExecutorCompletionService<T> completionService = new ExecutorCompletionService<>(executor);

        for (final Callable<T> callable : callables) {
            completionService.submit(callable);
        }

        try {
            for (int i = 0; i < futures.size(); i++) {
                final Future<T> future = completionService.take();
                try {
                    T result = future.get();
                    if (doneHandler != null && result != null) {
                        doneHandler.done(result);

                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
