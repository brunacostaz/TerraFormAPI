package br.com.fiap.terraform.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "consultReactionResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultReactionResponse {

    private String compoundCode;
    private String displayName;
    private String equation;
    private String purpose;

    @XmlElementWrapper(name = "reagents")
    @XmlElement(name = "reagent")
    private List<SoapReagent> reagents = new ArrayList<>();

    @XmlElementWrapper(name = "availableTargets")
    @XmlElement(name = "target")
    private List<String> availableTargets = new ArrayList<>();

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<SoapReagent> getReagents() {
        return reagents;
    }

    public void setReagents(List<SoapReagent> reagents) {
        this.reagents = reagents;
    }

    public List<String> getAvailableTargets() {
        return availableTargets;
    }

    public void setAvailableTargets(List<String> availableTargets) {
        this.availableTargets = availableTargets;
    }
}
