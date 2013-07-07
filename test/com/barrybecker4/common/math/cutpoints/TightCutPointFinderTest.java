// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math.cutpoints;

import com.barrybecker4.common.math.Range;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * @author Barry Becker
 */
public class TightCutPointFinderTest extends TestCase {

    /** instance under test */
    private TightCutPointFinder finder = new TightCutPointFinder();

    public void testFindCutPoints() {
        double[] cuts = finder.getCutPoints(new Range(10.0, 22.0), 5);

        assertTrue("undexpected cuts. Got:" + Arrays.toString(cuts),
                Arrays.equals(new double[] {10.0, 15.0, 20.0, 22.0}, cuts));
    }

    public void testFindCutPointsWhenHighPresisionEndPoints() {
        double[] cuts = finder.getCutPoints(new Range(11.234, 22.567), 4);

        assertTrue("undexpected cuts. Got:" + Arrays.toString(cuts),
                Arrays.equals(new double[] {11.234, 15.0, 20.0, 22.567}, cuts));
    }

}
