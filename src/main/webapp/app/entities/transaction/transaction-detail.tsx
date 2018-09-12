import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './transaction.reducer';
import { ITransaction } from 'app/shared/model/transaction.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class TransactionDetail extends React.Component<ITransactionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { transactionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.transaction.detail.title">Transaction</Translate> [<b>{transactionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="createdDateTime">
                <Translate contentKey="rpaprojectApp.transaction.createdDateTime">Created Date Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={transactionEntity.createdDateTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="status">
                <Translate contentKey="rpaprojectApp.transaction.status">Status</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.status}</dd>
            <dt>
              <span id="clientEmailAddress">
                <Translate contentKey="rpaprojectApp.transaction.clientEmailAddress">Client Email Address</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.clientEmailAddress}</dd>
            <dt>
              <span id="messageId">
                <Translate contentKey="rpaprojectApp.transaction.messageId">Message Id</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.messageId}</dd>
            <dt>
              <span id="fileName">
                <Translate contentKey="rpaprojectApp.transaction.fileName">File Name</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.fileName}</dd>
            <dt>
              <span id="processType">
                <Translate contentKey="rpaprojectApp.transaction.processType">Process Type</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.processType}</dd>
            <dt>
              <span id="createDate">
                <Translate contentKey="rpaprojectApp.transaction.createDate">Create Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={transactionEntity.createDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createdBy">
                <Translate contentKey="rpaprojectApp.transaction.createdBy">Created By</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.createdBy}</dd>
            <dt>
              <span id="updateDate">
                <Translate contentKey="rpaprojectApp.transaction.updateDate">Update Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={transactionEntity.updateDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedBy">
                <Translate contentKey="rpaprojectApp.transaction.updatedBy">Updated By</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.updatedBy}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.transaction.fileForOCRProcessing">File For OCR Processing</Translate>
            </dt>
            <dd>{transactionEntity.fileForOCRProcessingId ? transactionEntity.fileForOCRProcessingId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/transaction" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/transaction/${transactionEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ transaction }: IRootState) => ({
  transactionEntity: transaction.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TransactionDetail);
