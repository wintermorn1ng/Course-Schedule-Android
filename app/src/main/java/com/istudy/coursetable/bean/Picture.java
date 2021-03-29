package com.istudy.coursetable.bean;

import android.net.Uri;

public class Picture {
    private String uri;
    private String label;

    public Picture(String uri, String label) {
        this.uri = uri;
        this.label = label;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
