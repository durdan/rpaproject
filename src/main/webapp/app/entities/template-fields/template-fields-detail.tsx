import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './template-fields.reducer';
import { ITemplateFields } from 'app/shared/model/template-fields.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITemplateFieldsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class TemplateFieldsDetail extends React.Component<ITemplateFieldsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { templateFieldsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.templateFields.detail.title">TemplateFields</Translate> [<b>{templateFieldsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fieldName">
                <Translate contentKey="rpaprojectApp.templateFields.fieldName">Field Name</Translate>
              </span>
            </dt>
            <dd>{templateFieldsEntity.fieldName}</dd>
            <dt>
              <span id="fieldZoneMinX">
                <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMinX">Field Zone Min X</Translate>
              </span>
            </dt>
            <dd>{templateFieldsEntity.fieldZoneMinX}</dd>
            <dt>
              <span id="fieldZoneMinY">
                <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMinY">Field Zone Min Y</Translate>
              </span>
            </dt>
            <dd>{templateFieldsEntity.fieldZoneMinY}</dd>
            <dt>
              <span id="fieldZoneMaxX">
                <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMaxX">Field Zone Max X</Translate>
              </span>
            </dt>
            <dd>{templateFieldsEntity.fieldZoneMaxX}</dd>
            <dt>
              <span id="fieldZoneMaxY">
                <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMaxY">Field Zone Max Y</Translate>
              </span>
            </dt>
            <dd>{templateFieldsEntity.fieldZoneMaxY}</dd>
            <dt>
              <span id="fieldValidationRequire">
                <Translate contentKey="rpaprojectApp.templateFields.fieldValidationRequire">Field Validation Require</Translate>
              </span>
            </dt>
            <dd>{templateFieldsEntity.fieldValidationRequire}</dd>
            <dt>
              <span id="fieldValidationRule">
                <Translate contentKey="rpaprojectApp.templateFields.fieldValidationRule">Field Validation Rule</Translate>
              </span>
            </dt>
            <dd>{templateFieldsEntity.fieldValidationRule}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.templateFields.inputTemplate">Input Template</Translate>
            </dt>
            <dd>{templateFieldsEntity.inputTemplateId ? templateFieldsEntity.inputTemplateId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/template-fields" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/template-fields/${templateFieldsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ templateFields }: IRootState) => ({
  templateFieldsEntity: templateFields.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TemplateFieldsDetail);
