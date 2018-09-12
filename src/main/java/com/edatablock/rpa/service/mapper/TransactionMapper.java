package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.TransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Transaction and its DTO TransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {FileForOCRProcessingMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(source = "fileForOCRProcessing.id", target = "fileForOCRProcessingId")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(source = "fileForOCRProcessingId", target = "fileForOCRProcessing")
    @Mapping(target = "ocrProcessingError", ignore = true)
    @Mapping(target = "clientDataOcrs", ignore = true)
    Transaction toEntity(TransactionDTO transactionDTO);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
