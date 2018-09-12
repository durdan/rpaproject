import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITransaction } from 'app/shared/model/transaction.model';
import { getEntities as getTransactions } from 'app/entities/transaction/transaction.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ocr-processing-error.reducer';
import { IOcrProcessingError } from 'app/shared/model/ocr-processing-error.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOcrProcessingErrorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IOcrProcessingErrorUpdateState {
  isNew: boolean;
  transactionId: number;
}

export class OcrProcessingErrorUpdate extends React.Component<IOcrProcessingErrorUpdateProps, IOcrProcessingErrorUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getTransactions();
  }

  saveEntity = (event, errors, values) => {
    values.createdDateTime = new Date(values.createdDateTime);

    if (errors.length === 0) {
      const { ocrProcessingErrorEntity } = this.props;
      const entity = {
        ...ocrProcessingErrorEntity,
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
    this.props.history.push('/entity/ocr-processing-error');
  };

  render() {
    const { ocrProcessingErrorEntity, transactions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.ocrProcessingError.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.ocrProcessingError.home.createOrEditLabel">
                Create or edit a OcrProcessingError
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ocrProcessingErrorEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ocr-processing-error-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="clientEmailAddressLabel" for="clientEmailAddress">
                    <Translate contentKey="rpaprojectApp.ocrProcessingError.clientEmailAddress">Client Email Address</Translate>
                  </Label>
                  <AvField id="ocr-processing-error-clientEmailAddress" type="text" name="clientEmailAddress" />
                </AvGroup>
                <AvGroup>
                  <Label id="messageIdLabel" for="messageId">
                    <Translate contentKey="rpaprojectApp.ocrProcessingError.messageId">Message Id</Translate>
                  </Label>
                  <AvField id="ocr-processing-error-messageId" type="text" name="messageId" />
                </AvGroup>
                <AvGroup>
                  <Label id="attachmentIdLabel" for="attachmentId">
                    <Translate contentKey="rpaprojectApp.ocrProcessingError.attachmentId">Attachment Id</Translate>
                  </Label>
                  <AvField id="ocr-processing-error-attachmentId" type="number" className="form-control" name="attachmentId" />
                </AvGroup>
                <AvGroup>
                  <Label id="fileIdLabel" for="fileId">
                    <Translate contentKey="rpaprojectApp.ocrProcessingError.fileId">File Id</Translate>
                  </Label>
                  <AvField id="ocr-processing-error-fileId" type="text" name="fileId" />
                </AvGroup>
                <AvGroup>
                  <Label id="errorMessageLabel" for="errorMessage">
                    <Translate contentKey="rpaprojectApp.ocrProcessingError.errorMessage">Error Message</Translate>
                  </Label>
                  <AvField id="ocr-processing-error-errorMessage" type="text" name="errorMessage" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdDateTimeLabel" for="createdDateTime">
                    <Translate contentKey="rpaprojectApp.ocrProcessingError.createdDateTime">Created Date Time</Translate>
                  </Label>
                  <AvInput
                    id="ocr-processing-error-createdDateTime"
                    type="datetime-local"
                    className="form-control"
                    name="createdDateTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.ocrProcessingErrorEntity.createdDateTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="errorTypeLabel" for="errorType">
                    <Translate contentKey="rpaprojectApp.ocrProcessingError.errorType">Error Type</Translate>
                  </Label>
                  <AvField id="ocr-processing-error-errorType" type="text" name="errorType" />
                </AvGroup>
                <AvGroup>
                  <Label for="transaction.id">
                    <Translate contentKey="rpaprojectApp.ocrProcessingError.transaction">Transaction</Translate>
                  </Label>
                  <AvInput id="ocr-processing-error-transaction" type="select" className="form-control" name="transactionId">
                    <option value="" key="0" />
                    {transactions
                      ? transactions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ocr-processing-error" replace color="info">
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
  transactions: storeState.transaction.entities,
  ocrProcessingErrorEntity: storeState.ocrProcessingError.entity,
  loading: storeState.ocrProcessingError.loading,
  updating: storeState.ocrProcessingError.updating
});

const mapDispatchToProps = {
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
)(OcrProcessingErrorUpdate);
