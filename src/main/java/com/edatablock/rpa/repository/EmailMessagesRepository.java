package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.EmailMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmailMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailMessagesRepository extends JpaRepository<EmailMessages, Long> {

}
