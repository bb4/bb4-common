/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.format;

import java.text.DecimalFormat;

/**
 * Miscellaneous commonly used static utility methods.
 */
public final class FormatUtil {

    private static final DecimalFormat EXP_FORMAT = new DecimalFormat("0.###E0"); //NON-NLS
    private static final DecimalFormat NUM_FORMAT = new DecimalFormat("###,###.##");
    private static final DecimalFormat INT_FORMAT = new DecimalFormat("#,###");

    private FormatUtil() {}


    /**
     * Show a reasonable number of significant digits.
     * @param num the number to format.
     * @return a nicely formatted string representation of the number.
     */
    public static String formatNumber(double num) {
        double absnum = Math.abs(num);

        if (absnum == 0)  {
            return "0";
        }
        if (absnum > 10000000.0 || absnum < 0.000000001) {
            return EXP_FORMAT.format(num);
        }

        if (absnum > 1000.0) {
            NUM_FORMAT.setMinimumFractionDigits(0);
            NUM_FORMAT.setMaximumFractionDigits(0);
        }
        else if (absnum > 100.0) {
            NUM_FORMAT.setMinimumFractionDigits(1);
            NUM_FORMAT.setMaximumFractionDigits(1);
        }
        else if (absnum > 1.0) {
            NUM_FORMAT.setMinimumFractionDigits(1);
            NUM_FORMAT.setMaximumFractionDigits(3);
        }
        else if (absnum > 0.0001) {
            NUM_FORMAT.setMinimumFractionDigits(2);
            NUM_FORMAT.setMaximumFractionDigits(5);
        }
        else if (absnum>0.000001) {
            NUM_FORMAT.setMinimumFractionDigits(3);
            NUM_FORMAT.setMaximumFractionDigits(8);
        }
        else {
            NUM_FORMAT.setMinimumFractionDigits(6);
            NUM_FORMAT.setMaximumFractionDigits(11);
        }

        return NUM_FORMAT.format(num);
    }

    /**
     * @param num the number to format.
     * @return a nicely formatted string representation of the number.
     */
    public static String formatNumber(long num) {
        return INT_FORMAT.format(num);
    }

    /**
     * @param num the number to format.
     * @return a nicely formatted string representation of the number.
     */
    public static String formatNumber(int num) {
        return INT_FORMAT.format(num);
    }

    public static void main(String[] args) {
        System.out.println("formatted small number: " + FormatUtil.formatNumber(0.00000003456)); //NON-NLS
        System.out.println("formatted medium number: " + FormatUtil.formatNumber(239909.034983456)); //NON-NLS
        System.out.println("formatted large number: " + FormatUtil.formatNumber(1234981289879875329290.3456)); //NON-NLS
    }
}

