package com.barrybecker4.common.util1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class Base64CodecTest {


    @Test
    public void testCompress() {

        String str = "test/com/barrybecker4/common/util";
        String compressed = Base64Codec.compress(str);
        assertEquals("Unexpected compression result",
                "eJwrSS0u0U/Oz9VPSiwqqkxKTc5OLTIBCeTm5+mXlmTmAADYrAzD", compressed);
    }

    @Test
    public void testUncompress() {
        String uncompressed = Base64Codec.decompress("eJwrSS0u0U/Oz9VPSiwqqkxKTc5OLTIBCeTm5+mXlmTmAADYrAzD");
        assertEquals("Unexpected compression result",
                "test/com/barrybecker4/common/util", uncompressed);
    }

    @Test
    public void testCompressLonger() {

        String str = "The quick brown fox jumped over the lazy brown dog. The quick brown fox jumped over " +
                "the lazy brown dog. The quick brown fox jumped over the lazy brown dog. The quick brown " +
                "fox jumped over the lazy brown dog.";
        String compressed = Base64Codec.compress(str);
        assertEquals("Unexpected compression result",
                "eJwLyUhVKCzNTM5WSCrKL89TSMuvUMgqzS1ITVHIL0stUigByuckVlVCpVPy0/UUQgavHgB/gUr1", compressed);
    }

    @Test
    public void testCompressLonger2() {

        String str = "The quick brown fox jumped over the lazy brown dog. The quick brown fox jumped over " +
                "the lazy brown dog. The quick brown fox jumped over the lazy brown dog. The quick brown " +
                "fox jumped over the lazy brown dog. The quick brown fox jumped over the lazy brXown dog. " +
                "The quick brown fox jumped over " +
                "the lazy brown dog. The quick brown fox jumped over the lazy brown dog. The quick brown " +
                "fox jumped over the lazy brown dog. The quick brown fox jumped over the lazy brown dog. " +
                "The quick brown fox jumped over " +
                "the lazy brown dog. The quick brown fox jumped over the lazy brown dog. The quick brown " +
                "fox jumped over the lazy brown dog.";
        String compressed = Base64Codec.compress(str);
        assertEquals("Unexpected compression result",
                "eJwLyUhVKCzNTM5WSCrKL89TSMuvUMgqzS1ITVHIL0stUigByuckVlVCpVPy0/UUQoaHnojB7LjhqgcAZTLhdQ==",
                compressed);
    }

}
