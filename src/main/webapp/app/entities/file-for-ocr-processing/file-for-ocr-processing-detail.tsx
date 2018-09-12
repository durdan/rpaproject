import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './file-for-ocr-processing.reducer';
import { IFileForOCRProcessing } from 'app/shared/model/file-for-ocr-processing.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileForOCRProcessingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class FileForOCRProcessingDetail extends React.Component<IFileForOCRProcessingDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { fileForOCRProcessingEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.fileForOCRProcessing.detail.title">FileForOCRProcessing</Translate> [<b>
              {fileForOCRProcessingEntity.id}
            </b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fileInputType">
                <Translate contentKey="rpaprojectApp.fileForOCRProcessing.fileInputType">File Input Type</Translate>
              </span>
            </dt>
            <dd>{fileForOCRProcessingEntity.fileInputType}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="rpaprojectApp.fileForOCRProcessing.status">Status</Translate>
              </span>
            </dt>
            <dd>{fileForOCRProcessingEntity.status}</dd>
            <dt>
              <span id="messageId">
                <Translate contentKey="rpaprojectApp.fileForOCRProcessing.messageId">Message Id</Translate>
              </span>
            </dt>
            <dd>{fileForOCRProcessingEntity.messageId}</dd>
            <dt>
              <span id="clientEmailAddress">
                <Translate contentKey="rpaprojectApp.fileForOCRProcessing.clientEmailAddress">Client Email Address</Translate>
              </span>
            </dt>
            <dd>{fileForOCRProcessingEntity.clientEmailAddress}</dd>
            <dt>
              <span id="retry">
                <Translate contentKey="rpaprojectApp.fileForOCRProcessing.retry">Retry</Translate>
              </span>
            </dt>
            <dd>{fileForOCRProcessingEntity.retry}</dd>
            <dt>
              <span id="createdDateTime">
                <Translate contentKey="rpaprojectApp.fileForOCRProcessing.createdDateTime">Created Date Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={fileForOCRProcessingEntity.createdDateTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createdBy">
                <Translate contentKey="rpaprojectApp.fileForOCRProcessing.createdBy">Created By</Translate>
              </span>
            </dt>
            <dd>{fileForOCRProcessingEntity.createdBy}</dd>
            <dt>
              <span id="updateTimeStamp">
                <Translate contentKey="rpaprojectApp.fileForOCRProcessing.updateTimeStamp">Update Time Stamp</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={fileForOCRProcessingEntity.updateTimeStamp} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateBy">
                <Translate contentKey="rpaprojectApp.fileForOCRProcessing.updateBy">Update By</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={fileForOCRProcessingEntity.updateBy} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="rpaprojectApp.fileForOCRProcessing.emailAttachment">Email Attachment</Translate>
            </dt>
            <dd>{fileForOCRProcessingEntity.emailAttachmentId ? fileForOCRProcessingEntity.emailAttachmentId : ''}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.fileForOCRProcessing.uploadFiles">Upload Files</Translate>
            </dt>
            <dd>{fileForOCRProcessingEntity.uploadFilesId ? fileForOCRProcessingEntity.uploadFilesId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/file-for-ocr-processing" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/file-for-ocr-processing/${fileForOCRProcessingEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ fileForOCRProcessing }: IRootState) => ({
  fileForOCRProcessingEntity: fileForOCRProcessing.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileForOCRProcessingDetail);
