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
import { getEntities } from './ocr-processing-error.reducer';
import { IOcrProcessingError } from 'app/shared/model/ocr-processing-error.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IOcrProcessingErrorProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IOcrProcessingErrorState = IPaginationBaseState;

export class OcrProcessingError extends React.Component<IOcrProcessingErrorProps, IOcrProcessingErrorState> {
  state: IOcrProcessingErrorState = {
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
    const { ocrProcessingErrorList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="ocr-processing-error-heading">
          <Translate contentKey="rpaprojectApp.ocrProcessingError.home.title">Ocr Processing Errors</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="rpaprojectApp.ocrProcessingError.home.createLabel">Create new Ocr Processing Error</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('clientEmailAddress')}>
                  <Translate contentKey="rpaprojectApp.ocrProcessingError.clientEmailAddress">Client Email Address</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('messageId')}>
                  <Translate contentKey="rpaprojectApp.ocrProcessingError.messageId">Message Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('attachmentId')}>
                  <Translate contentKey="rpaprojectApp.ocrProcessingError.attachmentId">Attachment Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fileId')}>
                  <Translate contentKey="rpaprojectApp.ocrProcessingError.fileId">File Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('errorMessage')}>
                  <Translate contentKey="rpaprojectApp.ocrProcessingError.errorMessage">Error Message</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('createdDateTime')}>
                  <Translate contentKey="rpaprojectApp.ocrProcessingError.createdDateTime">Created Date Time</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('errorType')}>
                  <Translate contentKey="rpaprojectApp.ocrProcessingError.errorType">Error Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rpaprojectApp.ocrProcessingError.transaction">Transaction</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ocrProcessingErrorList.map((ocrProcessingError, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${ocrProcessingError.id}`} color="link" size="sm">
                      {ocrProcessingError.id}
                    </Button>
                  </td>
                  <td>{ocrProcessingError.clientEmailAddress}</td>
                  <td>{ocrProcessingError.messageId}</td>
                  <td>{ocrProcessingError.attachmentId}</td>
                  <td>{ocrProcessingError.fileId}</td>
                  <td>{ocrProcessingError.errorMessage}</td>
                  <td>
                    <TextFormat type="date" value={ocrProcessingError.createdDateTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{ocrProcessingError.errorType}</td>
                  <td>
                    {ocrProcessingError.transactionId ? (
                      <Link to={`transaction/${ocrProcessingError.transactionId}`}>{ocrProcessingError.transactionId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${ocrProcessingError.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ocrProcessingError.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ocrProcessingError.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ ocrProcessingError }: IRootState) => ({
  ocrProcessingErrorList: ocrProcessingError.entities,
  totalItems: ocrProcessingError.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OcrProcessingError);
