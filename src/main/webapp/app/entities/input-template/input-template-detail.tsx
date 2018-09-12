import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './input-template.reducer';
import { IInputTemplate } from 'app/shared/model/input-template.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInputTemplateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class InputTemplateDetail extends React.Component<IInputTemplateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { inputTemplateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.inputTemplate.detail.title">InputTemplate</Translate> [<b>{inputTemplateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="templateName">
                <Translate contentKey="rpaprojectApp.inputTemplate.templateName">Template Name</Translate>
              </span>
            </dt>
            <dd>{inputTemplateEntity.templateName}</dd>
            <dt>
              <span id="templateDescription">
                <Translate contentKey="rpaprojectApp.inputTemplate.templateDescription">Template Description</Translate>
              </span>
            </dt>
            <dd>{inputTemplateEntity.templateDescription}</dd>
            <dt>
              <span id="isActive">
                <Translate contentKey="rpaprojectApp.inputTemplate.isActive">Is Active</Translate>
              </span>
            </dt>
            <dd>{inputTemplateEntity.isActive}</dd>
            <dt>
              <span id="createDate">
                <Translate contentKey="rpaprojectApp.inputTemplate.createDate">Create Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={inputTemplateEntity.createDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createdBy">
                <Translate contentKey="rpaprojectApp.inputTemplate.createdBy">Created By</Translate>
              </span>
            </dt>
            <dd>{inputTemplateEntity.createdBy}</dd>
            <dt>
              <span id="updateDate">
                <Translate contentKey="rpaprojectApp.inputTemplate.updateDate">Update Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={inputTemplateEntity.updateDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedBy">
                <Translate contentKey="rpaprojectApp.inputTemplate.updatedBy">Updated By</Translate>
              </span>
            </dt>
            <dd>{inputTemplateEntity.updatedBy}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.inputTemplate.client">Client</Translate>
            </dt>
            <dd>{inputTemplateEntity.clientClientEmailAddress ? inputTemplateEntity.clientClientEmailAddress : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/input-template" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/input-template/${inputTemplateEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ inputTemplate }: IRootState) => ({
  inputTemplateEntity: inputTemplate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(InputTemplateDetail);
