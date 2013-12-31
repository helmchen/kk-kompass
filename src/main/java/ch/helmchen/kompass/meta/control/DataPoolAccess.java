/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.meta.control;

import ch.helmchen.kompass.meta.entities.DataPool;
import ch.helmchen.kompass.meta.entities.DataPoolType;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import static ch.helmchen.kompass.meta.entities.DataPool.QUERY_FIND_BY_KEYDATE;
import static ch.helmchen.kompass.meta.entities.DataPool.PARAM_DATAPOOL_TYPE;
import static ch.helmchen.kompass.meta.entities.DataPool.PARAM_VALID_AT;
import ch.helmchen.kompass.util.EntityNotFoundException;
import javax.ejb.LocalBean;

/**
 *
 * @author helmut
 */
@Stateless
@LocalBean
public class DataPoolAccess extends MetaAccess<DataPool> {

    @PersistenceContext
    private EntityManager em;

    /**
     *
     */
    public DataPoolAccess() {
        super(DataPool.class);
    }

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     * @param aDataPoolType
     * @param aDate
     * @return
     * @throws EntityNotFoundException
     */
    public DataPool findByKeydate(final DataPoolType aDataPoolType, final Date aDate) throws EntityNotFoundException {
        Query query = em.createNamedQuery(QUERY_FIND_BY_KEYDATE);
        query.setParameter(PARAM_DATAPOOL_TYPE, aDataPoolType);
        query.setParameter(PARAM_VALID_AT, aDate);
        try {
        return (DataPool) query.getSingleResult();
        } catch (javax.persistence.NoResultException noResult) {
            throw new EntityNotFoundException(query.toString(), noResult);
        }
    }
}
