/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License  
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.insurance.entities;

import ch.helmchen.kompass.geo.entities.StructureNumber;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author helmut
 */
public class InsuredStock {

    /**
     *
     */
    public static final String DATAPOOL_NAME = "InsuredStock";

      @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private FophNumber fophNumber;
    @NotNull
    private int version;
    @NotNull
    private StructureNumber structureNumber;
  private int insuredStock;
  private int ranking;

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
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
    public StructureNumber getStructureNumber() {
        return structureNumber;
    }

    /**
     *
     * @param structureNumber
     */
    public void setStructureNumber(StructureNumber structureNumber) {
        this.structureNumber = structureNumber;
    }

    /**
     *
     * @return
     */
    public int getInsuredStock() {
        return insuredStock;
    }

    /**
     *
     * @param insuredStock
     */
    public void setInsuredStock(int insuredStock) {
        this.insuredStock = insuredStock;
    }

    /**
     *
     * @return
     */
    public int getRanking() {
        return ranking;
    }

    /**
     *
     * @param ranking
     */
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
  
  
}
