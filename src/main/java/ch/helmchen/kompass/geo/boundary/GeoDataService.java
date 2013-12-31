/*
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.boundary;

import ch.helmchen.kompass.geo.control.CommunityAccess;
import ch.helmchen.kompass.geo.control.PoliticalStructureAccess;
import ch.helmchen.kompass.geo.entities.Community;
import ch.helmchen.kompass.geo.entities.CommunityNumber;
import ch.helmchen.kompass.geo.entities.PoliticalStructure;
import ch.helmchen.kompass.geo.entities.StructureDepth;
import ch.helmchen.kompass.meta.ApplicationInfo;
import ch.helmchen.kompass.meta.boundary.MetaService;
import ch.helmchen.kompass.meta.entities.DataPool;
import ch.helmchen.kompass.meta.entities.DataPoolType;
import ch.helmchen.kompass.meta.entities.License;
import ch.helmchen.kompass.util.EntityNotFoundException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Business-Logik für den Bereich Geodaten. Dieser Service stellt den Zugriff auf die Daten des
 * Bereichs Geodaten bereit. Neben dem Zugriff auf aktuelle Entities für die Unterstützung der
 * Ausfüllhilfe werden auch diverse Business-Funktionen für die Geodaten implementiert.
 *
 * @author helmut
 */
@Stateless
@LocalBean
public class GeoDataService {

    @EJB
    private MetaService metaService;
    @EJB
    private PoliticalStructureAccess politicalStructureAccess;
    @EJB
    private CommunityAccess communityAccess;

    private static final int MINIMUM_ACCEPTED_ENTRY_LENGTH = 3;

    private static final DataPoolType COMMUNITY_DATA_POOL = DataPoolType.PRIMES;
    private static final DataPoolType STRUCTURE_DATA_POOL = DataPoolType.GEOADATA;

    @PostConstruct
    private void afterInit() {
        // Prüft, ob die Injections sauber vorgenommen worden sind.
        if (metaService == null) {
            ApplicationInfo.warn(GeoDataService.class, ApplicationInfo.SYSTEM, ApplicationInfo.MISSING_RESOURCE, MetaService.class.getName());
        }
        if (politicalStructureAccess == null) {
            ApplicationInfo.warn(GeoDataService.class, ApplicationInfo.SYSTEM, ApplicationInfo.MISSING_RESOURCE, PoliticalStructureAccess.class.getName());
        }
        if (communityAccess == null) {
            ApplicationInfo.warn(GeoDataService.class, ApplicationInfo.SYSTEM, ApplicationInfo.MISSING_RESOURCE, CommunityAccess.class.getName());
        }
        ApplicationInfo.info(GeoDataService.class, ApplicationInfo.SYSTEM, ApplicationInfo.BEAN_CREATED, this);
    }

    /**
     * Stellt Daten für die Ausfüllhilfe zur Verfügung.
     *
     * @param anInput
     * @return
     */
    public Set<PoliticalStructure> findCurrentForInput(final String anInput) {
        Set<PoliticalStructure> result = new LinkedHashSet<>();

        if (anInput == null || anInput.isEmpty()
                || anInput.length() < MINIMUM_ACCEPTED_ENTRY_LENGTH) {
            ApplicationInfo.debug(GeoDataService.class,
                    ApplicationInfo.VALIDATION, ApplicationInfo.IGNORING_UNDERSIZED, anInput);
            return result;
        }
        final int version = getCurrentVersion(STRUCTURE_DATA_POOL);
        final String code = anInput;
        result.addAll(politicalStructureAccess.findByCode(
                version, code, StructureDepth.PROVINCE, StructureDepth.POSTAL_CODE));
        result.addAll(politicalStructureAccess.findByName(
                version, code, StructureDepth.PROVINCE, StructureDepth.POSTAL_CODE));
        return result;
    }

    /**
     *
     * @param aPostCode
     * @return
     */
    public Set<PoliticalStructure> findCurrentForPostCodeInput(final String aPostCode) {
        Set<PoliticalStructure> result = new LinkedHashSet<>();

        if (aPostCode == null || aPostCode.isEmpty()
                || aPostCode.length() < MINIMUM_ACCEPTED_ENTRY_LENGTH) {
            ApplicationInfo.debug(GeoDataService.class,
                    ApplicationInfo.VALIDATION, ApplicationInfo.IGNORING_UNDERSIZED, aPostCode);
            return result;
        }
        final int version = getCurrentVersion(STRUCTURE_DATA_POOL);
        final String code = aPostCode;
        result.addAll(politicalStructureAccess.findByCode(
                version, code, StructureDepth.POSTAL_CODE, StructureDepth.POSTAL_CODE));
        return result;
    }

    /**
     *
     * @param aCommunityNumber
     * @return
     * @throws EntityNotFoundException
     */
    public Community findCurrentCommunity(final CommunityNumber aCommunityNumber)
            throws EntityNotFoundException {
        if (aCommunityNumber == null || aCommunityNumber.isEmpty()) {
            IllegalArgumentException emptyArgument
                    = new IllegalArgumentException("Empty value " + aCommunityNumber
                            + " for communityNumber is not allowed.");
            throw new EntityNotFoundException(
                    ApplicationInfo.getMessage(ApplicationInfo.VALIDATION, ApplicationInfo.ILLEGAL_ARGUMENT_VALUE),
                    emptyArgument);
        }
        final int version = getCurrentVersion(COMMUNITY_DATA_POOL);
        return communityAccess.findByCommunityNumber(version, aCommunityNumber);
    }

    public void addCommunities(final List<Community> theCommunities) {
        if (theCommunities == null || theCommunities.isEmpty()) {
            throw new IllegalArgumentException("Empty value " + theCommunities
                    + " for theCommunities is not allowed.");
        }
        // TODO Neuen Release erstellen.
        int version = 0;
        for (Community community : theCommunities) {
            community.setVersion(version);
            communityAccess.create(community);
        }
    }

    private int getCurrentVersion(final DataPoolType aDataPool) {
        final Date now = new Date(System.currentTimeMillis());
        final DataPool dataPool;
        try {
            dataPool = metaService.getDataPool(aDataPool, now);
            return dataPool.getVersion();

        }
        catch (EntityNotFoundException ex) {
            ApplicationInfo.warn(GeoDataService.class, ApplicationInfo.STORAGE, ApplicationInfo.NO_VERSION_FOUND_FOR_X_AT_Y,
                    DataPoolType.GEOADATA, now);
            return 1;
        }
    }

    private int createNewVersion(final DataPoolType aDataPoolType, final String licenser, 
            final License aLicense, final Date aValidFrom, final Date aValidUntil) {
        final DataPool dataPool;
        dataPool = metaService.createDataPool(aDataPoolType, licenser, aLicense, aValidFrom, aValidUntil);
        return dataPool.getVersion();
    }

}
