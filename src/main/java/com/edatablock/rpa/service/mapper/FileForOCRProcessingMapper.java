package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.FileForOCRProcessingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FileForOCRProcessing and its DTO FileForOCRProcessingDTO.
 */
@Mapper(componentModel = "spring", uses = {EmailAttachmentMapper.class, UploadFilesMapper.class})
public interface FileForOCRProcessingMapper extends EntityMapper<FileForOCRProcessingDTO, FileForOCRProcessing> {

    @Mapping(source = "emailAttachment.id", target = "emailAttachmentId")
    @Mapping(source = "uploadFiles.id", target = "uploadFilesId")
    FileForOCRProcessingDTO toDto(FileForOCRProcessing fileForOCRProcessing);

    @Mapping(source = "emailAttachmentId", target = "emailAttachment")
    @Mapping(source = "uploadFilesId", target = "uploadFiles")
    @Mapping(target = "transaction", ignore = true)
    FileForOCRProcessing toEntity(FileForOCRProcessingDTO fileForOCRProcessingDTO);

    default FileForOCRProcessing fromId(Long id) {
        if (id == null) {
            return null;
        }
        FileForOCRProcessing fileForOCRProcessing = new FileForOCRProcessing();
        fileForOCRProcessing.setId(id);
        return fileForOCRProcessing;
    }
}
