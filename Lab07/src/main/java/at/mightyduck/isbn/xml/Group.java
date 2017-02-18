/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.isbn.xml;

import at.mightyduck.isbn.ISBN;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Simon
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType
public class Group {
    @XmlElement(name = "Prefix", type = String.class, required = true)
    private String prefix;
    @XmlElement(name = "Agency", type = String.class, required = true)
    private String agency;
    @XmlElementWrapper(name = "Rules")
    @XmlElement(name = "Rule")
    private List<Rule> rules;

    public Group() {
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public boolean matches(ISBN result) {
        return this.getPrefix().equals(result.getPrefix() + "-" + result.getRegistrationGroup());
    }
    
}
