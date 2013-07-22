package net.wikia.graph.matcher;
/**
 * Author: Artur Dwornik
 * Date: 01.07.13
 * Time: 04:24
 */

import com.jgraph.layout.JGraphCompoundLayout;
import com.jgraph.layout.JGraphFacade;
import com.wikia.api.model.PageInfo;
import com.wikia.api.service.PageService;
import com.wikia.api.service.PageServiceFactory;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SubGraphBuilderImplIT {
    private static Logger logger = LoggerFactory.getLogger(SubGraphBuilderImplIT.class);
    private PageInfo johnPrice;


    @BeforeMethod
    public void beforeMethod() throws IOException {
        PageServiceFactory pageServiceFactory = new PageServiceFactory();
        PageService pageService = pageServiceFactory.get(new URL("http://callofduty.wikia.com/api.php"));
        johnPrice = pageService.getPage("John_Price");
    }

    @Test(enabled = false)
    public void testBuild() throws Exception {
        final Object lock = new Object();
        logger.info("Start.");
        DirectedGraph<Vertex,Edge> graph = new SubGraphBuilderImpl().build(johnPrice);
        logger.info("Graph is created.");

        JGraphModelAdapter<Vertex, Edge> jGraphModelAdapter = new JGraphModelAdapter<>(graph);
        JGraph jgraph = new JGraph( jGraphModelAdapter );

        List<DefaultGraphCell> roots = new ArrayList<>();
        for (Vertex vertex : graph.vertexSet()) {
            if (graph.inDegreeOf(vertex) == 0) {
                roots.add(jGraphModelAdapter.getVertexCell(vertex));
            }
        }

        com.jgraph.layout.JGraphLayout layout = new JGraphCompoundLayout();
        layout.run(new JGraphFacade(jgraph, roots.toArray()));

        final JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setSize(300, 300);
        jFrame.add(jgraph);
        jFrame.setVisible(true);
        logger.info("window created.");

        Thread t = new Thread() {
            public void run() {
                synchronized(lock) {
                    while (jFrame.isVisible()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Working now");
                }
            }
        };
        t.start();

        jFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent arg0) {
                synchronized (lock) {
                    jFrame.setVisible(false);
                    lock.notify();
                }
            }

        });

        t.join();
    }
}
