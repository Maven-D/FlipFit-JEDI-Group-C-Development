package com.flipfit.rest;

import com.flipfit.bean.BaseUser;
import com.flipfit.bean.LoginCredentials;
import com.flipfit.business.FlipFitAuthenticationServiceImpl;
import com.flipfit.business.FlipFitAuthenticationServiceInterface;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class FlipFitController {

    private final FlipFitAuthenticationServiceInterface authBusiness = new FlipFitAuthenticationServiceImpl();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(@NotNull @Valid LoginCredentials credentials) {
        // Here, we "get from body" (via the 'credentials' object)
        // and "pass to verifyCredentials".
        BaseUser user = authBusiness.verifyCredentials(credentials.getEmail(), credentials.getPassword());

        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Invalid email or password\"}")
                    .build();
        }
    }
}