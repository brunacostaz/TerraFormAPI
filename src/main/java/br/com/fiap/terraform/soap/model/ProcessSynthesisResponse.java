package br.com.fiap.terraform.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "processSynthesisResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessSynthesisResponse {

    private boolean success;
    private Long greenhouseId;
    private String compoundCode;
    private int units;
    private BigDecimal producedPercentage;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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

    public BigDecimal getProducedPercentage() {
        return producedPercentage;
    }

    public void setProducedPercentage(BigDecimal producedPercentage) {
        this.producedPercentage = producedPercentage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

