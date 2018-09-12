import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class ClientDetail extends React.Component<IClientDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { clientEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.client.detail.title">Client</Translate> [<b>{clientEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="clientName">
                <Translate contentKey="rpaprojectApp.client.clientName">Client Name</Translate>
              </span>
            </dt>
            <dd>{clientEntity.clientName}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="rpaprojectApp.client.description">Description</Translate>
              </span>
            </dt>
            <dd>{clientEntity.description}</dd>
            <dt>
              <span id="clientAddress">
                <Translate contentKey="rpaprojectApp.client.clientAddress">Client Address</Translate>
              </span>
            </dt>
            <dd>{clientEntity.clientAddress}</dd>
            <dt>
              <span id="clientEmailAddress">
                <Translate contentKey="rpaprojectApp.client.clientEmailAddress">Client Email Address</Translate>
              </span>
            </dt>
            <dd>{clientEntity.clientEmailAddress}</dd>
            <dt>
              <span id="isActive">
                <Translate contentKey="rpaprojectApp.client.isActive">Is Active</Translate>
              </span>
            </dt>
            <dd>{clientEntity.isActive}</dd>
            <dt>
              <span id="createDate">
                <Translate contentKey="rpaprojectApp.client.createDate">Create Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={clientEntity.createDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createdBy">
                <Translate contentKey="rpaprojectApp.client.createdBy">Created By</Translate>
              </span>
            </dt>
            <dd>{clientEntity.createdBy}</dd>
            <dt>
              <span id="updateDate">
                <Translate contentKey="rpaprojectApp.client.updateDate">Update Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={clientEntity.updateDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedBy">
                <Translate contentKey="rpaprojectApp.client.updatedBy">Updated By</Translate>
              </span>
            </dt>
            <dd>{clientEntity.updatedBy}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.client.orgName">Org Name</Translate>
            </dt>
            <dd>{clientEntity.orgNameOrgName ? clientEntity.orgNameOrgName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/client" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/client/${clientEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ client }: IRootState) => ({
  clientEntity: client.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClientDetail);
