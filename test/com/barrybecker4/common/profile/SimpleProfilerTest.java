// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.profile;

import com.barrybecker4.common.app.ILog;
import com.barrybecker4.common.concurrency1.ThreadUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Barry Becker
 */
public class SimpleProfilerTest  {

    /** instance under test */
    SimpleProfiler profiler;

    @Before
    public void setUp() {
        profiler = new SimpleProfiler();
    }

    @Test
    public void testProfilerTime() {

        profiler.start();
        ThreadUtil.sleep(10);
        profiler.stop();

        ThreadUtil.sleep(5);

        ProfilerEntry entry = profiler.getEntry(SimpleProfiler.ROOT);
        Long elapsed = entry.getTime();
        // any elapsed time is acceptable, as different machines perform differently.
        assertTrue("Unexpected elapsed time: " + elapsed, elapsed>=0 );
    }

    @Test
    public void testProfilerTimeWhenDisabled() {

        profiler.setEnabled(false);
        profiler.start();
        ThreadUtil.sleep(10);
        profiler.stop();

        ProfilerEntry entry = profiler.getEntry(SimpleProfiler.ROOT);
        assertEquals("Unexpected elapsed time", 0, entry.getTime());
    }

    @Test
    public void testProfilerLogging() {

        profiler.start();
        ThreadUtil.sleep(10);
        profiler.stop();

        ILog logger = createLogger();
        profiler.setLogger(logger);

        profiler.print();
        assertTrue("Unexpected elapsed time",
                    logger.toString().startsWith("Time for totalTime : "));
    }

    private ILog createLogger() {
        ILog logger = new MemoryLogger();
        StringBuilder bldr = new StringBuilder();
        logger.setStringBuilder(bldr);
        return logger;
    }


}
