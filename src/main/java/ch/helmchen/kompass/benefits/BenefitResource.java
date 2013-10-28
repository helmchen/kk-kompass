/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.benefits;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;

/**
 * REST Web Service
 *
 * @author helmut
 */
@Path("benefit")
@RequestScoped
public class BenefitResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BenefitResource
     */
    public BenefitResource() {
    }

    /**
     * Retrieves representation of an instance of ch.helmchen.kompass.benefits.BenefitResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of BenefitResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }
}
