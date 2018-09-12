package com.edatablock.rpa.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FileToFtp entity.
 */
public class FileToFtpDTO implements Serializable {

    private Long id;

    private String messageId;

    private String clientEmailAddress;

    private String status;

    private String fileType;

    private Long clientDataOcrId;

    private String clientDataOcrClientEmailAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getClientDataOcrId() {
        return clientDataOcrId;
    }

    public void setClientDataOcrId(Long clientDataOcrId) {
        this.clientDataOcrId = clientDataOcrId;
    }

    public String getClientDataOcrClientEmailAddress() {
        return clientDataOcrClientEmailAddress;
    }

    public void setClientDataOcrClientEmailAddress(String clientDataOcrClientEmailAddress) {
        this.clientDataOcrClientEmailAddress = clientDataOcrClientEmailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileToFtpDTO fileToFtpDTO = (FileToFtpDTO) o;
        if (fileToFtpDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileToFtpDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileToFtpDTO{" +
            "id=" + getId() +
            ", messageId='" + getMessageId() + "'" +
            ", clientEmailAddress='" + getClientEmailAddress() + "'" +
            ", status='" + getStatus() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", clientDataOcr=" + getClientDataOcrId() +
            ", clientDataOcr='" + getClientDataOcrClientEmailAddress() + "'" +
            "}";
    }
}
