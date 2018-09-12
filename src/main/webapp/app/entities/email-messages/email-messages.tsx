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
import { getEntities } from './email-messages.reducer';
import { IEmailMessages } from 'app/shared/model/email-messages.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IEmailMessagesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IEmailMessagesState = IPaginationBaseState;

export class EmailMessages extends React.Component<IEmailMessagesProps, IEmailMessagesState> {
  state: IEmailMessagesState = {
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
    const { emailMessagesList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="email-messages-heading">
          <Translate contentKey="rpaprojectApp.emailMessages.home.title">Email Messages</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="rpaprojectApp.emailMessages.home.createLabel">Create new Email Messages</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('messageId')}>
                  <Translate contentKey="rpaprojectApp.emailMessages.messageId">Message Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('emailSubject')}>
                  <Translate contentKey="rpaprojectApp.emailMessages.emailSubject">Email Subject</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('emailBody')}>
                  <Translate contentKey="rpaprojectApp.emailMessages.emailBody">Email Body</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('status')}>
                  <Translate contentKey="rpaprojectApp.emailMessages.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('clientEmailAddress')}>
                  <Translate contentKey="rpaprojectApp.emailMessages.clientEmailAddress">Client Email Address</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('receiveFrom')}>
                  <Translate contentKey="rpaprojectApp.emailMessages.receiveFrom">Receive From</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('receivedTime')}>
                  <Translate contentKey="rpaprojectApp.emailMessages.receivedTime">Received Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('numberOfAttachments')}>
                  <Translate contentKey="rpaprojectApp.emailMessages.numberOfAttachments">Number Of Attachments</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('attachments')}>
                  <Translate contentKey="rpaprojectApp.emailMessages.attachments">Attachments</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rpaprojectApp.emailMessages.client">Client</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {emailMessagesList.map((emailMessages, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${emailMessages.id}`} color="link" size="sm">
                      {emailMessages.id}
                    </Button>
                  </td>
                  <td>{emailMessages.messageId}</td>
                  <td>{emailMessages.emailSubject}</td>
                  <td>{emailMessages.emailBody}</td>
                  <td>{emailMessages.status}</td>
                  <td>{emailMessages.clientEmailAddress}</td>
                  <td>{emailMessages.receiveFrom}</td>
                  <td>
                    <TextFormat type="date" value={emailMessages.receivedTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{emailMessages.numberOfAttachments}</td>
                  <td>{emailMessages.attachments}</td>
                  <td>
                    {emailMessages.clientClientEmailAddress ? (
                      <Link to={`client/${emailMessages.clientId}`}>{emailMessages.clientClientEmailAddress}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${emailMessages.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${emailMessages.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${emailMessages.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ emailMessages }: IRootState) => ({
  emailMessagesList: emailMessages.entities,
  totalItems: emailMessages.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmailMessages);
