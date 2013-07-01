package net.wikia.graph.matcher;
/**
 * Author: Artur Dwornik
 * Date: 01.07.13
 * Time: 04:01
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Edge {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(Edge.class);
    private Vertex from;
    private Vertex to;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
    }

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge edge = (Edge) o;

        if (!from.equals(edge.from)) return false;
        if (!to.equals(edge.to)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }
}
