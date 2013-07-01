package net.wikia.graph.matcher;
/**
 * Author: Artur Dwornik
 * Date: 01.07.13
 * Time: 03:42
 */

import com.wikia.api.model.PageInfo;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubGraphBuilderImpl implements SubGraphBuilder {
    private static Logger logger = LoggerFactory.getLogger(SubGraphBuilderImpl.class);
    private final org.jgrapht.EdgeFactory<Vertex, Edge> edgeFactory = new EdgeFactory();

    @Override
    public DirectedGraph<Vertex, Edge> build(PageInfo page) {
        DirectedGraph<Vertex, Edge> graph = new DefaultDirectedGraph<>(edgeFactory);

        addPageToGraph(page, graph, true);
        for(PageInfo linkedPage: page.getLinks()) {
            addPageToGraph(linkedPage, graph, false);
        }
        return graph;
    }

    private void addPageToGraph(PageInfo page, Graph<Vertex, Edge> graph, boolean forceAdd) {
        Vertex u = createVertex(page);
        graph.addVertex(u);

        for( PageInfo linkedPage: page.getLinks() ) {
            if( linkedPage != null && linkedPage.getNamespace() == 0 ) {
                Vertex v = createVertex(linkedPage);
                if( v == null ) {
                    continue;
                }
                if( forceAdd ) {
                    graph.addVertex(v);
                    graph.addEdge(u, v);
                } else {
                    if( graph.vertexSet().contains(v) ) {
                        graph.addEdge(u, v);
                    }
                }
            }
        }
    }

    private Vertex createVertex(PageInfo page) {
        if( page == null ) {
            throw new IllegalArgumentException("Page cannot be null.");
        }
        Long id = page.getId();
        String title = page.getTitle();
        if( id == null ) {
            logger.warn(String.format("Illegal state while creating vertex. Id is null, title = %s", title)); // TODO: investigate
            return null;
        }
        if( title == null ) {
            logger.warn(String.format("Illegal state while creating vertex. Title is null, id = %d", id));
            return null;
        }
        return new Vertex(id, title);
    }
}
