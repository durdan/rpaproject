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
import { getEntities } from './file-for-ocr-processing.reducer';
import { IFileForOCRProcessing } from 'app/shared/model/file-for-ocr-processing.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IFileForOCRProcessingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IFileForOCRProcessingState = IPaginationBaseState;

export class FileForOCRProcessing extends React.Component<IFileForOCRProcessingProps, IFileForOCRProcessingState> {
  state: IFileForOCRProcessingState = {
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
    const { fileForOCRProcessingList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="file-for-ocr-processing-heading">
          <Translate contentKey="rpaprojectApp.fileForOCRProcessing.home.title">File For OCR Processings</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="rpaprojectApp.fileForOCRProcessing.home.createLabel">Create new File For OCR Processing</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fileInputType')}>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.fileInputType">File Input Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('status')}>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('messageId')}>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.messageId">Message Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('clientEmailAddress')}>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.clientEmailAddress">Client Email Address</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('retry')}>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.retry">Retry</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('createdDateTime')}>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.createdDateTime">Created Date Time</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('createdBy')}>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('updateTimeStamp')}>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.updateTimeStamp">Update Time Stamp</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('updateBy')}>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.updateBy">Update By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.emailAttachment">Email Attachment</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rpaprojectApp.fileForOCRProcessing.uploadFiles">Upload Files</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fileForOCRProcessingList.map((fileForOCRProcessing, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${fileForOCRProcessing.id}`} color="link" size="sm">
                      {fileForOCRProcessing.id}
                    </Button>
                  </td>
                  <td>{fileForOCRProcessing.fileInputType}</td>
                  <td>{fileForOCRProcessing.status}</td>
                  <td>{fileForOCRProcessing.messageId}</td>
                  <td>{fileForOCRProcessing.clientEmailAddress}</td>
                  <td>{fileForOCRProcessing.retry}</td>
                  <td>
                    <TextFormat type="date" value={fileForOCRProcessing.createdDateTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{fileForOCRProcessing.createdBy}</td>
                  <td>
                    <TextFormat type="date" value={fileForOCRProcessing.updateTimeStamp} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={fileForOCRProcessing.updateBy} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {fileForOCRProcessing.emailAttachmentId ? (
                      <Link to={`email-attachment/${fileForOCRProcessing.emailAttachmentId}`}>
                        {fileForOCRProcessing.emailAttachmentId}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {fileForOCRProcessing.uploadFilesId ? (
                      <Link to={`upload-files/${fileForOCRProcessing.uploadFilesId}`}>{fileForOCRProcessing.uploadFilesId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${fileForOCRProcessing.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${fileForOCRProcessing.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${fileForOCRProcessing.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ fileForOCRProcessing }: IRootState) => ({
  fileForOCRProcessingList: fileForOCRProcessing.entities,
  totalItems: fileForOCRProcessing.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileForOCRProcessing);
