import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './organization.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrganizationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IOrganizationUpdateState {
  isNew: boolean;
}

export class OrganizationUpdate extends React.Component<IOrganizationUpdateProps, IOrganizationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    values.createDate = new Date(values.createDate);
    values.updateDate = new Date(values.updateDate);

    if (errors.length === 0) {
      const { organizationEntity } = this.props;
      const entity = {
        ...organizationEntity,
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
    this.props.history.push('/entity/organization');
  };

  render() {
    const { organizationEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.organization.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.organization.home.createOrEditLabel">Create or edit a Organization</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : organizationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="organization-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="orgNameLabel" for="orgName">
                    <Translate contentKey="rpaprojectApp.organization.orgName">Org Name</Translate>
                  </Label>
                  <AvField
                    id="organization-orgName"
                    type="text"
                    name="orgName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="rpaprojectApp.organization.description">Description</Translate>
                  </Label>
                  <AvField id="organization-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="orgAddressLabel" for="orgAddress">
                    <Translate contentKey="rpaprojectApp.organization.orgAddress">Org Address</Translate>
                  </Label>
                  <AvField id="organization-orgAddress" type="text" name="orgAddress" />
                </AvGroup>
                <AvGroup>
                  <Label id="orgEmailLabel" for="orgEmail">
                    <Translate contentKey="rpaprojectApp.organization.orgEmail">Org Email</Translate>
                  </Label>
                  <AvField
                    id="organization-orgEmail"
                    type="text"
                    name="orgEmail"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="isActiveLabel" for="isActive">
                    <Translate contentKey="rpaprojectApp.organization.isActive">Is Active</Translate>
                  </Label>
                  <AvField id="organization-isActive" type="number" className="form-control" name="isActive" />
                </AvGroup>
                <AvGroup>
                  <Label id="createDateLabel" for="createDate">
                    <Translate contentKey="rpaprojectApp.organization.createDate">Create Date</Translate>
                  </Label>
                  <AvInput
                    id="organization-createDate"
                    type="datetime-local"
                    className="form-control"
                    name="createDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.organizationEntity.createDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="createdByLabel" for="createdBy">
                    <Translate contentKey="rpaprojectApp.organization.createdBy">Created By</Translate>
                  </Label>
                  <AvField id="organization-createdBy" type="text" name="createdBy" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateDateLabel" for="updateDate">
                    <Translate contentKey="rpaprojectApp.organization.updateDate">Update Date</Translate>
                  </Label>
                  <AvInput
                    id="organization-updateDate"
                    type="datetime-local"
                    className="form-control"
                    name="updateDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.organizationEntity.updateDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedByLabel" for="updatedBy">
                    <Translate contentKey="rpaprojectApp.organization.updatedBy">Updated By</Translate>
                  </Label>
                  <AvField id="organization-updatedBy" type="text" name="updatedBy" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/organization" replace color="info">
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
  organizationEntity: storeState.organization.entity,
  loading: storeState.organization.loading,
  updating: storeState.organization.updating
});

const mapDispatchToProps = {
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
)(OrganizationUpdate);
