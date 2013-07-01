package net.wikia.graph.matcher;

import com.wikia.api.model.PageInfo;
import org.jgrapht.DirectedGraph;

/**
 * Author: Artur Dwornik
 * Date: 01.07.13
 * Time: 04:22
 */
public interface SubGraphBuilder {
    DirectedGraph<Vertex, Edge> build(PageInfo page);
}
