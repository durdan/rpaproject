package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.FileToFtp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FileToFtp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileToFtpRepository extends JpaRepository<FileToFtp, Long> {

}
