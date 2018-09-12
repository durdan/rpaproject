package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A OutputTemplate.
 */
@Entity
@Table(name = "output_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OutputTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "output_template_name")
    private String outputTemplateName;

    @NotNull
    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @Column(name = "position")
    private String position;

    @Column(name = "field_validation_require")
    private Integer fieldValidationRequire;

    @Column(name = "field_validation_rule")
    private String fieldValidationRule;

    @ManyToOne
    @JsonIgnoreProperties("outputTemplates")
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties("outputTemplates")
    private ClientDataOcr clientDataOcr;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutputTemplateName() {
        return outputTemplateName;
    }

    public OutputTemplate outputTemplateName(String outputTemplateName) {
        this.outputTemplateName = outputTemplateName;
        return this;
    }

    public void setOutputTemplateName(String outputTemplateName) {
        this.outputTemplateName = outputTemplateName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public OutputTemplate fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPosition() {
        return position;
    }

    public OutputTemplate position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getFieldValidationRequire() {
        return fieldValidationRequire;
    }

    public OutputTemplate fieldValidationRequire(Integer fieldValidationRequire) {
        this.fieldValidationRequire = fieldValidationRequire;
        return this;
    }

    public void setFieldValidationRequire(Integer fieldValidationRequire) {
        this.fieldValidationRequire = fieldValidationRequire;
    }

    public String getFieldValidationRule() {
        return fieldValidationRule;
    }

    public OutputTemplate fieldValidationRule(String fieldValidationRule) {
        this.fieldValidationRule = fieldValidationRule;
        return this;
    }

    public void setFieldValidationRule(String fieldValidationRule) {
        this.fieldValidationRule = fieldValidationRule;
    }

    public Client getClient() {
        return client;
    }

    public OutputTemplate client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientDataOcr getClientDataOcr() {
        return clientDataOcr;
    }

    public OutputTemplate clientDataOcr(ClientDataOcr clientDataOcr) {
        this.clientDataOcr = clientDataOcr;
        return this;
    }

    public void setClientDataOcr(ClientDataOcr clientDataOcr) {
        this.clientDataOcr = clientDataOcr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OutputTemplate outputTemplate = (OutputTemplate) o;
        if (outputTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), outputTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OutputTemplate{" +
            "id=" + getId() +
            ", outputTemplateName='" + getOutputTemplateName() + "'" +
            ", fieldName='" + getFieldName() + "'" +
            ", position='" + getPosition() + "'" +
            ", fieldValidationRequire=" + getFieldValidationRequire() +
            ", fieldValidationRule='" + getFieldValidationRule() + "'" +
            "}";
    }
}
