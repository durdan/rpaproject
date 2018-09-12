package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A OrgEmailConfig.
 */
@Entity
@Table(name = "org_email_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrgEmailConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_server_host")
    private String emailServerHost;

    @Column(name = "email_server_port")
    private Integer emailServerPort;

    @Column(name = "email_server_user_id")
    private String emailServerUserId;

    @Column(name = "email_server_password")
    private String emailServerPassword;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Organization orgName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailServerHost() {
        return emailServerHost;
    }

    public OrgEmailConfig emailServerHost(String emailServerHost) {
        this.emailServerHost = emailServerHost;
        return this;
    }

    public void setEmailServerHost(String emailServerHost) {
        this.emailServerHost = emailServerHost;
    }

    public Integer getEmailServerPort() {
        return emailServerPort;
    }

    public OrgEmailConfig emailServerPort(Integer emailServerPort) {
        this.emailServerPort = emailServerPort;
        return this;
    }

    public void setEmailServerPort(Integer emailServerPort) {
        this.emailServerPort = emailServerPort;
    }

    public String getEmailServerUserId() {
        return emailServerUserId;
    }

    public OrgEmailConfig emailServerUserId(String emailServerUserId) {
        this.emailServerUserId = emailServerUserId;
        return this;
    }

    public void setEmailServerUserId(String emailServerUserId) {
        this.emailServerUserId = emailServerUserId;
    }

    public String getEmailServerPassword() {
        return emailServerPassword;
    }

    public OrgEmailConfig emailServerPassword(String emailServerPassword) {
        this.emailServerPassword = emailServerPassword;
        return this;
    }

    public void setEmailServerPassword(String emailServerPassword) {
        this.emailServerPassword = emailServerPassword;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public OrgEmailConfig createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public OrgEmailConfig createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public OrgEmailConfig updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public OrgEmailConfig updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Organization getOrgName() {
        return orgName;
    }

    public OrgEmailConfig orgName(Organization organization) {
        this.orgName = organization;
        return this;
    }

    public void setOrgName(Organization organization) {
        this.orgName = organization;
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
        OrgEmailConfig orgEmailConfig = (OrgEmailConfig) o;
        if (orgEmailConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orgEmailConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrgEmailConfig{" +
            "id=" + getId() +
            ", emailServerHost='" + getEmailServerHost() + "'" +
            ", emailServerPort=" + getEmailServerPort() +
            ", emailServerUserId='" + getEmailServerUserId() + "'" +
            ", emailServerPassword='" + getEmailServerPassword() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
