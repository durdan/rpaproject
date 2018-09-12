import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmailAttachment } from 'app/shared/model/email-attachment.model';
import { getEntities as getEmailAttachments } from 'app/entities/email-attachment/email-attachment.reducer';
import { IUploadFiles } from 'app/shared/model/upload-files.model';
import { getEntities as getUploadFiles } from 'app/entities/upload-files/upload-files.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
import { getEntities as getTransactions } from 'app/entities/transaction/transaction.reducer';
import { getEntity, updateEntity, createEntity, reset } from './file-for-ocr-processing.reducer';
import { IFileForOCRProcessing } from 'app/shared/model/file-for-ocr-processing.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFileForOCRProcessingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IFileForOCRProcessingUpdateState {
  isNew: boolean;
  emailAttachmentId: number;
  uploadFilesId: number;
  transactionId: number;
}

export class FileForOCRProcessingUpdate extends React.Component<IFileForOCRProcessingUpdateProps, IFileForOCRProcessingUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      emailAttachmentId: 0,
      uploadFilesId: 0,
      transactionId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getEmailAttachments();
    this.props.getUploadFiles();
    this.props.getTransactions();
  }

  saveEntity = (event, errors, values) => {
    values.createdDateTime = new Date(values.createdDateTime);
    values.updateTimeStamp = new Date(values.updateTimeStamp);
    values.updateBy = new Date(values.updateBy);

    if (errors.length === 0) {
      const { fileForOCRProcessingEntity } = this.props;
      const entity = {
        ...fileForOCRProcessingEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/file-for-ocr-processing');
  };

  render() {
    const { fileForOCRProcessingEntity, emailAttachments, uploadFiles, transactions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.fileForOCRProcessing.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.fileForOCRProcessing.home.createOrEditLabel">
                Create or edit a FileForOCRProcessing
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : fileForOCRProcessingEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="file-for-ocr-processing-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="fileInputTypeLabel" for="fileInputType">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.fileInputType">File Input Type</Translate>
                  </Label>
                  <AvField
                    id="file-for-ocr-processing-fileInputType"
                    type="text"
                    name="fileInputType"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="status">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.status">Status</Translate>
                  </Label>
                  <AvField id="file-for-ocr-processing-status" type="text" name="status" />
                </AvGroup>
                <AvGroup>
                  <Label id="messageIdLabel" for="messageId">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.messageId">Message Id</Translate>
                  </Label>
                  <AvField id="file-for-ocr-processing-messageId" type="text" name="messageId" />
                </AvGroup>
                <AvGroup>
                  <Label id="clientEmailAddressLabel" for="clientEmailAddress">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.clientEmailAddress">Client Email Address</Translate>
                  </Label>
                  <AvField id="file-for-ocr-processing-clientEmailAddress" type="text" name="clientEmailAddress" />
                </AvGroup>
                <AvGroup>
                  <Label id="retryLabel" for="retry">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.retry">Retry</Translate>
                  </Label>
                  <AvField id="file-for-ocr-processing-retry" type="text" name="retry" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdDateTimeLabel" for="createdDateTime">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.createdDateTime">Created Date Time</Translate>
                  </Label>
                  <AvInput
                    id="file-for-ocr-processing-createdDateTime"
                    type="datetime-local"
                    className="form-control"
                    name="createdDateTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.fileForOCRProcessingEntity.createdDateTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="createdByLabel" for="createdBy">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.createdBy">Created By</Translate>
                  </Label>
                  <AvField id="file-for-ocr-processing-createdBy" type="text" name="createdBy" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateTimeStampLabel" for="updateTimeStamp">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.updateTimeStamp">Update Time Stamp</Translate>
                  </Label>
                  <AvInput
                    id="file-for-ocr-processing-updateTimeStamp"
                    type="datetime-local"
                    className="form-control"
                    name="updateTimeStamp"
                    value={isNew ? null : convertDateTimeFromServer(this.props.fileForOCRProcessingEntity.updateTimeStamp)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updateByLabel" for="updateBy">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.updateBy">Update By</Translate>
                  </Label>
                  <AvInput
                    id="file-for-ocr-processing-updateBy"
                    type="datetime-local"
                    className="form-control"
                    name="updateBy"
                    value={isNew ? null : convertDateTimeFromServer(this.props.fileForOCRProcessingEntity.updateBy)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="emailAttachment.id">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.emailAttachment">Email Attachment</Translate>
                  </Label>
                  <AvInput id="file-for-ocr-processing-emailAttachment" type="select" className="form-control" name="emailAttachmentId">
                    <option value="" key="0" />
                    {emailAttachments
                      ? emailAttachments.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="uploadFiles.id">
                    <Translate contentKey="rpaprojectApp.fileForOCRProcessing.uploadFiles">Upload Files</Translate>
                  </Label>
                  <AvInput id="file-for-ocr-processing-uploadFiles" type="select" className="form-control" name="uploadFilesId">
                    <option value="" key="0" />
                    {uploadFiles
                      ? uploadFiles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/file-for-ocr-processing" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  emailAttachments: storeState.emailAttachment.entities,
  uploadFiles: storeState.uploadFiles.entities,
  transactions: storeState.transaction.entities,
  fileForOCRProcessingEntity: storeState.fileForOCRProcessing.entity,
  loading: storeState.fileForOCRProcessing.loading,
  updating: storeState.fileForOCRProcessing.updating
});

const mapDispatchToProps = {
  getEmailAttachments,
  getUploadFiles,
  getTransactions,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileForOCRProcessingUpdate);
