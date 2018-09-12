package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.UploadFilesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UploadFiles and its DTO UploadFilesDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface UploadFilesMapper extends EntityMapper<UploadFilesDTO, UploadFiles> {

    @Mapping(source = "client.id", target = "clientId")
    UploadFilesDTO toDto(UploadFiles uploadFiles);

    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "fileForOCRProcessing", ignore = true)
    UploadFiles toEntity(UploadFilesDTO uploadFilesDTO);

    default UploadFiles fromId(Long id) {
        if (id == null) {
            return null;
        }
        UploadFiles uploadFiles = new UploadFiles();
        uploadFiles.setId(id);
        return uploadFiles;
    }
}
