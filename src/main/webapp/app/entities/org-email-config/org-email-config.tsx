import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './org-email-config.reducer';
import { IOrgEmailConfig } from 'app/shared/model/org-email-config.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IOrgEmailConfigProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IOrgEmailConfigState = IPaginationBaseState;

export class OrgEmailConfig extends React.Component<IOrgEmailConfigProps, IOrgEmailConfigState> {
  state: IOrgEmailConfigState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { orgEmailConfigList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="org-email-config-heading">
          <Translate contentKey="rpaprojectApp.orgEmailConfig.home.title">Org Email Configs</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="rpaprojectApp.orgEmailConfig.home.createLabel">Create new Org Email Config</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('emailServerHost')}>
                  <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerHost">Email Server Host</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('emailServerPort')}>
                  <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerPort">Email Server Port</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('emailServerUserId')}>
                  <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerUserId">Email Server User Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('emailServerPassword')}>
                  <Translate contentKey="rpaprojectApp.orgEmailConfig.emailServerPassword">Email Server Password</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('createDate')}>
                  <Translate contentKey="rpaprojectApp.orgEmailConfig.createDate">Create Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('createdBy')}>
                  <Translate contentKey="rpaprojectApp.orgEmailConfig.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('updateDate')}>
                  <Translate contentKey="rpaprojectApp.orgEmailConfig.updateDate">Update Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('updatedBy')}>
                  <Translate contentKey="rpaprojectApp.orgEmailConfig.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rpaprojectApp.orgEmailConfig.orgName">Org Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {orgEmailConfigList.map((orgEmailConfig, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${orgEmailConfig.id}`} color="link" size="sm">
                      {orgEmailConfig.id}
                    </Button>
                  </td>
                  <td>{orgEmailConfig.emailServerHost}</td>
                  <td>{orgEmailConfig.emailServerPort}</td>
                  <td>{orgEmailConfig.emailServerUserId}</td>
                  <td>{orgEmailConfig.emailServerPassword}</td>
                  <td>
                    <TextFormat type="date" value={orgEmailConfig.createDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{orgEmailConfig.createdBy}</td>
                  <td>
                    <TextFormat type="date" value={orgEmailConfig.updateDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{orgEmailConfig.updatedBy}</td>
                  <td>
                    {orgEmailConfig.orgNameOrgName ? (
                      <Link to={`organization/${orgEmailConfig.orgNameId}`}>{orgEmailConfig.orgNameOrgName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${orgEmailConfig.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orgEmailConfig.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orgEmailConfig.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ orgEmailConfig }: IRootState) => ({
  orgEmailConfigList: orgEmailConfig.entities,
  totalItems: orgEmailConfig.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrgEmailConfig);
