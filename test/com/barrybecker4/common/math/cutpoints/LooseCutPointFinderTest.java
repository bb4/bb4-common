// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.math.cutpoints;

import com.barrybecker4.common.math.Range;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * @author Barry Becker
 */
public class LooseCutPointFinderTest extends TestCase {

    /** instance under test */
    private LooseCutPointFinder finder = new LooseCutPointFinder();

    public void testFindCutPoints() {
        double[] cuts = finder.getCutPoints(new Range(10.0, 22.0), 5);

        assertTrue("undexpected cuts. Got:" + Arrays.toString(cuts),
                Arrays.equals(new double[] {10.0, 15.0, 20.0, 25.0}, cuts));
    }

}
