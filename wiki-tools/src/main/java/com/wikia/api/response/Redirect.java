package com.wikia.api.response;

import java.io.Serializable;

/**
 * Author: Artur Dwornik
 * Date: 04.06.13
 * Time: 21:14
 */

public class Redirect  implements Serializable {
    private String from;
    private String to;

    public Redirect(String from, String to) {
        this.from = from;
        this.to = to;
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
