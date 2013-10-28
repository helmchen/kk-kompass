/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.dataload;

import ch.helmchen.kompass.insurance.entities.InsuranceProvider;
import ch.helmchen.kompass.meta.entities.DataPool;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author helmut
 */
@XmlRootElement
public class InsuranceProviders implements Comparator<InsuranceProvider>{
    private DataPool dataPool;
    private Set<InsuranceProvider> insuranceProviders;
    
    {
        dataPool = new DataPool();
        insuranceProviders = new HashSet<InsuranceProvider>();
    }

    public DataPool getDataPool() {
        return dataPool;
    }

    public void setDataPool(DataPool dataPool) {
        this.dataPool = dataPool;
    }

    public Set<InsuranceProvider> getInsuranceProviders() {
        return insuranceProviders;
    }

    public void setInsuranceProviders(Set<InsuranceProvider> insuranceProviders) {
        this.insuranceProviders = insuranceProviders;
    }
    
    public void addInsuranceProvider(InsuranceProvider insuranceProvider) {
        insuranceProviders.add(insuranceProvider);
    }
   

    @Override
    public int compare(InsuranceProvider first, InsuranceProvider second) {
        if ((! isSet(first)) && (! isSet(second))) {
            return 0;
        }
        if (! isSet(first)) {
            return 1;
        }
        if (! isSet(second)) {
            return -1;
        }
        return first.getFophNumber().compareTo(second.getFophNumber());
        }
    
    private boolean isSet(InsuranceProvider ip) {
        return (ip != null && ip.getFophNumber() != null && ip.getFophNumber().getValue() != null);
   
    }
    
}
