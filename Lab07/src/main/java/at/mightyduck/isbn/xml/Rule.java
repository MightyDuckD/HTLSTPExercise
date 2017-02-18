/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.isbn.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Simon
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType
public class Rule {
    @XmlElement(name = "Range", type = String.class, required = true)
    private String range;
    @XmlElement(name = "Length", type = Integer.class, required = true)
    private Integer length;

    public Rule() {
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        System.out.println("set range " + range);
        this.range = range;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * True if the first digits of the given isbn is inside the range of
     * this rule.
     *
     * @param str
     * @throws IllegalStateException if the range of the rule is invalid
     * @throws NumberFormatException if the range of the rule is invalid
     * @return //TODO: Testcases
     */
    public boolean matches(String str) {
        String[] ranges = range.split("-");
        String lower = ranges[0];
        String upper = ranges[1];
        if (lower.length() != upper.length()) {
            throw new IllegalStateException("Rule is invalid");
        }
        int part = Integer.parseInt(str.substring(0, lower.length()));
        return Integer.parseInt(lower) <= part && part <= Integer.parseInt(upper);
    }

    /**
     * A Rule is invalid if length equals 0
     *
     * @return
     */
    public boolean isInvalid() {
        return getLength() == 0;
    }
    
}
