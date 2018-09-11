import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/organization">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.organization" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/org-email-config">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.orgEmailConfig" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/client">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.client" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/client-email-domain">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.clientEmailDomain" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/input-template">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.inputTemplate" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/output-template">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.outputTemplate" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/template-fields">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.templateFields" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/email-messages">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.emailMessages" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/email-attachment">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.emailAttachment" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/email-processing-error">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.emailProcessingError" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/upload-files">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.uploadFiles" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/file-for-ocr-processing">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.fileForOcrProcessing" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/transaction">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.transaction" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/ocr-processing-error">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.ocrProcessingError" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/client-data-ocr">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.clientDataOcr" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/file-to-ftp">
      <FontAwesomeIcon icon="asterisk" />&nbsp;<Translate contentKey="global.menu.entities.fileToFtp" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
