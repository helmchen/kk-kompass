/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License  
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.meta;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 *
 * @author helmut
 */
//@Singleton
@ApplicationPath("/rest")
public class ApplicationInfo extends Application {

    /**
     * Marker for System Messages.
     */
    public static final Marker SYSTEM = MarkerFactory.getMarker("system");
    /**
     * Marker for Security Messages.
     */
    public static final Marker SECURITY = MarkerFactory.getMarker("security");
    /**
     * Marker for Test Messages (should only be used in Unit and Integration tests.
     */
    public static final Marker TEST = MarkerFactory.getMarker("test");
    /**
     * Marker for performance measurement Messages.
     */
    public static final Marker PERFORMANCE = MarkerFactory.getMarker("performance");

    /**
     *
     */
    public static final Marker VALIDATION = MarkerFactory.getMarker("validation");
    /**
     * Marker for messages of the storage layer.
     */
    public static final Marker STORAGE = MarkerFactory.getMarker("storage");
    // Path to the message resources.
    private static final String MESSAGE_BUNDLE_PATH = "ch/helmchen/kompass/text/Messages";
    // currently loaded resources.
    private static ResourceBundle DEFAULT_MESSAGE_BUNDLE;
    //locale of the current resources.
    private static Locale locale;

    /**
     *
     */
    public static final String API_VERSION = "1.0";

    static {
        DEFAULT_MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGE_BUNDLE_PATH);
        locale = Locale.getDefault();
        info(ApplicationInfo.class, SYSTEM, "copyright");
    }

    /**
     *
     * @return
     */
    public static ResourceBundle getMessages() {
        return DEFAULT_MESSAGE_BUNDLE;
    }

    /**
     *
     * @param aMarker
     * @param aKey
     * @return
     */
    public static String getMessage(final Marker aMarker, final String aKey) {
        if (aMarker == null || aKey == null) {
            return null;
        }
        final String accessKey = aMarker.getName() + "." + aKey;
        return DEFAULT_MESSAGE_BUNDLE.getString(accessKey);
    }

    /**
     *
     * @param aClass
     * @param aMarker
     * @param aKey
     * @param theValues
     */
    public static void debug(Class aClass, Marker aMarker, String aKey, Object... theValues) {
        Logger logger = LoggerFactory.getLogger(aClass);
        if (logger.isDebugEnabled(aMarker)) {
            logger.debug(aMarker, getMessage(aMarker, aKey), theValues);
        }
    }

    /**
     *
     * @param aClass
     * @param aMarker
     * @param aKey
     * @param theValues
     */
    public static void info(Class aClass, Marker aMarker, String aKey, Object... theValues) {
        Logger logger = LoggerFactory.getLogger(aClass);
        if (logger.isInfoEnabled(aMarker)) {
            logger.info(aMarker, getMessage(aMarker, aKey), theValues);
        }
    }

    /**
     *
     * @param aClass
     * @param aMarker
     * @param aKey
     * @param theValues
     */
    public static void warn(Class aClass, Marker aMarker, String aKey, Object... theValues) {
        Logger logger = LoggerFactory.getLogger(aClass);
        if (logger.isWarnEnabled(aMarker)) {
            logger.warn(aMarker, getMessage(aMarker, aKey), theValues);
        }
    }

    public static String toShortString(final Collection<? extends Object> aCollection) {
        final StringBuilder result = new StringBuilder();
        result.append(aCollection.getClass().getName());
        result.append("@");
        result.append(Integer.toHexString(aCollection.hashCode()));
        result.append("{");

        int i = 0;
        for (Object o : aCollection) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(o);
            if (i++ >= 3) {
                break;
            }
        }
        result.append(" ... ");
        result.append(aCollection.size());
        result.append(" elements");
        return result.toString();
    }

    public static String toString(final Object instance, Map<String, Object> theProperties) {
        final StringBuilder result = new StringBuilder();
        result.append(instance.getClass().getName());
        result.append("@");
        result.append(Integer.toHexString(instance.hashCode()));
        result.append("{");
        boolean isFirst = true;
        for (String propertyName : theProperties.keySet()) {
            Object propertyValue = theProperties.get(propertyName);
            String value = null;
            if (propertyValue == null) {
                value = "null";
            } else if (propertyValue instanceof Collection
                    && ((Collection) propertyValue).size() > 3) {
                // Bei grossen Listen geben wir nur die Grösse aus
                value = toShortString((Collection) propertyValue);
            } else {
                value = propertyValue.toString();
            }
            if (isFirst) {
                isFirst = false;
            } else {
                result.append(", ");
            }
            result.append(propertyName);
            result.append("=");
            result.append(value);
        }
        result.append("}");
        return result.toString();
    }

    /**
     *
     * @return
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        // following code can be used to customize Jersey 2.0 JSON provider:
        try {
            Class jsonProvider = Class.forName("org.glassfish.jersey.jackson.JacksonFeature");
            // Class jsonProvider = Class.forName("org.glassfish.jersey.moxy.json.MoxyJsonFeature");
            // Class jsonProvider = Class.forName("org.glassfish.jersey.jettison.JettisonFeature");
            resources.add(jsonProvider);
        }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically re-generated by NetBeans
     * REST support to populate given list with all resources defined in the project.
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ch.helmchen.kompass.benefits.BenefitResource.class);
        resources.add(ch.helmchen.kompass.meta.boundary.MetaResource.class);
        resources.add(ch.helmchen.kompass.util.BusinessExceptionWrapper.class);
    }
}
