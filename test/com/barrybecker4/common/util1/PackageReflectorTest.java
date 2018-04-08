/** Copyright by Barry G. Becker, 2015. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.util1;


import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @author Barry Becker
 */
public class PackageReflectorTest {

    private PackageReflector reflector;

    @Before
    public void setUp() throws Exception {
        reflector = new PackageReflector();
    }

    @Test
    public void testGetClasses() throws Exception {

        List<Class> classes = reflector.getClasses("com.barrybecker4.common.format");
        assertEquals("Unexpected number of classes in com.barrybecker4.common.format.", 5, classes.size());

        assertEquals("Unexpected first class", "CurrencyFormatter", classes.get(0).getSimpleName());
    }


    @Test
    public void testGetClassesFromJar() throws Exception {

        List<Class> classes = reflector.getClasses("org.junit.runner");
        assertEquals("Unexpected number of classes in org.junit.runner.", 9, classes.size());

        assertEquals("Unexpected first class", "Computer", classes.get(0).getSimpleName());
    }

    @Test
    public void testGetClassesWhenNone() throws Exception {

        List<Class> classes = reflector.getClasses("com.invalid");
        assertEquals("Unexpectedly found classes when none should have been found.", 0, classes.size());
    }

}
