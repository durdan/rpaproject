package com.edatablock.rpa.service;

import com.edatablock.rpa.service.dto.TransactionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Transaction.
 */
public interface TransactionService {

    /**
     * Save a transaction.
     *
     * @param transactionDTO the entity to save
     * @return the persisted entity
     */
    TransactionDTO save(TransactionDTO transactionDTO);

    /**
     * Get all the transactions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TransactionDTO> findAll(Pageable pageable);
    /**
     * Get all the TransactionDTO where OcrProcessingError is null.
     *
     * @return the list of entities
     */
    List<TransactionDTO> findAllWhereOcrProcessingErrorIsNull();


    /**
     * Get the "id" transaction.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TransactionDTO> findOne(Long id);

    /**
     * Delete the "id" transaction.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
