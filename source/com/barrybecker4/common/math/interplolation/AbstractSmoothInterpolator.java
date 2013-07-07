/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math.interplolation;

/**
 * @author Barry Becker
 */
public abstract class AbstractSmoothInterpolator extends AbstractInterpolator {

    AbstractSmoothInterpolator(double[] function) {
        super(function);
    }

    @Override
    public double interpolate(double value) {
        int len = function.length - 1;
        double x = value * (double) len;
        int index0 = (int) x;
        int index1 = index0 + 1;
        double xdiff = x - index0;
        //System.out.println("cub:  x=" + x + " xdiff="+ xdiff  + " index0="+ index0 + " index1=" + index1);

        // we need to come up with the 4 points to use for interpolation
        double y1 = function[ index0 ];
        double y0 = y1;
        double y2 = y1;
        if (len > 0)
            y2 = function[ index1 ];
        double y3 = y2;
        if (index0 > 0) {
            y0 = function[index0-1];
        }
        if (index1 < len) {
            y3 = function[index1+1];
        }
        //System.out.println("y0=" + y0 + " y1=" + y1 + " y2=" + y2 + " y3=" + y3 + "  xdiff=" + xdiff);
        return smoothInterpolate(y0, y1, y2, y3, xdiff);
    }

    protected abstract double smoothInterpolate(double y0,double y1, double y2,double y3, double mu);
}