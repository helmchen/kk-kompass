/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.util;

import ch.helmchen.kompass.meta.ApplicationInfo;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author helmut
 */
@Provider
public class BusinessExceptionWrapper implements ExceptionMapper<BusinessException> {

    /**
     *
     * @param exception
     * @return
     */
    public Response toResponse(BusinessException exception) {
        Response response = Response.status(404).build();
         ApplicationInfo.info(BusinessExceptionWrapper.class, 
                 ApplicationInfo.VALIDATION, "sending404dueToException", 
                 exception.getLocalizedMessage(), null);
        return response;
    }
}
