package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.EmailAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmailAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailAttachmentRepository extends JpaRepository<EmailAttachment, Long> {

}
