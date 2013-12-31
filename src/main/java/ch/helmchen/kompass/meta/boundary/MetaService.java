/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.meta.boundary;

import ch.helmchen.kompass.meta.ApplicationInfo;
import ch.helmchen.kompass.meta.control.DataPoolAccess;
import ch.helmchen.kompass.meta.entities.DataPool;
import ch.helmchen.kompass.meta.entities.DataPoolType;
import ch.helmchen.kompass.meta.entities.License;
import ch.helmchen.kompass.util.EntityNotFoundException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author helmut
 */
@Stateless
@LocalBean
public class MetaService {

    @Inject
    DataPoolAccess dataPoolAccess;

    /**
     *
     * @param aDataPoolType
     * @param aDate
     * @return
     * @throws EntityNotFoundException
     */
    public DataPool getDataPool(final DataPoolType aDataPoolType, final Date aDate) throws EntityNotFoundException {
        return dataPoolAccess.findByKeydate(aDataPoolType, aDate);
    }

    public DataPool createDataPool(final DataPoolType aDataPoolType, final String licenser, final License aLicense, final Date validFrom, final Date validUntil) {
        int version = 1;
        try {
            // bestehenden ermitteln und Gültigkeit einschränken
            final DataPool currentDataPool = getDataPool(aDataPoolType, validFrom);
            version = currentDataPool.getVersion() + 1;
            currentDataPool.setValidUntil(validFrom);
            dataPoolAccess.edit(currentDataPool);
        } catch (EntityNotFoundException ex) {
            ApplicationInfo.info(
                    MetaService.class, ApplicationInfo.STORAGE, 
                    ApplicationInfo.NO_VERSION_FOUND_FOR_X_AT_Y, aDataPoolType, validFrom);
            ApplicationInfo.info(
                    MetaService.class, ApplicationInfo.STORAGE, 
                    ApplicationInfo.EXPECTING_FIRST_VERSION_FOR, aDataPoolType);
        }
        // neuen ergänzen.
        DataPool newDataPool = new DataPool();
        newDataPool.setDataPoolType(aDataPoolType);
        newDataPool.setLicense(aLicense);
        newDataPool.setLicenser(licenser);
        newDataPool.setLoadedAt(new Date(System.currentTimeMillis()));
        newDataPool.setLocked(true);
        newDataPool.setValidFrom(validFrom);
        newDataPool.setValidUntil(validUntil);
        newDataPool.setVersion(version);
        dataPoolAccess.create(newDataPool);
        return newDataPool;
    }
}
