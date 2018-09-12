package com.edatablock.rpa.repository;

import com.edatablock.rpa.domain.ClientEmailDomain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClientEmailDomain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientEmailDomainRepository extends JpaRepository<ClientEmailDomain, Long> {

}
