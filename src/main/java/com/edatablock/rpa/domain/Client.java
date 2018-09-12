package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "description")
    private String description;

    @Column(name = "client_address")
    private String clientAddress;

    @NotNull
    @Column(name = "client_email_address", nullable = false)
    private String clientEmailAddress;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OutputTemplate> outputTemplates = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private Organization orgName;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InputTemplate> inputTemplates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public Client clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescription() {
        return description;
    }

    public Client description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public Client clientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
        return this;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public Client clientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
        return this;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public Client isActive(Integer isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Client createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Client createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public Client updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Client updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<OutputTemplate> getOutputTemplates() {
        return outputTemplates;
    }

    public Client outputTemplates(Set<OutputTemplate> outputTemplates) {
        this.outputTemplates = outputTemplates;
        return this;
    }

    public Client addOutputTemplate(OutputTemplate outputTemplate) {
        this.outputTemplates.add(outputTemplate);
        outputTemplate.setClient(this);
        return this;
    }

    public Client removeOutputTemplate(OutputTemplate outputTemplate) {
        this.outputTemplates.remove(outputTemplate);
        outputTemplate.setClient(null);
        return this;
    }

    public void setOutputTemplates(Set<OutputTemplate> outputTemplates) {
        this.outputTemplates = outputTemplates;
    }

    public Organization getOrgName() {
        return orgName;
    }

    public Client orgName(Organization organization) {
        this.orgName = organization;
        return this;
    }

    public void setOrgName(Organization organization) {
        this.orgName = organization;
    }

    public Set<InputTemplate> getInputTemplates() {
        return inputTemplates;
    }

    public Client inputTemplates(Set<InputTemplate> inputTemplates) {
        this.inputTemplates = inputTemplates;
        return this;
    }

    public Client addInputTemplate(InputTemplate inputTemplate) {
        this.inputTemplates.add(inputTemplate);
        inputTemplate.setClient(this);
        return this;
    }

    public Client removeInputTemplate(InputTemplate inputTemplate) {
        this.inputTemplates.remove(inputTemplate);
        inputTemplate.setClient(null);
        return this;
    }

    public void setInputTemplates(Set<InputTemplate> inputTemplates) {
        this.inputTemplates = inputTemplates;
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
        Client client = (Client) o;
        if (client.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", description='" + getDescription() + "'" +
            ", clientAddress='" + getClientAddress() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", isActive=" + getIsActive() +
            ", createDate='" + getCreateDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
