package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ClientEmailDomain.
 */
@Entity
@Table(name = "client_email_domain")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClientEmailDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email_domain", nullable = false)
    private String emailDomain;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Integer isActive;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public ClientEmailDomain emailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
        return this;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public String getDescription() {
        return description;
    }

    public ClientEmailDomain description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public ClientEmailDomain isActive(Integer isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Client getClient() {
        return client;
    }

    public ClientEmailDomain client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
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
        ClientEmailDomain clientEmailDomain = (ClientEmailDomain) o;
        if (clientEmailDomain.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientEmailDomain.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientEmailDomain{" +
            "id=" + getId() +
            ", emailDomain='" + getEmailDomain() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive=" + getIsActive() +
            "}";
    }
}
