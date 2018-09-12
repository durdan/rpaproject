package com.edatablock.rpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A UploadFiles.
 */
@Entity
@Table(name = "upload_files")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UploadFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_email_address")
    private String clientEmailAddress;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "file_extension", nullable = false)
    private String fileExtension;

    @Column(name = "upload_by")
    private String uploadBy;

    @Column(name = "upload_date_time")
    private Instant uploadDateTime;

    @Column(name = "upload_location")
    private String uploadLocation;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Client client;

    @OneToOne(mappedBy = "uploadFiles")
    @JsonIgnore
    private FileForOCRProcessing fileForOCRProcessing;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public UploadFiles clientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
        return this;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public UploadFiles fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public UploadFiles fileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        return this;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public UploadFiles uploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
        return this;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public Instant getUploadDateTime() {
        return uploadDateTime;
    }

    public UploadFiles uploadDateTime(Instant uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
        return this;
    }

    public void setUploadDateTime(Instant uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public String getUploadLocation() {
        return uploadLocation;
    }

    public UploadFiles uploadLocation(String uploadLocation) {
        this.uploadLocation = uploadLocation;
        return this;
    }

    public void setUploadLocation(String uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    public Client getClient() {
        return client;
    }

    public UploadFiles client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public FileForOCRProcessing getFileForOCRProcessing() {
        return fileForOCRProcessing;
    }

    public UploadFiles fileForOCRProcessing(FileForOCRProcessing fileForOCRProcessing) {
        this.fileForOCRProcessing = fileForOCRProcessing;
        return this;
    }

    public void setFileForOCRProcessing(FileForOCRProcessing fileForOCRProcessing) {
        this.fileForOCRProcessing = fileForOCRProcessing;
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
        UploadFiles uploadFiles = (UploadFiles) o;
        if (uploadFiles.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uploadFiles.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UploadFiles{" +
            "id=" + getId() +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", uploadBy='" + getUploadBy() + "'" +
            ", uploadDateTime='" + getUploadDateTime() + "'" +
            ", uploadLocation='" + getUploadLocation() + "'" +
            "}";
    }
}
