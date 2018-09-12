import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './org-email-config.reducer';
import { IOrgEmailConfig } from 'app/shared/model/org-email-config.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrgEmailConfigDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class OrgEmailConfigDetail extends React.Component<IOrgEmailConfigDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { orgEmailConfigEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.orgEmailConfig.detail.title">OrgEmailConfig</Translate> [<b>{orgEmailConfigEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="emailServerHost">
                <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerHost">Email Server Host</Translate>
              </span>
            </dt>
            <dd>{orgEmailConfigEntity.emailServerHost}</dd>
            <dt>
              <span id="emailServerPort">
                <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerPort">Email Server Port</Translate>
              </span>
            </dt>
            <dd>{orgEmailConfigEntity.emailServerPort}</dd>
            <dt>
              <span id="emailServerUserId">
                <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerUserId">Email Server User Id</Translate>
              </span>
            </dt>
            <dd>{orgEmailConfigEntity.emailServerUserId}</dd>
            <dt>
              <span id="emailServerPassword">
                <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerPassword">Email Server Password</Translate>
              </span>
            </dt>
            <dd>{orgEmailConfigEntity.emailServerPassword}</dd>
            <dt>
              <span id="createDate">
                <Translate contentKey="rpaprojectApp.orgEmailConfig.createDate">Create Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orgEmailConfigEntity.createDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createdBy">
                <Translate contentKey="rpaprojectApp.orgEmailConfig.createdBy">Created By</Translate>
              </span>
            </dt>
            <dd>{orgEmailConfigEntity.createdBy}</dd>
            <dt>
              <span id="updateDate">
                <Translate contentKey="rpaprojectApp.orgEmailConfig.updateDate">Update Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orgEmailConfigEntity.updateDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedBy">
                <Translate contentKey="rpaprojectApp.orgEmailConfig.updatedBy">Updated By</Translate>
              </span>
            </dt>
            <dd>{orgEmailConfigEntity.updatedBy}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.orgEmailConfig.orgName">Org Name</Translate>
            </dt>
            <dd>{orgEmailConfigEntity.orgNameOrgName ? orgEmailConfigEntity.orgNameOrgName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/org-email-config" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/org-email-config/${orgEmailConfigEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ orgEmailConfig }: IRootState) => ({
  orgEmailConfigEntity: orgEmailConfig.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrgEmailConfigDetail);
