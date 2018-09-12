package com.edatablock.rpa.service.mapper;

import com.edatablock.rpa.domain.*;
import com.edatablock.rpa.service.dto.OrgEmailConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrgEmailConfig and its DTO OrgEmailConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface OrgEmailConfigMapper extends EntityMapper<OrgEmailConfigDTO, OrgEmailConfig> {

    @Mapping(source = "orgName.id", target = "orgNameId")
    @Mapping(source = "orgName.orgName", target = "orgNameOrgName")
    OrgEmailConfigDTO toDto(OrgEmailConfig orgEmailConfig);

    @Mapping(source = "orgNameId", target = "orgName")
    OrgEmailConfig toEntity(OrgEmailConfigDTO orgEmailConfigDTO);

    default OrgEmailConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrgEmailConfig orgEmailConfig = new OrgEmailConfig();
        orgEmailConfig.setId(id);
        return orgEmailConfig;
    }
}
