/*
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.control;

import ch.helmchen.kompass.geo.entities.Community;
import static ch.helmchen.kompass.geo.entities.Community.PARAM_COMMUNITY_NUMBER;
import static ch.helmchen.kompass.geo.entities.Community.PARAM_VERSION;
import static ch.helmchen.kompass.geo.entities.Community.QUERY_FIND_BY_COMMUNITY_NUMBER;
import ch.helmchen.kompass.geo.entities.CommunityNumber;
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
public class CommunityAccess extends GeoAccess<Community> {

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
    public CommunityAccess() {
        super(Community.class);
    }

    /**
     *
     * @param aVersion
     * @param aCommunityNumber
     * @return
     */
    public Community findByCommunityNumber(final int aVersion, final CommunityNumber aCommunityNumber) {
        Query q = getEntityManager().createNamedQuery(QUERY_FIND_BY_COMMUNITY_NUMBER);
        q.setParameter(PARAM_VERSION, aVersion);
        q.setParameter(PARAM_COMMUNITY_NUMBER, aCommunityNumber);
        return (Community) q.getSingleResult();
    }

}
