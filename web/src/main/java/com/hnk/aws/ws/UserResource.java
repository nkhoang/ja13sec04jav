package com.hnk.aws.ws;

import com.hnk.aws.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service(value = "userResource")
@Path("/user")
public class UserResource {
    private static Logger LOG = LoggerFactory.getLogger(UserResource.class.getCanonicalName());
    @Autowired
    private UserService userService;

    @GET
    @Path("/group/all")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse listAllGropus() {
        return new JsonResponse(userService.listAllGroups());
    }


}

