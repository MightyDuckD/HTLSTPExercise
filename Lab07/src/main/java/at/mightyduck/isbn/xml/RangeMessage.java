/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.isbn.xml;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Simon
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType
@XmlRootElement(name = "ISBNRangeMessage")
public class RangeMessage {
    @XmlElement(name = "MessageSource", type = String.class, required = true)
    private String messageSource;
    @XmlElement(name = "MessageSerialNumber", type = String.class, required = true)
    private String messageSerialNumber;
    @XmlElement(name = "MessageDate", type = String.class, required = true)
    private String messageDate;
    @XmlElementWrapper(name = "EAN.UCCPrefixes")
    @XmlElement(name = "EAN.UCC")
    private List<EAN_UCC> ean_ucc;
    @XmlElementWrapper(name = "RegistrationGroups")
    @XmlElement(name = "Group")
    private List<Group> registrationGroups;

    public RangeMessage() {
    }

    public String getMessageSerialNumber() {
        return messageSerialNumber;
    }

    public void setMessageSerialNumber(String messageSerialNumber) {
        this.messageSerialNumber = messageSerialNumber;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public List<EAN_UCC> getEan_ucc() {
        return ean_ucc;
    }

    public void setEan_ucc(List<EAN_UCC> ean_ucc) {
        this.ean_ucc = ean_ucc;
    }

    public List<Group> getRegistrationGroups() {
        return registrationGroups;
    }

    public void setRegistrationGroups(List<Group> registrationGroups) {
        this.registrationGroups = registrationGroups;
    }
    
}
