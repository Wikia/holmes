package net.wikia.graph.matcher;
/**
 * Author: Artur Dwornik
 * Date: 01.07.13
 * Time: 04:09
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EdgeFactory implements org.jgrapht.EdgeFactory<Vertex, Edge> {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(EdgeFactory.class);

    @Override
    public Edge createEdge(Vertex vertex, Vertex vertex2) {
        return new Edge(vertex, vertex2);
    }
}
