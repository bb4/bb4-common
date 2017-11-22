package com.barrybecker4.common.app;

import com.barrybecker4.common.i18n.StubMessageContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class AppContextTest {

    /**
     * Verify that we get an error if the AppContext was not stubbed with a MessageContext.
     */
    @Test(expected = NullPointerException.class)
    public void testGetLabelWhenNoMessageContext() {
         AppContext.getLabel("FOO");
    }

    @Test
    public void testGetLabel() {
        AppContext.injectMessageContext(new StubMessageContext());

        String label = AppContext.getLabel("FOO");
        assertEquals("Unexpected label", "foo", label);

        AppContext.injectMessageContext(null);
    }

    @Test
    public void testGetLabelWithParams() {
        AppContext.injectMessageContext(new StubMessageContext());

        String label = AppContext.getLabel("FOO_BAR", new String[] {"param1", "param2"});
        assertEquals("Unexpected label", "foo_bar", label);

        AppContext.injectMessageContext(null);
    }
}
