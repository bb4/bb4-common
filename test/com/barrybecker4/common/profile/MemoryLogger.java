// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.profile;

import com.barrybecker4.common.app.ILog;

import java.io.FileNotFoundException;

/**
 * @author Barry Becker
 */
public class MemoryLogger implements ILog {

    StringBuilder bldr;

    @Override
    public void setDestination(int logDestination) {
    }

    @Override
    public int getDestination() {
        return 0;
    }

    @Override
    public void setLogFile(String fileName) throws FileNotFoundException {
    }

    @Override
    public void setStringBuilder(StringBuilder bldr) {
       this.bldr = bldr;
    }

    @Override
    public void print(int logLevel, int appLogLevel, String message) {
    }

    @Override
    public void println(int logLevel, int appLogLevel, String message) {
    }

    @Override
    public void print(String message) {
        bldr.append(message);
    }

    @Override
    public void println(String message) {
        bldr.append(message).append("\n");
    }

    public String toString() {
        return bldr.toString();
    }
}
