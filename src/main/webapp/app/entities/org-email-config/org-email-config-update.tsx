import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './org-email-config.reducer';
import { IOrgEmailConfig } from 'app/shared/model/org-email-config.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrgEmailConfigUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IOrgEmailConfigUpdateState {
  isNew: boolean;
  orgNameId: number;
}

export class OrgEmailConfigUpdate extends React.Component<IOrgEmailConfigUpdateProps, IOrgEmailConfigUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      orgNameId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getOrganizations();
  }

  saveEntity = (event, errors, values) => {
    values.createDate = new Date(values.createDate);
    values.updateDate = new Date(values.updateDate);

    if (errors.length === 0) {
      const { orgEmailConfigEntity } = this.props;
      const entity = {
        ...orgEmailConfigEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/org-email-config');
  };

  render() {
    const { orgEmailConfigEntity, organizations, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.orgEmailConfig.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.orgEmailConfig.home.createOrEditLabel">Create or edit a OrgEmailConfig</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : orgEmailConfigEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="org-email-config-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="emailServerHostLabel" for="emailServerHost">
                    <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerHost">Email Server Host</Translate>
                  </Label>
                  <AvField id="org-email-config-emailServerHost" type="text" name="emailServerHost" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailServerPortLabel" for="emailServerPort">
                    <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerPort">Email Server Port</Translate>
                  </Label>
                  <AvField id="org-email-config-emailServerPort" type="number" className="form-control" name="emailServerPort" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailServerUserIdLabel" for="emailServerUserId">
                    <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerUserId">Email Server User Id</Translate>
                  </Label>
                  <AvField id="org-email-config-emailServerUserId" type="text" name="emailServerUserId" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailServerPasswordLabel" for="emailServerPassword">
                    <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerPassword">Email Server Password</Translate>
                  </Label>
                  <AvField id="org-email-config-emailServerPassword" type="text" name="emailServerPassword" />
                </AvGroup>
                <AvGroup>
                  <Label id="createDateLabel" for="createDate">
                    <Translate contentKey="rpaprojectApp.orgEmailConfig.createDate">Create Date</Translate>
                  </Label>
                  <AvInput
                    id="org-email-config-createDate"
                    type="datetime-local"
                    className="form-control"
                    name="createDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.orgEmailConfigEntity.createDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="createdByLabel" for="createdBy">
                    <Translate contentKey="rpaprojectApp.orgEmailConfig.createdBy">Created By</Translate>
                  </Label>
                  <AvField id="org-email-config-createdBy" type="text" name="createdBy" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateDateLabel" for="updateDate">
                    <Translate contentKey="rpaprojectApp.orgEmailConfig.updateDate">Update Date</Translate>
                  </Label>
                  <AvInput
                    id="org-email-config-updateDate"
                    type="datetime-local"
                    className="form-control"
                    name="updateDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.orgEmailConfigEntity.updateDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedByLabel" for="updatedBy">
                    <Translate contentKey="rpaprojectApp.orgEmailConfig.updatedBy">Updated By</Translate>
                  </Label>
                  <AvField id="org-email-config-updatedBy" type="text" name="updatedBy" />
                </AvGroup>
                <AvGroup>
                  <Label for="orgName.orgName">
                    <Translate contentKey="rpaprojectApp.orgEmailConfig.orgName">Org Name</Translate>
                  </Label>
                  <AvInput id="org-email-config-orgName" type="select" className="form-control" name="orgNameId">
                    <option value="" key="0" />
                    {organizations
                      ? organizations.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.orgName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/org-email-config" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  organizations: storeState.organization.entities,
  orgEmailConfigEntity: storeState.orgEmailConfig.entity,
  loading: storeState.orgEmailConfig.loading,
  updating: storeState.orgEmailConfig.updating
});

const mapDispatchToProps = {
  getOrganizations,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrgEmailConfigUpdate);
