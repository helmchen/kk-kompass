/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.meta.boundary;

import ch.helmchen.kompass.meta.control.DataPoolAccess;
import ch.helmchen.kompass.meta.entities.DataPool;
import ch.helmchen.kompass.meta.entities.DataPoolType;
import ch.helmchen.kompass.util.EntityNotFoundException;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;

/**
 *
 * @author helmut
 */
@Stateless
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
}
