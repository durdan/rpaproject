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
import { IClientDataOcr } from 'app/shared/model/client-data-ocr.model';
import { getEntities as getClientDataOcrs } from 'app/entities/client-data-ocr/client-data-ocr.reducer';
import { getEntity, updateEntity, createEntity, reset } from './output-template.reducer';
import { IOutputTemplate } from 'app/shared/model/output-template.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOutputTemplateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IOutputTemplateUpdateState {
  isNew: boolean;
  clientId: number;
  clientDataOcrId: number;
}

export class OutputTemplateUpdate extends React.Component<IOutputTemplateUpdateProps, IOutputTemplateUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      clientId: 0,
      clientDataOcrId: 0,
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
    this.props.getClientDataOcrs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { outputTemplateEntity } = this.props;
      const entity = {
        ...outputTemplateEntity,
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
    this.props.history.push('/entity/output-template');
  };

  render() {
    const { outputTemplateEntity, clients, clientDataOcrs, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.outputTemplate.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.outputTemplate.home.createOrEditLabel">Create or edit a OutputTemplate</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : outputTemplateEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="output-template-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="outputTemplateNameLabel" for="outputTemplateName">
                    <Translate contentKey="rpaprojectApp.outputTemplate.outputTemplateName">Output Template Name</Translate>
                  </Label>
                  <AvField id="output-template-outputTemplateName" type="text" name="outputTemplateName" />
                </AvGroup>
                <AvGroup>
                  <Label id="fieldNameLabel" for="fieldName">
                    <Translate contentKey="rpaprojectApp.outputTemplate.fieldName">Field Name</Translate>
                  </Label>
                  <AvField
                    id="output-template-fieldName"
                    type="text"
                    name="fieldName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="positionLabel" for="position">
                    <Translate contentKey="rpaprojectApp.outputTemplate.position">Position</Translate>
                  </Label>
                  <AvField id="output-template-position" type="text" name="position" />
                </AvGroup>
                <AvGroup>
                  <Label id="fieldValidationRequireLabel" for="fieldValidationRequire">
                    <Translate contentKey="rpaprojectApp.outputTemplate.fieldValidationRequire">Field Validation Require</Translate>
                  </Label>
                  <AvField
                    id="output-template-fieldValidationRequire"
                    type="number"
                    className="form-control"
                    name="fieldValidationRequire"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fieldValidationRuleLabel" for="fieldValidationRule">
                    <Translate contentKey="rpaprojectApp.outputTemplate.fieldValidationRule">Field Validation Rule</Translate>
                  </Label>
                  <AvField id="output-template-fieldValidationRule" type="text" name="fieldValidationRule" />
                </AvGroup>
                <AvGroup>
                  <Label for="client.id">
                    <Translate contentKey="rpaprojectApp.outputTemplate.client">Client</Translate>
                  </Label>
                  <AvInput id="output-template-client" type="select" className="form-control" name="clientId">
                    <option value="" key="0" />
                    {clients
                      ? clients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="clientDataOcr.id">
                    <Translate contentKey="rpaprojectApp.outputTemplate.clientDataOcr">Client Data Ocr</Translate>
                  </Label>
                  <AvInput id="output-template-clientDataOcr" type="select" className="form-control" name="clientDataOcrId">
                    <option value="" key="0" />
                    {clientDataOcrs
                      ? clientDataOcrs.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/output-template" replace color="info">
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
  clientDataOcrs: storeState.clientDataOcr.entities,
  outputTemplateEntity: storeState.outputTemplate.entity,
  loading: storeState.outputTemplate.loading,
  updating: storeState.outputTemplate.updating
});

const mapDispatchToProps = {
  getClients,
  getClientDataOcrs,
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
)(OutputTemplateUpdate);
