import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IInputTemplate } from 'app/shared/model/input-template.model';
import { getEntities as getInputTemplates } from 'app/entities/input-template/input-template.reducer';
import { getEntity, updateEntity, createEntity, reset } from './template-fields.reducer';
import { ITemplateFields } from 'app/shared/model/template-fields.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITemplateFieldsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface ITemplateFieldsUpdateState {
  isNew: boolean;
  inputTemplateId: number;
}

export class TemplateFieldsUpdate extends React.Component<ITemplateFieldsUpdateProps, ITemplateFieldsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      inputTemplateId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getInputTemplates();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { templateFieldsEntity } = this.props;
      const entity = {
        ...templateFieldsEntity,
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
    this.props.history.push('/entity/template-fields');
  };

  render() {
    const { templateFieldsEntity, inputTemplates, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.templateFields.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.templateFields.home.createOrEditLabel">Create or edit a TemplateFields</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : templateFieldsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="template-fields-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="fieldNameLabel" for="fieldName">
                    <Translate contentKey="rpaprojectApp.templateFields.fieldName">Field Name</Translate>
                  </Label>
                  <AvField
                    id="template-fields-fieldName"
                    type="text"
                    name="fieldName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fieldZoneMinXLabel" for="fieldZoneMinX">
                    <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMinX">Field Zone Min X</Translate>
                  </Label>
                  <AvField
                    id="template-fields-fieldZoneMinX"
                    type="number"
                    className="form-control"
                    name="fieldZoneMinX"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fieldZoneMinYLabel" for="fieldZoneMinY">
                    <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMinY">Field Zone Min Y</Translate>
                  </Label>
                  <AvField
                    id="template-fields-fieldZoneMinY"
                    type="number"
                    className="form-control"
                    name="fieldZoneMinY"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fieldZoneMaxXLabel" for="fieldZoneMaxX">
                    <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMaxX">Field Zone Max X</Translate>
                  </Label>
                  <AvField
                    id="template-fields-fieldZoneMaxX"
                    type="number"
                    className="form-control"
                    name="fieldZoneMaxX"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fieldZoneMaxYLabel" for="fieldZoneMaxY">
                    <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMaxY">Field Zone Max Y</Translate>
                  </Label>
                  <AvField
                    id="template-fields-fieldZoneMaxY"
                    type="number"
                    className="form-control"
                    name="fieldZoneMaxY"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fieldValidationRequireLabel" for="fieldValidationRequire">
                    <Translate contentKey="rpaprojectApp.templateFields.fieldValidationRequire">Field Validation Require</Translate>
                  </Label>
                  <AvField
                    id="template-fields-fieldValidationRequire"
                    type="number"
                    className="form-control"
                    name="fieldValidationRequire"
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fieldValidationRuleLabel" for="fieldValidationRule">
                    <Translate contentKey="rpaprojectApp.templateFields.fieldValidationRule">Field Validation Rule</Translate>
                  </Label>
                  <AvField id="template-fields-fieldValidationRule" type="text" name="fieldValidationRule" />
                </AvGroup>
                <AvGroup>
                  <Label for="inputTemplate.id">
                    <Translate contentKey="rpaprojectApp.templateFields.inputTemplate">Input Template</Translate>
                  </Label>
                  <AvInput id="template-fields-inputTemplate" type="select" className="form-control" name="inputTemplateId">
                    <option value="" key="0" />
                    {inputTemplates
                      ? inputTemplates.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/template-fields" replace color="info">
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
  inputTemplates: storeState.inputTemplate.entities,
  templateFieldsEntity: storeState.templateFields.entity,
  loading: storeState.templateFields.loading,
  updating: storeState.templateFields.updating
});

const mapDispatchToProps = {
  getInputTemplates,
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
)(TemplateFieldsUpdate);
