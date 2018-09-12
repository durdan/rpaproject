package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.OrgEmailConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrgEmailConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgEmailConfigRepository extends JpaRepository<OrgEmailConfig, Long> {

}
