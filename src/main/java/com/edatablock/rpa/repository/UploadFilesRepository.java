package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.UploadFiles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UploadFiles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UploadFilesRepository extends JpaRepository<UploadFiles, Long> {

}
