/** Copyright by Barry G. Becker, 2000-2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.i18n1;

import com.barrybecker4.common.app1.ILog;

import javax.swing.JComponent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Manage access to localized message bundles.
 * When creating an instance specify the paths to the resource bundles to use.
 *
 * @author Barry Becker
 */
public class MessageContext {

    public static final LocaleType DEFAULT_LOCALE = LocaleType.ENGLISH;

    /** logger object. Use console by default. */
    private ILog logger;

    /** debug level */
    private int debug = 0;

    /** the list of paths the define where to get the messageBundles */
    private List<String> resourcePaths;

    /** the list of bundles to look for messages in */
    private List<ResourceBundle> messagesBundles;

    private LocaleType currentLocale = DEFAULT_LOCALE;


    /**
     * Constructor
     * @param resourcePath path to message bundle
     */
    public MessageContext(String resourcePath) {
        this(new ArrayList<>(Collections.singletonList(resourcePath)));
    }

    /**
     * Constructor
     * @param resourcePaths list of paths to message bundles
     */
    public MessageContext(List<String> resourcePaths) {
        this.resourcePaths = resourcePaths;
        messagesBundles = new ArrayList<>();
    }

    /**
     * @param resourcePath another resource path to get a message bundle from.
     */
    public void addResourcePath(String resourcePath) {
        if (!resourcePaths.contains(resourcePath)) {
            resourcePaths.add(resourcePath);
            messagesBundles.clear();
        }
    }

    public void setDebugMode(int debugMode) {
        debug = debugMode;
    }

    /**
     * @param logger the logging device. Determines where the output goes.
     */
    public void setLogger( ILog logger ) {
        assert logger != null;
        this.logger = logger;
    }


    private void log(int logLevel, String message) {
        if (logger == null) {
            throw new RuntimeException("Set a logger on the MessageContext before calling log.");
        }
        logger.print(logLevel, debug, message);
    }

    /**
     * Set or change the current locale.
     * @param localeName name locale to use (something like ENGLISH, GERMAN, etc)
     */
    public void setLocale(String localeName) {
        setLocale(getLocale(localeName, true));
    }

    /**
     * Set or change the current locale.
     * @param locale locale to use
     */
    public void setLocale(LocaleType locale) {
        currentLocale = locale;
        messagesBundles.clear();
        initMessageBundles(currentLocale);
        JComponent.setDefaultLocale(currentLocale.getLocale());
    }

    public Locale getLocale() {
        return currentLocale.getLocale();
    }

    /**
     * Look first in the common message bundle.
     * If not found there, look in the application specific bundle if there is one.
     * @param key the message key to find in resource bundle.
     * @return  the localized message label
     */
    public String getLabel(String key)  {
        return getLabel(key, null);
    }

    /**
     * Look first in the common message bundle.
     * If not found there, look in the application specific bundle if there is one.
     * @param key the message key to find in resource bundle.
     * @param params typically a list of string sto use a parameters to the template defined by the message from key.
     * @return  the localized message label
     */
    public String getLabel(String key, Object[] params)  {
        String label = key;
        if (messagesBundles.isEmpty())  {
            initMessageBundles(currentLocale);
        }
        boolean found = false;
        int numBundles = messagesBundles.size();
        int ct = 0;
        while (!found && ct < numBundles) {
            ResourceBundle bundle = messagesBundles.get(ct++);
            if (bundle.containsKey(key))  {
                label = bundle.getString(key);
                if (params != null) {
                    MessageFormat formatter = new MessageFormat(label, currentLocale.getLocale());
                    label = formatter.format(params);
                }
                found = true;
            }
        }

        if (!found) {
            String msg = "Could not find label for " + key + " among " + resourcePaths.toString();   // NON-NLS
            log(0, msg);
            throw new MissingResourceException(msg, resourcePaths.toString(), key);
        }
        return label;
    }


    private void initMessageBundles(LocaleType locale) {

        for (String path : resourcePaths) {
            ResourceBundle bundle = ResourceBundle.getBundle(path, locale.getLocale());
            if (bundle == null) {
                throw new IllegalArgumentException("Messages bundle for "+ path + " was not found.");
            }
            messagesBundles.add(bundle);
        }

        JComponent.setDefaultLocale(locale.getLocale());
    }

    /**
     * Looks up an {@link LocaleType} for a given locale name.
     * @param finf fail if not found.
     * @return locale the name of a local. Something like ENGLISH, GERMAN, etc
     * @throws Error if the name is not a member of the enumeration
     */
    public LocaleType getLocale(String name, boolean finf) {
        LocaleType type; // english is the default
        try {
            type = LocaleType.valueOf(name);
        }
        catch (IllegalAccessError e) {
            log(0,  "***************" );
            log(0, name +" is not a valid locale. We currently only support: ");  // NON-NLS
            LocaleType[] values = LocaleType.values();
            for (final LocaleType newVar : values) {
                log(0, newVar.toString());
            }
            log(0,  "Defaulting to English." );  // NON-NLS
            log(0, "***************" );
            assert (!finf);
            throw e;
        }
        return type;
    }
}