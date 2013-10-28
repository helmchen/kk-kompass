/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License  
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.primes.entities;

import ch.helmchen.kompass.insurance.entities.FophNumber;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author helmut
 */
public class OhiPrime {

    /**
     *
     */
    public static final String DATAPOOL_NAME = "OihPrime";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private FophNumber fophNumber;
    @NotNull
    private int version;
    private OhiType type;
    private String tariffName;
    private int tariffOrder;
    private boolean limited;
    // DB-Key Land + Kanton + Prämienregion.
    private String ohiZone;
    private int ageFrom;
    private int ageUntil;
    private boolean withAccident;
    private double franchise;
    private double prime;

    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public FophNumber getFophNumber() {
        return fophNumber;
    }

    /**
     *
     * @param fophNumber
     */
    public void setFophNumber(FophNumber fophNumber) {
        this.fophNumber = fophNumber;
    }

    /**
     *
     * @return
     */
    public int getVersion() {
        return version;
    }

    /**
     *
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     *
     * @return
     */
    public OhiType getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(OhiType type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public String getTariffName() {
        return tariffName;
    }

    /**
     *
     * @param tariffName
     */
    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    /**
     *
     * @return
     */
    public int getTariffOrder() {
        return tariffOrder;
    }

    /**
     *
     * @param tariffOrder
     */
    public void setTariffOrder(int tariffOrder) {
        this.tariffOrder = tariffOrder;
    }

    /**
     *
     * @return
     */
    public boolean isLimited() {
        return limited;
    }

    /**
     *
     * @param limited
     */
    public void setLimited(boolean limited) {
        this.limited = limited;
    }

    /**
     *
     * @return
     */
    public String getOhiZone() {
        return ohiZone;
    }

    /**
     *
     * @param ohiZone
     */
    public void setOhiZone(String ohiZone) {
        this.ohiZone = ohiZone;
    }

    /**
     *
     * @return
     */
    public int getAgeFrom() {
        return ageFrom;
    }

    /**
     *
     * @param ageFrom
     */
    public void setAgeFrom(int ageFrom) {
        this.ageFrom = ageFrom;
    }

    /**
     *
     * @return
     */
    public int getAgeUntil() {
        return ageUntil;
    }

    /**
     *
     * @param ageUntil
     */
    public void setAgeUntil(int ageUntil) {
        this.ageUntil = ageUntil;
    }

    /**
     *
     * @return
     */
    public boolean isWithAccident() {
        return withAccident;
    }

    /**
     *
     * @param withAccident
     */
    public void setWithAccident(boolean withAccident) {
        this.withAccident = withAccident;
    }

    /**
     *
     * @return
     */
    public double getFranchise() {
        return franchise;
    }

    /**
     *
     * @param franchise
     */
    public void setFranchise(double franchise) {
        this.franchise = franchise;
    }

    /**
     *
     * @return
     */
    public double getPrime() {
        return prime;
    }

    /**
     *
     * @param prime
     */
    public void setPrime(double prime) {
        this.prime = prime;
    }

    @Override
    public String toString() {
        return "OhiPrime{" + "id=" + id + ", fophNumber=" + fophNumber + ", version=" + version + ", type=" + type + ", tariffName=" + tariffName + ", tariffOrder=" + tariffOrder + ", limited=" + limited + ", ohiZone=" + ohiZone + ", ageFrom=" + ageFrom + ", ageUntil=" + ageUntil + ", withAccident=" + withAccident + ", franchise=" + franchise + ", prime=" + prime + '}';
    }
    
    
}
