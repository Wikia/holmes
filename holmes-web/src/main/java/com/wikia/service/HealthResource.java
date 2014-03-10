package com.wikia.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/health")
public class HealthResource {
    private static final double NANOSECONDS_IN_SECOND = 1.0E-09;
    private long startTime = System.nanoTime();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public HealthStatus index() {
        final Runtime runtime = Runtime.getRuntime();
        return new HealthStatus() {{
            setHealthy(true);
            setFreeMemory(runtime.freeMemory());
            setRunningTime((System.nanoTime() - startTime) * NANOSECONDS_IN_SECOND);
        }};
    }
}

