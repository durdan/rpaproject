import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ocr-processing-error.reducer';
import { IOcrProcessingError } from 'app/shared/model/ocr-processing-error.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOcrProcessingErrorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class OcrProcessingErrorDetail extends React.Component<IOcrProcessingErrorDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ocrProcessingErrorEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.ocrProcessingError.detail.title">OcrProcessingError</Translate> [<b>
              {ocrProcessingErrorEntity.id}
            </b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="clientEmailAddress">
                <Translate contentKey="rpaprojectApp.ocrProcessingError.clientEmailAddress">Client Email Address</Translate>
              </span>
            </dt>
            <dd>{ocrProcessingErrorEntity.clientEmailAddress}</dd>
            <dt>
              <span id="messageId">
                <Translate contentKey="rpaprojectApp.ocrProcessingError.messageId">Message Id</Translate>
              </span>
            </dt>
            <dd>{ocrProcessingErrorEntity.messageId}</dd>
            <dt>
              <span id="attachmentId">
                <Translate contentKey="rpaprojectApp.ocrProcessingError.attachmentId">Attachment Id</Translate>
              </span>
            </dt>
            <dd>{ocrProcessingErrorEntity.attachmentId}</dd>
            <dt>
              <span id="fileId">
                <Translate contentKey="rpaprojectApp.ocrProcessingError.fileId">File Id</Translate>
              </span>
            </dt>
            <dd>{ocrProcessingErrorEntity.fileId}</dd>
            <dt>
              <span id="errorMessage">
                <Translate contentKey="rpaprojectApp.ocrProcessingError.errorMessage">Error Message</Translate>
              </span>
            </dt>
            <dd>{ocrProcessingErrorEntity.errorMessage}</dd>
            <dt>
              <span id="createdDateTime">
                <Translate contentKey="rpaprojectApp.ocrProcessingError.createdDateTime">Created Date Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ocrProcessingErrorEntity.createdDateTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="errorType">
                <Translate contentKey="rpaprojectApp.ocrProcessingError.errorType">Error Type</Translate>
              </span>
            </dt>
            <dd>{ocrProcessingErrorEntity.errorType}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.ocrProcessingError.transaction">Transaction</Translate>
            </dt>
            <dd>{ocrProcessingErrorEntity.transactionId ? ocrProcessingErrorEntity.transactionId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/ocr-processing-error" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/ocr-processing-error/${ocrProcessingErrorEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ocrProcessingError }: IRootState) => ({
  ocrProcessingErrorEntity: ocrProcessingError.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OcrProcessingErrorDetail);
