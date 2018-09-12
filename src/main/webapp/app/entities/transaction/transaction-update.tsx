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
import { IOcrProcessingError } from 'app/shared/model/ocr-processing-error.model';
import { getEntities as getOcrProcessingErrors } from 'app/entities/ocr-processing-error/ocr-processing-error.reducer';
import { getEntity, updateEntity, createEntity, reset } from './transaction.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface ITransactionUpdateState {
  isNew: boolean;
  fileForOCRProcessingId: number;
  ocrProcessingErrorId: number;
}

export class TransactionUpdate extends React.Component<ITransactionUpdateProps, ITransactionUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      fileForOCRProcessingId: 0,
      ocrProcessingErrorId: 0,
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
    this.props.getOcrProcessingErrors();
  }

  saveEntity = (event, errors, values) => {
    values.createdDateTime = new Date(values.createdDateTime);
    values.createDate = new Date(values.createDate);
    values.updateDate = new Date(values.updateDate);

    if (errors.length === 0) {
      const { transactionEntity } = this.props;
      const entity = {
        ...transactionEntity,
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
    this.props.history.push('/entity/transaction');
  };

  render() {
    const { transactionEntity, fileForOCRProcessings, ocrProcessingErrors, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.transaction.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.transaction.home.createOrEditLabel">Create or edit a Transaction</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : transactionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="transaction-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="createdDateTimeLabel" for="createdDateTime">
                    <Translate contentKey="rpaprojectApp.transaction.createdDateTime">Created Date Time</Translate>
                  </Label>
                  <AvInput
                    id="transaction-createdDateTime"
                    type="datetime-local"
                    className="form-control"
                    name="createdDateTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.transactionEntity.createdDateTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="status">
                    <Translate contentKey="rpaprojectApp.transaction.status">Status</Translate>
                  </Label>
                  <AvField id="transaction-status" type="text" name="status" />
                </AvGroup>
                <AvGroup>
                  <Label id="clientEmailAddressLabel" for="clientEmailAddress">
                    <Translate contentKey="rpaprojectApp.transaction.clientEmailAddress">Client Email Address</Translate>
                  </Label>
                  <AvField
                    id="transaction-clientEmailAddress"
                    type="text"
                    name="clientEmailAddress"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="messageIdLabel" for="messageId">
                    <Translate contentKey="rpaprojectApp.transaction.messageId">Message Id</Translate>
                  </Label>
                  <AvField
                    id="transaction-messageId"
                    type="text"
                    name="messageId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fileNameLabel" for="fileName">
                    <Translate contentKey="rpaprojectApp.transaction.fileName">File Name</Translate>
                  </Label>
                  <AvField
                    id="transaction-fileName"
                    type="text"
                    name="fileName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="processTypeLabel" for="processType">
                    <Translate contentKey="rpaprojectApp.transaction.processType">Process Type</Translate>
                  </Label>
                  <AvField id="transaction-processType" type="text" name="processType" />
                </AvGroup>
                <AvGroup>
                  <Label id="createDateLabel" for="createDate">
                    <Translate contentKey="rpaprojectApp.transaction.createDate">Create Date</Translate>
                  </Label>
                  <AvInput
                    id="transaction-createDate"
                    type="datetime-local"
                    className="form-control"
                    name="createDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.transactionEntity.createDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="createdByLabel" for="createdBy">
                    <Translate contentKey="rpaprojectApp.transaction.createdBy">Created By</Translate>
                  </Label>
                  <AvField id="transaction-createdBy" type="text" name="createdBy" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateDateLabel" for="updateDate">
                    <Translate contentKey="rpaprojectApp.transaction.updateDate">Update Date</Translate>
                  </Label>
                  <AvInput
                    id="transaction-updateDate"
                    type="datetime-local"
                    className="form-control"
                    name="updateDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.transactionEntity.updateDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedByLabel" for="updatedBy">
                    <Translate contentKey="rpaprojectApp.transaction.updatedBy">Updated By</Translate>
                  </Label>
                  <AvField id="transaction-updatedBy" type="text" name="updatedBy" />
                </AvGroup>
                <AvGroup>
                  <Label for="fileForOCRProcessing.id">
                    <Translate contentKey="rpaprojectApp.transaction.fileForOCRProcessing">File For OCR Processing</Translate>
                  </Label>
                  <AvInput id="transaction-fileForOCRProcessing" type="select" className="form-control" name="fileForOCRProcessingId">
                    <option value="" key="0" />
                    {fileForOCRProcessings
                      ? fileForOCRProcessings.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/transaction" replace color="info">
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
  ocrProcessingErrors: storeState.ocrProcessingError.entities,
  transactionEntity: storeState.transaction.entity,
  loading: storeState.transaction.loading,
  updating: storeState.transaction.updating
});

const mapDispatchToProps = {
  getFileForOcrProcessings,
  getOcrProcessingErrors,
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
)(TransactionUpdate);
