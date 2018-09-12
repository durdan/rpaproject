import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, reset } from './input-template.reducer';
import { IInputTemplate } from 'app/shared/model/input-template.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInputTemplateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IInputTemplateUpdateState {
  isNew: boolean;
  clientId: number;
}

export class InputTemplateUpdate extends React.Component<IInputTemplateUpdateProps, IInputTemplateUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      clientId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getClients();
  }

  saveEntity = (event, errors, values) => {
    values.createDate = new Date(values.createDate);
    values.updateDate = new Date(values.updateDate);

    if (errors.length === 0) {
      const { inputTemplateEntity } = this.props;
      const entity = {
        ...inputTemplateEntity,
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
    this.props.history.push('/entity/input-template');
  };

  render() {
    const { inputTemplateEntity, clients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.inputTemplate.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.inputTemplate.home.createOrEditLabel">Create or edit a InputTemplate</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : inputTemplateEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="input-template-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="templateNameLabel" for="templateName">
                    <Translate contentKey="rpaprojectApp.inputTemplate.templateName">Template Name</Translate>
                  </Label>
                  <AvField
                    id="input-template-templateName"
                    type="text"
                    name="templateName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="templateDescriptionLabel" for="templateDescription">
                    <Translate contentKey="rpaprojectApp.inputTemplate.templateDescription">Template Description</Translate>
                  </Label>
                  <AvField id="input-template-templateDescription" type="text" name="templateDescription" />
                </AvGroup>
                <AvGroup>
                  <Label id="isActiveLabel" for="isActive">
                    <Translate contentKey="rpaprojectApp.inputTemplate.isActive">Is Active</Translate>
                  </Label>
                  <AvField id="input-template-isActive" type="number" className="form-control" name="isActive" />
                </AvGroup>
                <AvGroup>
                  <Label id="createDateLabel" for="createDate">
                    <Translate contentKey="rpaprojectApp.inputTemplate.createDate">Create Date</Translate>
                  </Label>
                  <AvInput
                    id="input-template-createDate"
                    type="datetime-local"
                    className="form-control"
                    name="createDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.inputTemplateEntity.createDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="createdByLabel" for="createdBy">
                    <Translate contentKey="rpaprojectApp.inputTemplate.createdBy">Created By</Translate>
                  </Label>
                  <AvField id="input-template-createdBy" type="text" name="createdBy" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateDateLabel" for="updateDate">
                    <Translate contentKey="rpaprojectApp.inputTemplate.updateDate">Update Date</Translate>
                  </Label>
                  <AvInput
                    id="input-template-updateDate"
                    type="datetime-local"
                    className="form-control"
                    name="updateDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.inputTemplateEntity.updateDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedByLabel" for="updatedBy">
                    <Translate contentKey="rpaprojectApp.inputTemplate.updatedBy">Updated By</Translate>
                  </Label>
                  <AvField id="input-template-updatedBy" type="text" name="updatedBy" />
                </AvGroup>
                <AvGroup>
                  <Label for="client.clientEmailAddress">
                    <Translate contentKey="rpaprojectApp.inputTemplate.client">Client</Translate>
                  </Label>
                  <AvInput id="input-template-client" type="select" className="form-control" name="clientId">
                    <option value="" key="0" />
                    {clients
                      ? clients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.clientEmailAddress}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/input-template" replace color="info">
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
  clients: storeState.client.entities,
  inputTemplateEntity: storeState.inputTemplate.entity,
  loading: storeState.inputTemplate.loading,
  updating: storeState.inputTemplate.updating
});

const mapDispatchToProps = {
  getClients,
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
)(InputTemplateUpdate);
