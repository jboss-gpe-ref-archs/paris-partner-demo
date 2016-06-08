package org.jboss.fuse.microservice;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class CamelRestRoute extends RouteBuilder {

    private static final String HOST = "0.0.0.0";

    @Override
    public void configure() throws Exception {

        restConfiguration()
            .component("servlet")
            .contextPath("/camel/rest")
            .host(HOST);

        // use the rest DSL to define the rest services
        rest("/users")
            .get("/{id}/hello")
                .route()
                .log("!! Request processd by the Camel REST Servlet")
                .log(LoggingLevel.DEBUG,"HTTP Path : ${header.CamelHttpPath}")
                .log(LoggingLevel.DEBUG,"HTTP Uri : ${header.CamelHttpUri}")
                .beanRef("service","helloWorld");
    }
}
