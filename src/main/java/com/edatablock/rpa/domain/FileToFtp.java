package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FileToFtp.
 */
@Entity
@Table(name = "file_to_ftp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileToFtp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "client_email_address")
    private String clientEmailAddress;

    @Column(name = "status")
    private String status;

    @Column(name = "file_type")
    private String fileType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ClientDataOcr clientDataOcr;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public FileToFtp messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public FileToFtp clientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
        return this;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getStatus() {
        return status;
    }

    public FileToFtp status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileType() {
        return fileType;
    }

    public FileToFtp fileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public ClientDataOcr getClientDataOcr() {
        return clientDataOcr;
    }

    public FileToFtp clientDataOcr(ClientDataOcr clientDataOcr) {
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
        FileToFtp fileToFtp = (FileToFtp) o;
        if (fileToFtp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileToFtp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileToFtp{" +
            "id=" + getId() +
            ", messageId='" + getMessageId() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", status='" + getStatus() + "'" +
            ", fileType='" + getFileType() + "'" +
            "}";
    }
}
