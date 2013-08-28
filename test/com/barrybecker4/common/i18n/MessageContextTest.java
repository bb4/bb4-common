package com.barrybecker4.common.i18n;

import com.barrybecker4.common.app.ILog;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.util.MissingResourceException;

/**
 * @author Barry Becker
 */
public class MessageContextTest extends TestCase {

    /** instance under test */
    private MessageContext context;

    public void testConstructionWithValidPath() {
        context = new MessageContext("com.barrybecker4.common.i18n.message");

        assertEquals("Unexpected message",
                "bar", context.getLabel("FOO"));
    }

    public void testGetLabelWhenLabelMissing() {
        context = new MessageContext("com.barrybecker4.common.i18n.message");
        context.setLogger(new MyLogger());
        try {
            context.getLabel("INVALID");
            fail();
        } catch(MissingResourceException e) {
            // success
        }
    }

    public void testConstructionWithInvalidPath() {
        context = new MessageContext("com.barrybecker4.common.invalid.message");

        try {
            context.getLabel("FOO");
            fail();
        } catch(MissingResourceException e) {
            // success
        }
    }

    private class MyLogger implements ILog {

        @Override
        public void setDestination(int logDestination) {}

        @Override
        public int getDestination() { return 0; }

        @Override
        public void setLogFile(String fileName) throws FileNotFoundException {}

        @Override
        public void setStringBuilder(StringBuilder bldr) {}

        @Override
        public void print(int logLevel, int appLogLevel, String message) {
            System.out.print(message);
        }

        @Override
        public void println(int logLevel, int appLogLevel, String message) {
            System.out.println(message);
        }

        @Override
        public void print(String message) {
            System.out.print(message);
        }

        @Override
        public void println(String message) {
            System.out.println(message);
        }
    }
}
