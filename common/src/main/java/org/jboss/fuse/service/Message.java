package org.jboss.fuse.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Header;
import org.jboss.fuse.model.Response;

public class Message {

    public String helloWorld(@Header("id") String id) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Response response = new Response();
        response.setId(id);
        response.setMessage("Hellow World");

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);

    }
}
