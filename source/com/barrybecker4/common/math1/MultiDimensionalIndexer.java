/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math1;

import java.util.Arrays;

/**
 * Provide support for indexing into high dimensional arrays.
 * It does not actually allocate memory for the large dimensional array. That is up to the client app to manage.
 * Can be used to create an arbitrarily sized array of &gt; 1 dimension.
 *
 * @author Barry Becker
 */
public class MultiDimensionalIndexer {

    /** the number of dimensions. May be any number  >= 0. */
    private final int numDims;

    /** product of number of dimensions. All dimensions multiplied together. */
    private final long numVals;

    /** the size of each dimension in the multi-dim array. */
    private final int dims[];


    /**
     * Constructor
     */
    public MultiDimensionalIndexer(int[] dims) {
        this.dims = dims.clone();
        numDims = this.dims.length;
        numVals = getDimensionProduct();
        if (numDims <= 0) {
            throw new IllegalArgumentException("You must have > 0 dimension to use this class");
        }
        if (numVals <= 0) {
            throw new IllegalArgumentException(
                    "The product of the dimension lengths must be greater than 0. It was " + numVals);
        }
    }

    public int getNumDims() {
        return numDims;
    }

    /**
     * @return the total number of values in the hypothetical array
     */
    public long getNumValues() {
        return numVals;
    }

    /**
     * @return the product of the first n dimensions. returns 1 if n is 0
     */
    private int getDimensionProduct()  {
        int prod = 1;
        if ( numDims == 0 )
            return prod;

        for ( int j = 0; j < numDims; j++ ) {
            prod *= dims[j];
        }
        return prod;
    }

    /**
     * get a raw integer index from a integer array of indices.
     * @param index n dimensional array of indices.
     * @return raw index
     */
    int getRawIndex( int[] index )  {
        assert ( index.length == numDims) :
                "the index you specified has " + index.length + " values, when you need to specify " + numDims;
        int rawIndex = index[numDims- 1];
        int offset = dims[numDims - 1];
        for ( int i = numDims - 2; i >= 0; i-- ) {
            rawIndex += offset * index[i];
            offset *= dims[i];
        }
        return rawIndex;
    }

    /**
     * get an integer array of indices from a raw integer index.
     * @param rawIndex the raw integer index to convert to an array of int indices.
     * @return an integer array of indices from a raw integer index.
     */
    public int[] getIndexFromRaw( int rawIndex ) {
        int[] index = new int[numDims];
        int prod = 1;
        for ( int i = numDims - 1; i >= 0; i-- ) {
            index[i] = rawIndex / prod % dims[i];
            if ( i > 0 )  {
                prod *= dims[i];
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