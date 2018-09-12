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
import { getEntity, updateEntity, createEntity, reset } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IClientUpdateState {
  isNew: boolean;
  orgNameId: number;
}

export class ClientUpdate extends React.Component<IClientUpdateProps, IClientUpdateState> {
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
      const { clientEntity } = this.props;
      const entity = {
        ...clientEntity,
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
    this.props.history.push('/entity/client');
  };

  render() {
    const { clientEntity, organizations, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.client.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.client.home.createOrEditLabel">Create or edit a Client</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : clientEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="client-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="clientNameLabel" for="clientName">
                    <Translate contentKey="rpaprojectApp.client.clientName">Client Name</Translate>
                  </Label>
                  <AvField
                    id="client-clientName"
                    type="text"
                    name="clientName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="rpaprojectApp.client.description">Description</Translate>
                  </Label>
                  <AvField id="client-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="clientAddressLabel" for="clientAddress">
                    <Translate contentKey="rpaprojectApp.client.clientAddress">Client Address</Translate>
                  </Label>
                  <AvField id="client-clientAddress" type="text" name="clientAddress" />
                </AvGroup>
                <AvGroup>
                  <Label id="clientEmailAddressLabel" for="clientEmailAddress">
                    <Translate contentKey="rpaprojectApp.client.clientEmailAddress">Client Email Address</Translate>
                  </Label>
                  <AvField
                    id="client-clientEmailAddress"
                    type="text"
                    name="clientEmailAddress"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="isActiveLabel" for="isActive">
                    <Translate contentKey="rpaprojectApp.client.isActive">Is Active</Translate>
                  </Label>
                  <AvField id="client-isActive" type="number" className="form-control" name="isActive" />
                </AvGroup>
                <AvGroup>
                  <Label id="createDateLabel" for="createDate">
                    <Translate contentKey="rpaprojectApp.client.createDate">Create Date</Translate>
                  </Label>
                  <AvInput
                    id="client-createDate"
                    type="datetime-local"
                    className="form-control"
                    name="createDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.clientEntity.createDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="createdByLabel" for="createdBy">
                    <Translate contentKey="rpaprojectApp.client.createdBy">Created By</Translate>
                  </Label>
                  <AvField id="client-createdBy" type="text" name="createdBy" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateDateLabel" for="updateDate">
                    <Translate contentKey="rpaprojectApp.client.updateDate">Update Date</Translate>
                  </Label>
                  <AvInput
                    id="client-updateDate"
                    type="datetime-local"
                    className="form-control"
                    name="updateDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.clientEntity.updateDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedByLabel" for="updatedBy">
                    <Translate contentKey="rpaprojectApp.client.updatedBy">Updated By</Translate>
                  </Label>
                  <AvField id="client-updatedBy" type="text" name="updatedBy" />
                </AvGroup>
                <AvGroup>
                  <Label for="orgName.orgName">
                    <Translate contentKey="rpaprojectApp.client.orgName">Org Name</Translate>
                  </Label>
                  <AvInput id="client-orgName" type="select" className="form-control" name="orgNameId">
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
                <Button tag={Link} id="cancel-save" to="/entity/client" replace color="info">
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
  clientEntity: storeState.client.entity,
  loading: storeState.client.loading,
  updating: storeState.client.updating
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
)(ClientUpdate);
