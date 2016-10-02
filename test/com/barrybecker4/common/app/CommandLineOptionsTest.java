/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.app;

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
}