package com.barrybecker4.common.i18n;

import com.barrybecker4.common.app.ILog;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.MissingResourceException;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class MessageContextTest {

    /** instance under test */
    private MessageContext context;

    @Test
    public void testConstructionWithValidPath() {
        context = new MessageContext("com.barrybecker4.common.i18n.message");

        assertEquals("Unexpected message",
                "bar", context.getLabel("FOO"));
    }

    @Test(expected = MissingResourceException.class)
    public void testGetLabelWhenLabelMissing() {
        context = new MessageContext("com.barrybecker4.common.i18n.message");
        context.setLogger(new MyLogger());
        context.getLabel("INVALID");
    }

    @Test(expected = MissingResourceException.class)
    public void testConstructionWithInvalidPath() {
        context = new MessageContext("com.barrybecker4.common.invalid.message");
        context.getLabel("FOO");
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
