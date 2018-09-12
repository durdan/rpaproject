import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './email-messages.reducer';
import { IEmailMessages } from 'app/shared/model/email-messages.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmailMessagesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class EmailMessagesDetail extends React.Component<IEmailMessagesDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { emailMessagesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.emailMessages.detail.title">EmailMessages</Translate> [<b>{emailMessagesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="messageId">
                <Translate contentKey="rpaprojectApp.emailMessages.messageId">Message Id</Translate>
              </span>
            </dt>
            <dd>{emailMessagesEntity.messageId}</dd>
            <dt>
              <span id="emailSubject">
                <Translate contentKey="rpaprojectApp.emailMessages.emailSubject">Email Subject</Translate>
              </span>
            </dt>
            <dd>{emailMessagesEntity.emailSubject}</dd>
            <dt>
              <span id="emailBody">
                <Translate contentKey="rpaprojectApp.emailMessages.emailBody">Email Body</Translate>
              </span>
            </dt>
            <dd>{emailMessagesEntity.emailBody}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="rpaprojectApp.emailMessages.status">Status</Translate>
              </span>
            </dt>
            <dd>{emailMessagesEntity.status}</dd>
            <dt>
              <span id="clientEmailAddress">
                <Translate contentKey="rpaprojectApp.emailMessages.clientEmailAddress">Client Email Address</Translate>
              </span>
            </dt>
            <dd>{emailMessagesEntity.clientEmailAddress}</dd>
            <dt>
              <span id="receiveFrom">
                <Translate contentKey="rpaprojectApp.emailMessages.receiveFrom">Receive From</Translate>
              </span>
            </dt>
            <dd>{emailMessagesEntity.receiveFrom}</dd>
            <dt>
              <span id="receivedTime">
                <Translate contentKey="rpaprojectApp.emailMessages.receivedTime">Received Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={emailMessagesEntity.receivedTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="numberOfAttachments">
                <Translate contentKey="rpaprojectApp.emailMessages.numberOfAttachments">Number Of Attachments</Translate>
              </span>
            </dt>
            <dd>{emailMessagesEntity.numberOfAttachments}</dd>
            <dt>
              <span id="attachments">
                <Translate contentKey="rpaprojectApp.emailMessages.attachments">Attachments</Translate>
              </span>
            </dt>
            <dd>{emailMessagesEntity.attachments}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.emailMessages.client">Client</Translate>
            </dt>
            <dd>{emailMessagesEntity.clientClientEmailAddress ? emailMessagesEntity.clientClientEmailAddress : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/email-messages" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/email-messages/${emailMessagesEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ emailMessages }: IRootState) => ({
  emailMessagesEntity: emailMessages.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmailMessagesDetail);
