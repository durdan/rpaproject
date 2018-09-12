import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client-data-ocr.reducer';
import { IClientDataOcr } from 'app/shared/model/client-data-ocr.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDataOcrDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class ClientDataOcrDetail extends React.Component<IClientDataOcrDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { clientDataOcrEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.clientDataOcr.detail.title">ClientDataOcr</Translate> [<b>{clientDataOcrEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="keyName">
                <Translate contentKey="rpaprojectApp.clientDataOcr.keyName">Key Name</Translate>
              </span>
            </dt>
            <dd>{clientDataOcrEntity.keyName}</dd>
            <dt>
              <span id="value">
                <Translate contentKey="rpaprojectApp.clientDataOcr.value">Value</Translate>
              </span>
            </dt>
            <dd>{clientDataOcrEntity.value}</dd>
            <dt>
              <span id="messageId">
                <Translate contentKey="rpaprojectApp.clientDataOcr.messageId">Message Id</Translate>
              </span>
            </dt>
            <dd>{clientDataOcrEntity.messageId}</dd>
            <dt>
              <span id="clientEmailAddress">
                <Translate contentKey="rpaprojectApp.clientDataOcr.clientEmailAddress">Client Email Address</Translate>
              </span>
            </dt>
            <dd>{clientDataOcrEntity.clientEmailAddress}</dd>
            <dt>
              <span id="attachmentId">
                <Translate contentKey="rpaprojectApp.clientDataOcr.attachmentId">Attachment Id</Translate>
              </span>
            </dt>
            <dd>{clientDataOcrEntity.attachmentId}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.clientDataOcr.transaction">Transaction</Translate>
            </dt>
            <dd>{clientDataOcrEntity.transactionId ? clientDataOcrEntity.transactionId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/client-data-ocr" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/client-data-ocr/${clientDataOcrEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ clientDataOcr }: IRootState) => ({
  clientDataOcrEntity: clientDataOcr.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClientDataOcrDetail);
