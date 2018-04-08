/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.profile1;

import com.barrybecker4.common.app1.ILog;
import com.barrybecker4.common.format1.FormatUtil;

import java.util.LinkedList;
import java.util.List;


/**
 * Represents the timing numbers for a named region of the code.
 *
 * @author Barry Becker
 */
public class ProfilerEntry {

    private static final String INDENT = "    ";

    /** the name of this profiler entry  */
    private final String name;

    private long startTime = 0;

    /** the total time used by this named code section while the app was running  */
    private long totalTime = 0;

    private final List<ProfilerEntry> children = new LinkedList<>();

    /** Constructor */
    public ProfilerEntry(String name) {
        this.name = name;
    }

    protected void addChild(ProfilerEntry child) {
        children.add(child);
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        totalTime += System.currentTimeMillis() - startTime;
    }

    public long getTime() {
        return totalTime;
    }

    public double getTimeInSeconds() {
        return (double) totalTime /1000.0;
    }

    protected void resetAll() {
        totalTime = 0;
        for (ProfilerEntry p : children) {
            p.resetAll();
        }
    }

    public void print() {
        print("", null);
    }

    public void print(String indent, ILog logger) {

        String text = indent + getFormattedTime();
        if (logger == null)
            System.out.println(text);
        else
            logger.println(text);

        long totalChildTime = 0;
        for (ProfilerEntry pe : children) {
            totalChildTime += pe.getTime();
            pe.print(indent + INDENT, logger);
        }

        assert (totalChildTime <= 1.0 * totalTime): "The sum of the child times("+totalChildTime
                +") cannot be greater than the parent time ("+ totalTime +") for entry '" + name + "'. " +
                "child entries =" + children;
    }

    public String toString() {
        return getFormattedTime();
    }

    private String getFormattedTime() {
        double seconds = getTimeInSeconds();
        return  "Time for " + name + " : " + FormatUtil.formatNumber(seconds) + " seconds"; //NON-NLS
    }
}