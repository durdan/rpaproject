package com.edatablock.rpa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OutputTemplate entity.
 */
public class OutputTemplateDTO implements Serializable {

    private Long id;

    private String outputTemplateName;

    @NotNull
    private String fieldName;

    private String position;

    private Integer fieldValidationRequire;

    private String fieldValidationRule;

    private Long clientId;

    private Long clientDataOcrId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutputTemplateName() {
        return outputTemplateName;
    }

    public void setOutputTemplateName(String outputTemplateName) {
        this.outputTemplateName = outputTemplateName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getFieldValidationRequire() {
        return fieldValidationRequire;
    }

    public void setFieldValidationRequire(Integer fieldValidationRequire) {
        this.fieldValidationRequire = fieldValidationRequire;
    }

    public String getFieldValidationRule() {
        return fieldValidationRule;
    }

    public void setFieldValidationRule(String fieldValidationRule) {
        this.fieldValidationRule = fieldValidationRule;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getClientDataOcrId() {
        return clientDataOcrId;
    }

    public void setClientDataOcrId(Long clientDataOcrId) {
        this.clientDataOcrId = clientDataOcrId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OutputTemplateDTO outputTemplateDTO = (OutputTemplateDTO) o;
        if (outputTemplateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), outputTemplateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OutputTemplateDTO{" +
            "id=" + getId() +
            ", outputTemplateName='" + getOutputTemplateName() + "'" +
            ", fieldName='" + getFieldName() + "'" +
            ", position='" + getPosition() + "'" +
            ", fieldValidationRequire=" + getFieldValidationRequire() +
            ", fieldValidationRule='" + getFieldValidationRule() + "'" +
            ", client=" + getClientId() +
            ", clientDataOcr=" + getClientDataOcrId() +
            "}";
    }
}
