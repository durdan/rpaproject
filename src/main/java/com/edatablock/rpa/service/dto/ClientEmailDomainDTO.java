package com.edatablock.rpa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClientEmailDomain entity.
 */
public class ClientEmailDomainDTO implements Serializable {

    private Long id;

    @NotNull
    private String emailDomain;

    private String description;

    private Integer isActive;

    private Long clientId;

    private String clientClientName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientClientName() {
        return clientClientName;
    }

    public void setClientClientName(String clientClientName) {
        this.clientClientName = clientClientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientEmailDomainDTO clientEmailDomainDTO = (ClientEmailDomainDTO) o;
        if (clientEmailDomainDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientEmailDomainDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientEmailDomainDTO{" +
            "id=" + getId() +
            ", emailDomain='" + getEmailDomain() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive=" + getIsActive() +
            ", client=" + getClientId() +
            ", client='" + getClientClientName() + "'" +
            "}";
    }
}
