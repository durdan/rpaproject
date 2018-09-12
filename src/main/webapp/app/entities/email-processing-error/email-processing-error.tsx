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
import { getEntities } from './email-processing-error.reducer';
import { IEmailProcessingError } from 'app/shared/model/email-processing-error.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IEmailProcessingErrorProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IEmailProcessingErrorState = IPaginationBaseState;

export class EmailProcessingError extends React.Component<IEmailProcessingErrorProps, IEmailProcessingErrorState> {
  state: IEmailProcessingErrorState = {
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
    const { emailProcessingErrorList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="email-processing-error-heading">
          <Translate contentKey="rpaprojectApp.emailProcessingError.home.title">Email Processing Errors</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="rpaprojectApp.emailProcessingError.home.createLabel">Create new Email Processing Error</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('errorMessage')}>
                  <Translate contentKey="rpaprojectApp.emailProcessingError.errorMessage">Error Message</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('messageID')}>
                  <Translate contentKey="rpaprojectApp.emailProcessingError.messageID">Message ID</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('clientEmailAddress')}>
                  <Translate contentKey="rpaprojectApp.emailProcessingError.clientEmailAddress">Client Email Address</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('receiveFrom')}>
                  <Translate contentKey="rpaprojectApp.emailProcessingError.receiveFrom">Receive From</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('receivedTime')}>
                  <Translate contentKey="rpaprojectApp.emailProcessingError.receivedTime">Received Time</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rpaprojectApp.emailProcessingError.emailMessages">Email Messages</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {emailProcessingErrorList.map((emailProcessingError, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${emailProcessingError.id}`} color="link" size="sm">
                      {emailProcessingError.id}
                    </Button>
                  </td>
                  <td>{emailProcessingError.errorMessage}</td>
                  <td>{emailProcessingError.messageID}</td>
                  <td>{emailProcessingError.clientEmailAddress}</td>
                  <td>{emailProcessingError.receiveFrom}</td>
                  <td>
                    <TextFormat type="date" value={emailProcessingError.receivedTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {emailProcessingError.emailMessagesId ? (
                      <Link to={`email-messages/${emailProcessingError.emailMessagesId}`}>{emailProcessingError.emailMessagesId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${emailProcessingError.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${emailProcessingError.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${emailProcessingError.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ emailProcessingError }: IRootState) => ({
  emailProcessingErrorList: emailProcessingError.entities,
  totalItems: emailProcessingError.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmailProcessingError);
