package net.wikia.graph.matcher;
/**
 * Author: Artur Dwornik
 * Date: 01.07.13
 * Time: 04:01
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vertex {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(Vertex.class);
    private long id;
    private String title;

    public Vertex(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        return id == vertex.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                '}';
    }
}
