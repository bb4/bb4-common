/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.app1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class CommandLineOptionsTest {

    @Test
    public void testCommandLineOptionsToString() {

        String[] testArgs = {"-a", "b", "-c", "dog", "-e", "-f", "-type", "foo", "-h"};
        CommandLineOptions options = new CommandLineOptions(testArgs);
        assertEquals("unexpected", "{a=b, c=dog, e=null, f=null, type=foo, h=null}", options.toString());
    }

    @Test
    public void testEmptyCommandLineOptions() {

        String[] testArgs = {};
        CommandLineOptions options = new CommandLineOptions(testArgs);
        assertEquals("unexpected", "{}", options.toString());
    }

    @Test(expected=AssertionError.class)
    public void testCommandLineOptionsWithNoDash() {
        String[] testArgs = {"a", "b", "-c", "dog"};
        CommandLineOptions options = new CommandLineOptions(testArgs);
    }

    @Test
    public void testCommandLineOptionsWithSpace() {

        String[] testArgs = {"-a", " b", "-c ", "dog"};
        CommandLineOptions options = new CommandLineOptions(testArgs);
        assertEquals("unexpected", "{a=b, c=dog}", options.toString());
    }

    @Test
    public void testCommandLineOptionsWithoutValues() {

        String[] testArgs = {"-abc", "-c", "dog", "-efg"};
        CommandLineOptions options = new CommandLineOptions(testArgs);
        assertEquals("unexpected", "{abc=null, c=dog, efg=null}", options.toString());
    }

}