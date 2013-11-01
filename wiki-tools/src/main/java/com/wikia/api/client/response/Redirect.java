package com.wikia.api.client.response;

import java.io.Serializable;



public class Redirect  implements Serializable {
    private static final long serialVersionUID = -7094562354329443893L;
    private String from;
    private String to;

    public Redirect(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public Redirect() {
        this(null, null);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
