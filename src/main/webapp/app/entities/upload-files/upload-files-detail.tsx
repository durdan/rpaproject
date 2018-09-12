import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './upload-files.reducer';
import { IUploadFiles } from 'app/shared/model/upload-files.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUploadFilesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class UploadFilesDetail extends React.Component<IUploadFilesDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { uploadFilesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.uploadFiles.detail.title">UploadFiles</Translate> [<b>{uploadFilesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="clientEmailAddress">
                <Translate contentKey="rpaprojectApp.uploadFiles.clientEmailAddress">Client Email Address</Translate>
              </span>
            </dt>
            <dd>{uploadFilesEntity.clientEmailAddress}</dd>
            <dt>
              <span id="fileName">
                <Translate contentKey="rpaprojectApp.uploadFiles.fileName">File Name</Translate>
              </span>
            </dt>
            <dd>{uploadFilesEntity.fileName}</dd>
            <dt>
              <span id="fileExtension">
                <Translate contentKey="rpaprojectApp.uploadFiles.fileExtension">File Extension</Translate>
              </span>
            </dt>
            <dd>{uploadFilesEntity.fileExtension}</dd>
            <dt>
              <span id="uploadBy">
                <Translate contentKey="rpaprojectApp.uploadFiles.uploadBy">Upload By</Translate>
              </span>
            </dt>
            <dd>{uploadFilesEntity.uploadBy}</dd>
            <dt>
              <span id="uploadDateTime">
                <Translate contentKey="rpaprojectApp.uploadFiles.uploadDateTime">Upload Date Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={uploadFilesEntity.uploadDateTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="uploadLocation">
                <Translate contentKey="rpaprojectApp.uploadFiles.uploadLocation">Upload Location</Translate>
              </span>
            </dt>
            <dd>{uploadFilesEntity.uploadLocation}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.uploadFiles.client">Client</Translate>
            </dt>
            <dd>{uploadFilesEntity.clientId ? uploadFilesEntity.clientId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/upload-files" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/upload-files/${uploadFilesEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ uploadFiles }: IRootState) => ({
  uploadFilesEntity: uploadFiles.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UploadFilesDetail);
