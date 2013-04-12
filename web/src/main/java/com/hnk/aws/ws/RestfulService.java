package com.hnk.aws.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service(value = "restfulService")
@Path("/")
public class RestfulService {
    private static Logger LOG = LoggerFactory.getLogger(RestfulService.class.getCanonicalName());

    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHello() {
        return "hello";
    }


}

