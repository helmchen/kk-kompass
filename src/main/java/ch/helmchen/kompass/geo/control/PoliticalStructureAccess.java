/*
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.control;

import ch.helmchen.kompass.geo.entities.PoliticalStructure;
import static ch.helmchen.kompass.geo.entities.PoliticalStructure.PARAM_CODE;
import static ch.helmchen.kompass.geo.entities.PoliticalStructure.PARAM_DEEPEST_LEVEL;
import static ch.helmchen.kompass.geo.entities.PoliticalStructure.PARAM_HIGHEST_LEVEL;
import static ch.helmchen.kompass.geo.entities.PoliticalStructure.PARAM_NAME;
import static ch.helmchen.kompass.geo.entities.PoliticalStructure.PARAM_VERSION;
import static ch.helmchen.kompass.geo.entities.PoliticalStructure.QUERY_FIND_BY_CODE;
import static ch.helmchen.kompass.geo.entities.PoliticalStructure.QUERY_FIND_BY_NAME;
import ch.helmchen.kompass.geo.entities.StructureDepth;
import ch.helmchen.kompass.util.DatabaseHelper;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author helmut
 */
@Stateless
@LocalBean
public class PoliticalStructureAccess extends GeoAccess<PoliticalStructure> {

    @PersistenceContext(unitName = "kkcockpit_persistence")
    private EntityManager em;

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
     */
    public PoliticalStructureAccess() {
        super(PoliticalStructure.class);
    }

    /**
     *
     * @param aVersion
     * @param aCode
     * @param minimumDepth
     * @param maximumDepth
     * @return
     */
    public List<PoliticalStructure> findByCode(final int aVersion, final String aCode,
            final StructureDepth minimumDepth, final StructureDepth maximumDepth) {
        Query q = getEntityManager().createNamedQuery(QUERY_FIND_BY_CODE);
        q.setParameter(PARAM_VERSION, aVersion);
        q.setParameter(PARAM_CODE, DatabaseHelper.beginsWith(aCode));
        q.setParameter(PARAM_DEEPEST_LEVEL, minimumDepth);
        q.setParameter(PARAM_HIGHEST_LEVEL, maximumDepth);
        return q.getResultList();
    }

    /**
     *
     * @param aVersion
     * @param aName
     * @param minimumDepth
     * @param maximumDepth
     * @return
     */
    public List<PoliticalStructure> findByName(final int aVersion, final String aName,
            final StructureDepth minimumDepth, final StructureDepth maximumDepth) {
        Query q = getEntityManager().createNamedQuery(QUERY_FIND_BY_NAME);
        q.setParameter(PARAM_VERSION, aVersion);
        q.setParameter(PARAM_NAME, DatabaseHelper.beginsWith(aName));
        q.setParameter(PARAM_DEEPEST_LEVEL, minimumDepth);
        q.setParameter(PARAM_HIGHEST_LEVEL, maximumDepth);
        return q.getResultList();
    }

}
