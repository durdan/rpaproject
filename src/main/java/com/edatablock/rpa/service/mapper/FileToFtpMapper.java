package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.FileToFtpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FileToFtp and its DTO FileToFtpDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientDataOcrMapper.class})
public interface FileToFtpMapper extends EntityMapper<FileToFtpDTO, FileToFtp> {

    @Mapping(source = "clientDataOcr.id", target = "clientDataOcrId")
    @Mapping(source = "clientDataOcr.clientEmailAddress", target = "clientDataOcrClientEmailAddress")
    FileToFtpDTO toDto(FileToFtp fileToFtp);

    @Mapping(source = "clientDataOcrId", target = "clientDataOcr")
    FileToFtp toEntity(FileToFtpDTO fileToFtpDTO);

    default FileToFtp fromId(Long id) {
        if (id == null) {
            return null;
        }
        FileToFtp fileToFtp = new FileToFtp();
        fileToFtp.setId(id);
        return fileToFtp;
    }
}
