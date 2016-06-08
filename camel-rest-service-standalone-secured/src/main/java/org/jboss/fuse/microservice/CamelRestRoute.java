package org.jboss.fuse.microservice;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.jsse.KeyManagersParameters;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;

public class CamelRestRoute extends RouteBuilder {

    private static final String HOST = "0.0.0.0";

    @PropertyInject("server-standalone-secured-port")
    private String PORT;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("jetty").scheme("https")
                .endpointProperty("handlers","#securityHandler")
                .endpointProperty("sslContextParameters","#sslParams")
            .host(HOST)
            .setPort(PORT);

        // use the rest DSL to define the rest services
        rest("/camel/rest/users")
            .get("/{id}/hello")
                .route()
                .log("!! Request processd by the Camel REST Jetty Secured")
                .log(LoggingLevel.DEBUG,"HTTP Path : ${header.CamelHttpPath}")
                .log(LoggingLevel.DEBUG,"HTTP Uri : ${header.CamelHttpUri}")
                .beanRef("service","helloWorld");

    }
}
