package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.OcrProcessingErrorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OcrProcessingError and its DTO OcrProcessingErrorDTO.
 */
@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface OcrProcessingErrorMapper extends EntityMapper<OcrProcessingErrorDTO, OcrProcessingError> {

    @Mapping(source = "transaction.id", target = "transactionId")
    OcrProcessingErrorDTO toDto(OcrProcessingError ocrProcessingError);

    @Mapping(source = "transactionId", target = "transaction")
    OcrProcessingError toEntity(OcrProcessingErrorDTO ocrProcessingErrorDTO);

    default OcrProcessingError fromId(Long id) {
        if (id == null) {
            return null;
        }
        OcrProcessingError ocrProcessingError = new OcrProcessingError();
        ocrProcessingError.setId(id);
        return ocrProcessingError;
    }
}
