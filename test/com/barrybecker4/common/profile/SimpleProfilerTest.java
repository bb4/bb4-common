// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.profile;

import com.barrybecker4.common.app.ILog;
import com.barrybecker4.common.concurrency.ThreadUtil;
import junit.framework.TestCase;

/**
 * @author Barry Becker
 */
public class SimpleProfilerTest extends TestCase {

    /** instance under test */
    SimpleProfiler profiler;

    @Override
    public void setUp() {
        profiler = new SimpleProfiler();
    }

    public void testProfilerTime() {

        profiler.start();
        ThreadUtil.sleep(10);
        profiler.stop();

        ThreadUtil.sleep(5);

        ProfilerEntry entry = profiler.getEntry(SimpleProfiler.ROOT);
        Long elapsed = entry.getTime();
        assertTrue("Unexpected elapsed time: " + elapsed, elapsed>=10 && elapsed <=12);
    }

    public void testProfilerTimeWhenDisabled() {

        profiler.setEnabled(false);
        profiler.start();
        ThreadUtil.sleep(10);
        profiler.stop();

        ProfilerEntry entry = profiler.getEntry(SimpleProfiler.ROOT);
        assertEquals("Unexpected elapsed time", 0, entry.getTime());
    }

    public void testProfilerLogging() {

        profiler.start();
        ThreadUtil.sleep(10);
        profiler.stop();

        ILog logger = createLogger();
        profiler.setLogger(logger);

        profiler.print();
        assertEquals("Unexpected elapsed time",
                "Time for totalTime : 0.01 seconds\n", logger.toString());
    }

    private ILog createLogger() {
        ILog logger = new MemoryLogger();
        StringBuilder bldr = new StringBuilder();
        logger.setStringBuilder(bldr);
        return logger;
    }


}
