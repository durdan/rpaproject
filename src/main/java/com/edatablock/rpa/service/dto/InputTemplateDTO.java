package com.edatablock.rpa.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InputTemplate entity.
 */
public class InputTemplateDTO implements Serializable {

    private Long id;

    @NotNull
    private String templateName;

    private String templateDescription;

    private Integer isActive;

    private Instant createDate;

    private String createdBy;

    private Instant updateDate;

    private String updatedBy;

    private Long clientId;

    private String clientClientEmailAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientClientEmailAddress() {
        return clientClientEmailAddress;
    }

    public void setClientClientEmailAddress(String clientClientEmailAddress) {
        this.clientClientEmailAddress = clientClientEmailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InputTemplateDTO inputTemplateDTO = (InputTemplateDTO) o;
        if (inputTemplateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inputTemplateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InputTemplateDTO{" +
            "id=" + getId() +
            ", templateName='" + getTemplateName() + "'" +
            ", templateDescription='" + getTemplateDescription() + "'" +
            ", isActive=" + getIsActive() +
            ", createDate='" + getCreateDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", client=" + getClientId() +
            ", client='" + getClientClientEmailAddress() + "'" +
            "}";
    }
}
