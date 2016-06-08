package org.jboss.fuse.microservice;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;

public class CamelRestRoute extends RouteBuilder {

    private static final String HOST = "0.0.0.0";

    @PropertyInject("server-standalone-port")
    private String PORT;

    @Override
    public void configure() throws Exception {

        restConfiguration()
            .component("jetty").contextPath("/camel/rest").scheme("http")
            .host(HOST)
            .setPort(PORT);

        // use the rest DSL to define the rest services
        rest("/users")
            .get("/{id}/hello")
                .route()
                .log(LoggingLevel.DEBUG,"HTTP Path : ${header.CamelHttpPath}")
                .log(LoggingLevel.DEBUG,"HTTP Uri : ${header.CamelHttpUri}")
                .beanRef("service","helloWorld");

    }
}
