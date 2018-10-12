package com.TahaHussein.bookstore.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

/**
 * @author Taha Hussein
 */
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {
    //https://stackoverflow.com/questions/28306103/angular-js-no-access-control-allow-origin-header-is-present-on-the-requested
    @OPTIONS
    public Response options() {
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .header("Access-Control-Allow-Credentials",true)
                .build();
    }
}
