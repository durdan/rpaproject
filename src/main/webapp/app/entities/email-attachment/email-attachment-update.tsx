import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFileForOCRProcessing } from 'app/shared/model/file-for-ocr-processing.model';
import { getEntities as getFileForOcrProcessings } from 'app/entities/file-for-ocr-processing/file-for-ocr-processing.reducer';
import { IEmailMessages } from 'app/shared/model/email-messages.model';
import { getEntities as getEmailMessages } from 'app/entities/email-messages/email-messages.reducer';
import { getEntity, updateEntity, createEntity, reset } from './email-attachment.reducer';
import { IEmailAttachment } from 'app/shared/model/email-attachment.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmailAttachmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IEmailAttachmentUpdateState {
  isNew: boolean;
  fileForOCRProcessingId: number;
  emailMessagesId: number;
}

export class EmailAttachmentUpdate extends React.Component<IEmailAttachmentUpdateProps, IEmailAttachmentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      fileForOCRProcessingId: 0,
      emailMessagesId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getFileForOcrProcessings();
    this.props.getEmailMessages();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { emailAttachmentEntity } = this.props;
      const entity = {
        ...emailAttachmentEntity,
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
    this.props.history.push('/entity/email-attachment');
  };

  render() {
    const { emailAttachmentEntity, fileForOCRProcessings, emailMessages, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.emailAttachment.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.emailAttachment.home.createOrEditLabel">Create or edit a EmailAttachment</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : emailAttachmentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="email-attachment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="messageIdLabel" for="messageId">
                    <Translate contentKey="rpaprojectApp.emailAttachment.messageId">Message Id</Translate>
                  </Label>
                  <AvField id="email-attachment-messageId" type="text" name="messageId" />
                </AvGroup>
                <AvGroup>
                  <Label id="clientEmailAddressLabel" for="clientEmailAddress">
                    <Translate contentKey="rpaprojectApp.emailAttachment.clientEmailAddress">Client Email Address</Translate>
                  </Label>
                  <AvField id="email-attachment-clientEmailAddress" type="text" name="clientEmailAddress" />
                </AvGroup>
                <AvGroup>
                  <Label id="fileNameLabel" for="fileName">
                    <Translate contentKey="rpaprojectApp.emailAttachment.fileName">File Name</Translate>
                  </Label>
                  <AvField
                    id="email-attachment-fileName"
                    type="text"
                    name="fileName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fileExtensionLabel" for="fileExtension">
                    <Translate contentKey="rpaprojectApp.emailAttachment.fileExtension">File Extension</Translate>
                  </Label>
                  <AvField
                    id="email-attachment-fileExtension"
                    type="text"
                    name="fileExtension"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fileLocationLabel" for="fileLocation">
                    <Translate contentKey="rpaprojectApp.emailAttachment.fileLocation">File Location</Translate>
                  </Label>
                  <AvField id="email-attachment-fileLocation" type="text" name="fileLocation" />
                </AvGroup>
                <AvGroup>
                  <Label for="emailMessages.messageId">
                    <Translate contentKey="rpaprojectApp.emailAttachment.emailMessages">Email Messages</Translate>
                  </Label>
                  <AvInput id="email-attachment-emailMessages" type="select" className="form-control" name="emailMessagesId">
                    <option value="" key="0" />
                    {emailMessages
                      ? emailMessages.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.messageId}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/email-attachment" replace color="info">
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
  fileForOCRProcessings: storeState.fileForOCRProcessing.entities,
  emailMessages: storeState.emailMessages.entities,
  emailAttachmentEntity: storeState.emailAttachment.entity,
  loading: storeState.emailAttachment.loading,
  updating: storeState.emailAttachment.updating
});

const mapDispatchToProps = {
  getFileForOcrProcessings,
  getEmailMessages,
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
)(EmailAttachmentUpdate);
