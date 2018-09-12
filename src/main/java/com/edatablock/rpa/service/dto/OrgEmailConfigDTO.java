package com.edatablock.rpa.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OrgEmailConfig entity.
 */
public class OrgEmailConfigDTO implements Serializable {

    private Long id;

    private String emailServerHost;

    private Integer emailServerPort;

    private String emailServerUserId;

    private String emailServerPassword;

    private Instant createDate;

    private String createdBy;

    private Instant updateDate;

    private String updatedBy;

    private Long orgNameId;

    private String orgNameOrgName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailServerHost() {
        return emailServerHost;
    }

    public void setEmailServerHost(String emailServerHost) {
        this.emailServerHost = emailServerHost;
    }

    public Integer getEmailServerPort() {
        return emailServerPort;
    }

    public void setEmailServerPort(Integer emailServerPort) {
        this.emailServerPort = emailServerPort;
    }

    public String getEmailServerUserId() {
        return emailServerUserId;
    }

    public void setEmailServerUserId(String emailServerUserId) {
        this.emailServerUserId = emailServerUserId;
    }

    public String getEmailServerPassword() {
        return emailServerPassword;
    }

    public void setEmailServerPassword(String emailServerPassword) {
        this.emailServerPassword = emailServerPassword;
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

    public Long getOrgNameId() {
        return orgNameId;
    }

    public void setOrgNameId(Long organizationId) {
        this.orgNameId = organizationId;
    }

    public String getOrgNameOrgName() {
        return orgNameOrgName;
    }

    public void setOrgNameOrgName(String organizationOrgName) {
        this.orgNameOrgName = organizationOrgName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrgEmailConfigDTO orgEmailConfigDTO = (OrgEmailConfigDTO) o;
        if (orgEmailConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orgEmailConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrgEmailConfigDTO{" +
            "id=" + getId() +
            ", emailServerHost='" + getEmailServerHost() + "'" +
            ", emailServerPort=" + getEmailServerPort() +
            ", emailServerUserId='" + getEmailServerUserId() + "'" +
            ", emailServerPassword='" + getEmailServerPassword() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", orgName=" + getOrgNameId() +
            ", orgName='" + getOrgNameOrgName() + "'" +
            "}";
    }
}
