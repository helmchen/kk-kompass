/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.insurance.entities;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author helmut
 */
@Entity
@XmlRootElement
public class InsuranceProvider implements Serializable {

    /**
     *
     */
    public static final String DATAPOOL_NAME = "InsuranceProvider";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private FophNumber fophNumber;
    @NotNull
    private int version;
    @NotNull
    @Size(min = 3, max = 128)
    private String name;
    @NotNull
    private LegalForm legalForm;
    private String holdingId;
    @ElementCollection
    @CollectionTable(name = "InsuranceProviderAddress", joinColumns = @JoinColumn(name = "id"))
    private List<PostalContact> addresses;
    @ElementCollection
    @CollectionTable(name = "InsuranceProviderPhone", joinColumns = @JoinColumn(name = "id"))
    private List<TelecomContact> phoneContacts;
    @ElementCollection
    @CollectionTable(name = "InsuranceProviderEmail", joinColumns = @JoinColumn(name = "id"))
    private List<EmailContact> emailAddresses;
    private URL homePage;
    private String imagePath;
    @Size(min = 4, max = 7)
    private String cdColor;

    {
        addresses = new ArrayList<PostalContact>();
        phoneContacts = new ArrayList<TelecomContact>();
        emailAddresses = new ArrayList<EmailContact>();

    }

    /**
     *
     * @return
     */
    public Integer getId() {
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
    @XmlAttribute
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
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public LegalForm getLegalForm() {
        return legalForm;
    }

    /**
     *
     * @param legalForm
     */
    public void setLegalForm(LegalForm legalForm) {
        this.legalForm = legalForm;
    }

    /**
     *
     * @return
     */
    public String getHoldingId() {
        return holdingId;
    }

    /**
     *
     * @param holdingId
     */
    public void setHoldingId(String holdingId) {
        this.holdingId = holdingId;
    }

    /**
     *
     * @return
     */
    public List<PostalContact> getAddresses() {
        return addresses;
    }

    /**
     *
     * @param addresses
     */
    public void setAddresses(List<PostalContact> addresses) {
        this.addresses = addresses;
    }

    /**
     *
     * @return
     */
    public List<TelecomContact> getPhoneContacts() {
        return phoneContacts;
    }

    /**
     *
     * @param phoneContacts
     */
    public void setPhoneContacts(List<TelecomContact> phoneContacts) {
        this.phoneContacts = phoneContacts;
    }

    /**
     *
     * @return
     */
    public List<EmailContact> getEmailAddresses() {
        return emailAddresses;
    }

    /**
     *
     * @param emailAddresses
     */
    public void setEmailAddresses(List<EmailContact> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    /**
     *
     * @return
     */
    public URL getHomePage() {
        return homePage;
    }

    /**
     *
     * @param homePage
     */
    public void setHomePage(URL homePage) {
        this.homePage = homePage;
    }

    /**
     *
     * @return
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     *
     * @param imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     *
     * @return
     */
    public String getCdColor() {
        return cdColor;
    }

    /**
     *
     * @param cdColor
     */
    public void setCdColor(String cdColor) {
        this.cdColor = cdColor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InsuranceProvider other = (InsuranceProvider) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean add(EmailContact e) {
        return emailAddresses.add(e);
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean add(PostalContact e) {
        return addresses.add(e);
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean add(TelecomContact e) {
        return phoneContacts.add(e);
    }

    @Override
    public String toString() {
        return "InsuranceProvider{" + "id=" + id + ", fophNumber=" + fophNumber + ", version=" + version + ", name=" + name + ", legalForm=" + legalForm + ", holdingId=" + holdingId + ", addresses=" + addresses + ", phoneContacts=" + phoneContacts + ", emailAddresses=" + emailAddresses + ", homePage=" + homePage + ", imagePath=" + imagePath + ", cdColor=" + cdColor + '}';
    }

}
