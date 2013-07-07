/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math;

import java.util.Arrays;

/**
 * Provide support for high dimensional arrays of doubles.
 * Eventually this class should support multi-dimensional arrays of any type, but
 * for now if only supports doubles.
 * Use this class when you need to create an arbitrarily sized array of >1 dimension.
 *
 * @author Barry Becker
 */
public class MultiArray {

    /** the number of dimensions. May be any number  >= 0. */
    private final int numDims_;

    /** product of number of dimensions. All dimensions multiplied together. */
    private final int numVals_;

    /** the size of each dimension in the multi-dim array. */
    private final int dims_[];

    /** this will hold all the data for this array class. */
    private double[] arrayData_ = null;

    /**
     * Constructor
     */
    public MultiArray( int[] dims ) {
        dims_ = dims.clone();
        numDims_ = dims_.length;
        numVals_ = getDimensionProduct();
        assert (numDims_ > 0) : "You must have > 0 dimension to use this class" ;
        assert (numVals_ > 0) : "The product of the dimension lengths must be greater than 0. It was " + numVals_;
        arrayData_ = new double[numVals_];
    }

    public int getNumDims() {
        return numDims_;
    }

    public int getNumValues() {
        return numVals_;
    }

    /**
     * @return the product of the first n dimensions. returns 1 if n is 0
     */
    private int getDimensionProduct()  {
        int prod = 1;
        if ( numDims_ == 0 )
            return prod;

        for ( int j = 0; j < numDims_; j++ ) {
            prod *= dims_[j];
        }
        return prod;
    }

    /**
     * specify a tuple to get a particular value.
     * @param index an integer index array of size numDims specifying a location in the data array.
     * @return array value.
     */
    public double get( int[] index ) {
        return arrayData_[getRawIndex( index )];
    }

    /**
     * specify a tuple to set a given value.
     * @param index location in the data array.
     * @param value to assign to that location.
     */
    public void set( int[] index, double value ) {
        arrayData_[getRawIndex( index )] = value;
    }

    /**
     * specify a tuple to get a particular value.
     * see getIndexFromRaw for how we can get a multi-dim index from a single raw integer index.
     * @param rawIndex int specifying location in the multi dim array.
     */
    public double getRaw( int rawIndex ) {
        return arrayData_[rawIndex];
    }

    /**
     * get a raw integer index from a integer array of indices.
     * @param index n dimensional array of indices.
     * @return raw index
     */
    private int getRawIndex( int[] index )  {
        assert ( index.length == numDims_) :
                "the index you specified has " + index.length + " values, when you need to specify " + numDims_;
        int rawIndex = index[numDims_ - 1];
        int offset = dims_[numDims_ - 1];
        for ( int i = numDims_ - 2; i >= 0; i-- ) {
            rawIndex += offset * index[i];
            offset *= dims_[i];
        }
        return rawIndex;
    }

    /**
     * get an integer array of indices from a raw integer index.
     * @param rawIndex the raw integer index to convert to an array of int indices.
     * @return an integer array of indices from a raw integer index.
     */
    public int[] getIndexFromRaw( int rawIndex ) {
        int[] index = new int[numDims_];
        int prod = 1;
        for ( int i = numDims_ - 1; i >= 0; i-- ) {
            index[i] = rawIndex / prod % dims_[i];
            if ( i > 0 )  {
                prod *= dims_[i];
            }
        }
        return index;
    }

    /**
     * @param rawIndex raw index
     * @return a string representation of the tuple
     * eg "3,5,9,3"
     */
    public String getIndexKey( int rawIndex ) {
        int[] index = getIndexFromRaw( rawIndex );
        return Arrays.toString(index);
    }
}



