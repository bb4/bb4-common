/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math.cutpoints;

import com.barrybecker4.common.math.Range;
import com.barrybecker4.common.math.cutpoints.CutPointGenerator;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * @author Barry Becker
 */
public class NiceNumbersTest extends TestCase {

    private static final String LOOSE = "loose ";
    private static final String TIGHT = "tight ";

    private static final String[] EXPECTED_LOOSE_CUTS = {"0", "20", "40", "60", "80", "100", "120"};
    private static final String[] EXPECTED_TIGHT_CUTS = {"11", "20", "40", "60", "80", "101"};

    /** instance under test */
    private CutPointGenerator generator = new CutPointGenerator();

    public void testNiceNumbers1() {
        generator.setUseTightLabeling(false);
        String[] resultLoose = generator.getCutPointLabels(new Range(11.0, 101.0), 5);
        generator.setUseTightLabeling(true);
        String[] resultTight = generator.getCutPointLabels(new Range(11.0, 101.0), 5);
        assertNotNull("resultLoose should not be null", resultLoose);
        assertNotNull("resultTight should not be null", resultTight);
        assertTrue(LOOSE + Arrays.toString(resultLoose),
                              Arrays.equals(resultLoose, EXPECTED_LOOSE_CUTS));
        assertTrue(TIGHT + Arrays.toString(resultTight),
                              Arrays.equals(resultTight, EXPECTED_TIGHT_CUTS));
    }

    private static final String[] EXPECTED_LOOSE_CUTS2 = {"11.05", "11.1", "11.15", "11.2", "11.25"};
    private static final String[] EXPECTED_TIGHT_CUTS2 = {"11.1", "11.15", "11.2", "11.23"};

    public void testNiceNumbers2() {
        generator.setUseTightLabeling(false);
        String[] resultLoose = generator.getCutPointLabels(new Range(11.1, 11.23), 5);
        generator.setUseTightLabeling(true);
        String[] resultTight = generator.getCutPointLabels(new Range(11.1, 11.23), 5);

        assertTrue(LOOSE + Arrays.toString(resultLoose),
                Arrays.equals(resultLoose, EXPECTED_LOOSE_CUTS2));
        assertTrue(TIGHT + Arrays.toString(resultTight),
                Arrays.equals(resultTight, EXPECTED_TIGHT_CUTS2));
    }

    /** There should be no fractional digits in this case */
    public void testFracDigitsWhenIntegerEndPoints() {
        double expectedNumFractDigits = 0;
        double f = generator.getNumberOfFractionDigits(new Range(10, 100), 3);
        assertEquals("Unexpected.", expectedNumFractDigits, f);

        f = generator.getNumberOfFractionDigits(new Range(1000, 100000), 10);
        assertEquals("Unexpected.", expectedNumFractDigits, f);

        f = generator.getNumberOfFractionDigits(new Range(-1000, -100), 300);
        assertEquals("Unexpected.", expectedNumFractDigits, f);
    }

    /** Min > max/ Expect an error */
    public void testFracDigitsWithHighMaxTicks() {

        Range range = new Range(-0.002, 0.001);
        double f = generator.getNumberOfFractionDigits(range, 900);

        assertEquals("Unexpected.", 6.0, f);
    }

    public void testFracDigitsInThirdDecimalPlace200Ticks() {
        double f = generator.getNumberOfFractionDigits(new Range(-0.002, 0.001), 200);
        assertEquals("Unexpected.", 5.0, f);
    }

    public void testFracDigitsInThirdDecimalPlace2Ticks() {
        double f = generator.getNumberOfFractionDigits(new Range(-0.002, 0.001), 2);
        assertEquals("Unexpected.", 3.0, f);
    }

    public void testFracDigitsSixthDecimalPlace30Ticks() {
        double f = generator.getNumberOfFractionDigits(new Range(0.0000001, 0.0001), 30);
        assertEquals("Unexpected.", 6.0, f);
    }

    public void testFracDigitsSixthDecimalPlace5Ticks() {
        double f = generator.getNumberOfFractionDigits(new Range(0.0000001, 0.0001), 5);
        assertEquals("Unexpected.", 5.0, f);
    }

    public void testFracDigits4() {
        double f = generator.getNumberOfFractionDigits(new Range(-100.01, -100.001), 30);
        assertEquals("Unexpected.", 4.0, f);
    }

}
