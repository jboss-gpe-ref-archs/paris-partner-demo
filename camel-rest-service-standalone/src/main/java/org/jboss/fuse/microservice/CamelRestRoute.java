package org.jboss.fuse.microservice;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;

public class CamelRestRoute extends RouteBuilder {

    private static final String HOST = "0.0.0.0";

    @PropertyInject("server-standalone-port")
    private String PORT;

    @BeanInject("")
    private Service service;

    @Override
    public void configure() throws Exception {

        restConfiguration()
            .component("jetty")
            .contextPath("/camel/rest")
                .endpointProperty("handlers","#securityHandler")
            .host(HOST)
            .setPort(PORT);

        // use the rest DSL to define the rest services
        rest("/users")
            .get("/{id}/hello")
                .route()
                .log(LoggingLevel.DEBUG,"HTTP Path : ${header.CamelHttpPath}")
                .log(LoggingLevel.DEBUG,"HTTP Uri : ${header.CamelHttpUri}")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        String id = exchange.getIn().getHeader("id", String.class);
                        exchange.getOut().setBody("Hello " + id + "! Welcome from machine : " + System.getProperty("karaf.name") );
                    }
                });

    }
}
