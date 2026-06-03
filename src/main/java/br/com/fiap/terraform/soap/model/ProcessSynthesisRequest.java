package br.com.fiap.terraform.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "processSynthesisRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessSynthesisRequest {

    private Long greenhouseId;
    private String compoundCode;
    private int units;

    public Long getGreenhouseId() {
        return greenhouseId;
    }

    public void setGreenhouseId(Long greenhouseId) {
        this.greenhouseId = greenhouseId;
    }

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}

