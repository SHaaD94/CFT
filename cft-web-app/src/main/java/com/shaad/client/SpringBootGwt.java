package com.shaad.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.Defaults;

public class SpringBootGwt implements EntryPoint {

    public void onModuleLoad() {
        useCorrectRequestBaseUrl();
        com.google.gwt.user.client.ui.RootPanel.get().add(new RootPanel());
    }

    private void useCorrectRequestBaseUrl() {
        if (isDevelopmentMode()) {
            Defaults.setServiceRoot("http://localhost:8080");
        } else {
            Defaults.setServiceRoot(GWT.getHostPageBaseURL());
        }
    }

    private static native boolean isDevelopmentMode()/*-{
        return typeof $wnd.__gwt_sdm !== 'undefined';
    }-*/;
}
