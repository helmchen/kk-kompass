/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.meta.boundary;

import ch.helmchen.kompass.meta.ApplicationInfo;
import ch.helmchen.kompass.meta.entities.DataPool;
import ch.helmchen.kompass.meta.entities.DataPoolType;
import ch.helmchen.kompass.util.BusinessException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import javax.ejb.PostActivate;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.owasp.esapi.codecs.MySQLCodec;

/**
 *
 * @author helmut
 */
@Path("/meta/" + ApplicationInfo.API_VERSION + "/{dataPoolType}/{validAt}")
@Produces({"application/json", "application/xml"})
@RequestScoped
public class MetaResource {

    private static final MySQLCodec DB_CODEC;
    private static final DateFormat DF;

    static {
        DB_CODEC = new MySQLCodec(MySQLCodec.Mode.STANDARD);
        DF = DateFormat.getDateInstance(DateFormat.SHORT);
    }
    @Inject
    MetaService metaService;

    /**
     *
     */
    @PostActivate
    public void afterInit() {
        ApplicationInfo.info(MetaResource.class, ApplicationInfo.SYSTEM, "beanCreated", this);
    }

    /**
     *
     * @param aDataPoolType
     * @param aDate
     * @return
     * @throws BusinessException
     */
    @GET
    public DataPool getDataPool(@PathParam("dataPoolType") final String aDataPoolType, @PathParam("validAt") final String aDate) throws BusinessException {
        ApplicationInfo.info(MetaResource.class, ApplicationInfo.VALIDATION, "receivedRequest", aDataPoolType, aDate);
        return metaService.getDataPool(secureDataPoolType(aDataPoolType), secureDate(aDate));
    }

    private DataPoolType secureDataPoolType(final String aDataPoolType) throws BusinessException {
        try {
            final String strDataPoolType = DB_CODEC.decode(aDataPoolType);
            final DataPoolType result = DataPoolType.valueOf(strDataPoolType.toUpperCase());
            ApplicationInfo.debug(MetaResource.class, ApplicationInfo.SECURITY, "paramDecoded", aDataPoolType, result);
            return result;
        } catch (Exception ex) {
            throw new BusinessException("error during secureDate", ex);
        }
    }

    private Date secureDate(final String aDate) throws BusinessException {
        try {
            final String strValidAt = DB_CODEC.decode(aDate);
            final Date result;
            try {
                result = DF.parse(strValidAt);
            } catch (ParseException ex) {
                throw new IllegalArgumentException(ApplicationInfo.getMessage(ApplicationInfo.VALIDATION, "illegalArgumentValue"), ex);
            }
            ApplicationInfo.debug(MetaResource.class, ApplicationInfo.SECURITY, "paramDecoded", aDate, result);
            return result;
        } catch (Exception ex) {
            throw new BusinessException("error during secureDate", ex);
        }
    }

    /**
     *
     * @return
     */
    public String doTest() {
        return "test";
    }
}
