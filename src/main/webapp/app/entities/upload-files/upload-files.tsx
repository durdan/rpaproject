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
import { getEntities } from './upload-files.reducer';
import { IUploadFiles } from 'app/shared/model/upload-files.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IUploadFilesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IUploadFilesState = IPaginationBaseState;

export class UploadFiles extends React.Component<IUploadFilesProps, IUploadFilesState> {
  state: IUploadFilesState = {
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
    const { uploadFilesList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="upload-files-heading">
          <Translate contentKey="rpaprojectApp.uploadFiles.home.title">Upload Files</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="rpaprojectApp.uploadFiles.home.createLabel">Create new Upload Files</Translate>
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
                  <Translate contentKey="rpaprojectApp.uploadFiles.clientEmailAddress">Client Email Address</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fileName')}>
                  <Translate contentKey="rpaprojectApp.uploadFiles.fileName">File Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fileExtension')}>
                  <Translate contentKey="rpaprojectApp.uploadFiles.fileExtension">File Extension</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('uploadBy')}>
                  <Translate contentKey="rpaprojectApp.uploadFiles.uploadBy">Upload By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('uploadDateTime')}>
                  <Translate contentKey="rpaprojectApp.uploadFiles.uploadDateTime">Upload Date Time</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('uploadLocation')}>
                  <Translate contentKey="rpaprojectApp.uploadFiles.uploadLocation">Upload Location</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rpaprojectApp.uploadFiles.client">Client</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {uploadFilesList.map((uploadFiles, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${uploadFiles.id}`} color="link" size="sm">
                      {uploadFiles.id}
                    </Button>
                  </td>
                  <td>{uploadFiles.clientEmailAddress}</td>
                  <td>{uploadFiles.fileName}</td>
                  <td>{uploadFiles.fileExtension}</td>
                  <td>{uploadFiles.uploadBy}</td>
                  <td>
                    <TextFormat type="date" value={uploadFiles.uploadDateTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{uploadFiles.uploadLocation}</td>
                  <td>{uploadFiles.clientId ? <Link to={`client/${uploadFiles.clientId}`}>{uploadFiles.clientId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${uploadFiles.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${uploadFiles.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${uploadFiles.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ uploadFiles }: IRootState) => ({
  uploadFilesList: uploadFiles.entities,
  totalItems: uploadFiles.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UploadFiles);
