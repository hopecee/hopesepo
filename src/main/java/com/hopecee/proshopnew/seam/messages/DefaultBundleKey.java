/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.seam.messages;

import org.jboss.seam.international.status.builder.BundleKey;

/**
 *
 * @author hope
 */
public class DefaultBundleKey extends BundleKey {

    public static final String DEFAULT_BUNDLE_NAME = "messages";

    public DefaultBundleKey(String key) {
        super(DEFAULT_BUNDLE_NAME, key);
    }
}
