import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './output-template.reducer';
import { IOutputTemplate } from 'app/shared/model/output-template.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOutputTemplateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class OutputTemplateDetail extends React.Component<IOutputTemplateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { outputTemplateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.outputTemplate.detail.title">OutputTemplate</Translate> [<b>{outputTemplateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="outputTemplateName">
                <Translate contentKey="rpaprojectApp.outputTemplate.outputTemplateName">Output Template Name</Translate>
              </span>
            </dt>
            <dd>{outputTemplateEntity.outputTemplateName}</dd>
            <dt>
              <span id="fieldName">
                <Translate contentKey="rpaprojectApp.outputTemplate.fieldName">Field Name</Translate>
              </span>
            </dt>
            <dd>{outputTemplateEntity.fieldName}</dd>
            <dt>
              <span id="position">
                <Translate contentKey="rpaprojectApp.outputTemplate.position">Position</Translate>
              </span>
            </dt>
            <dd>{outputTemplateEntity.position}</dd>
            <dt>
              <span id="fieldValidationRequire">
                <Translate contentKey="rpaprojectApp.outputTemplate.fieldValidationRequire">Field Validation Require</Translate>
              </span>
            </dt>
            <dd>{outputTemplateEntity.fieldValidationRequire}</dd>
            <dt>
              <span id="fieldValidationRule">
                <Translate contentKey="rpaprojectApp.outputTemplate.fieldValidationRule">Field Validation Rule</Translate>
              </span>
            </dt>
            <dd>{outputTemplateEntity.fieldValidationRule}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.outputTemplate.client">Client</Translate>
            </dt>
            <dd>{outputTemplateEntity.clientId ? outputTemplateEntity.clientId : ''}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.outputTemplate.clientDataOcr">Client Data Ocr</Translate>
            </dt>
            <dd>{outputTemplateEntity.clientDataOcrId ? outputTemplateEntity.clientDataOcrId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/output-template" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/output-template/${outputTemplateEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ outputTemplate }: IRootState) => ({
  outputTemplateEntity: outputTemplate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OutputTemplateDetail);
