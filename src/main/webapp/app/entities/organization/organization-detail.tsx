import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './organization.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrganizationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class OrganizationDetail extends React.Component<IOrganizationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { organizationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.organization.detail.title">Organization</Translate> [<b>{organizationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="orgName">
                <Translate contentKey="rpaprojectApp.organization.orgName">Org Name</Translate>
              </span>
            </dt>
            <dd>{organizationEntity.orgName}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="rpaprojectApp.organization.description">Description</Translate>
              </span>
            </dt>
            <dd>{organizationEntity.description}</dd>
            <dt>
              <span id="orgAddress">
                <Translate contentKey="rpaprojectApp.organization.orgAddress">Org Address</Translate>
              </span>
            </dt>
            <dd>{organizationEntity.orgAddress}</dd>
            <dt>
              <span id="orgEmail">
                <Translate contentKey="rpaprojectApp.organization.orgEmail">Org Email</Translate>
              </span>
            </dt>
            <dd>{organizationEntity.orgEmail}</dd>
            <dt>
              <span id="isActive">
                <Translate contentKey="rpaprojectApp.organization.isActive">Is Active</Translate>
              </span>
            </dt>
            <dd>{organizationEntity.isActive}</dd>
            <dt>
              <span id="createDate">
                <Translate contentKey="rpaprojectApp.organization.createDate">Create Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={organizationEntity.createDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createdBy">
                <Translate contentKey="rpaprojectApp.organization.createdBy">Created By</Translate>
              </span>
            </dt>
            <dd>{organizationEntity.createdBy}</dd>
            <dt>
              <span id="updateDate">
                <Translate contentKey="rpaprojectApp.organization.updateDate">Update Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={organizationEntity.updateDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedBy">
                <Translate contentKey="rpaprojectApp.organization.updatedBy">Updated By</Translate>
              </span>
            </dt>
            <dd>{organizationEntity.updatedBy}</dd>
          </dl>
          <Button tag={Link} to="/entity/organization" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/organization/${organizationEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ organization }: IRootState) => ({
  organizationEntity: organization.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrganizationDetail);
