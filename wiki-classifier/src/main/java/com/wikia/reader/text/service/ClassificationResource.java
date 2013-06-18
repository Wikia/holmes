package com.wikia.reader.text.service;/**
 * Author: Artur Dwornik
 * Date: 13.04.13
 * Time: 11:05
 */

import com.google.common.base.Strings;
import com.wikia.reader.text.classifiers.ClassifierManager;
import com.wikia.reader.text.data.InstanceSource;
import com.wikia.reader.text.service.model.ClassificationCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.MalformedURLException;
import java.net.URL;

@Path("/classifications")
public class ClassificationResource {
    private static Logger logger = LoggerFactory.getLogger(ClassificationResource.class);
    private static ClassifierManager classifierManager;
    static {
        setClassifierManager(new ClassifierManager());
    }

    public static ClassifierManager getClassifierManager() {
        return classifierManager;
    }

    public static void setClassifierManager(ClassifierManager classifierManager) {
        ClassificationResource.classifierManager = classifierManager;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{wikiName}/{page}")
    public ClassificationCollection getClassifications(@PathParam("wikiName") String wikiName,@PathParam("page")  String page) {
        //return  new ClassificationCollection(
        //        Lists.newArrayList(new Classification(Lists.newArrayList("class"))));
        if( Strings.isNullOrEmpty(wikiName) || Strings.isNullOrEmpty(page) || page.startsWith("Special:") ) {
            return null;
        }
        try {
            InstanceSource source = new InstanceSource(new URL("http://" +  wikiName + ".wikia.com"), page, null);
            return getClassifierManager().classify(source);
        } catch (MalformedURLException e) {
            logger.error("Cannot parse url", e);
            return null;
        }
    }

}
