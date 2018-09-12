package com.edatablock.rpa.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UploadFiles entity.
 */
public class UploadFilesDTO implements Serializable {

    private Long id;

    private String clientEmailAddress;

    @NotNull
    private String fileName;

    @NotNull
    private String fileExtension;

    private String uploadBy;

    private Instant uploadDateTime;

    private String uploadLocation;

    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public Instant getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(Instant uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public String getUploadLocation() {
        return uploadLocation;
    }

    public void setUploadLocation(String uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UploadFilesDTO uploadFilesDTO = (UploadFilesDTO) o;
        if (uploadFilesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uploadFilesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UploadFilesDTO{" +
            "id=" + getId() +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", uploadBy='" + getUploadBy() + "'" +
            ", uploadDateTime='" + getUploadDateTime() + "'" +
            ", uploadLocation='" + getUploadLocation() + "'" +
            ", client=" + getClientId() +
            "}";
    }
}
