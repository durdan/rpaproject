import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './email-processing-error.reducer';
import { IEmailProcessingError } from 'app/shared/model/email-processing-error.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmailProcessingErrorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class EmailProcessingErrorDetail extends React.Component<IEmailProcessingErrorDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { emailProcessingErrorEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.emailProcessingError.detail.title">EmailProcessingError</Translate> [<b>
              {emailProcessingErrorEntity.id}
            </b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="errorMessage">
                <Translate contentKey="rpaprojectApp.emailProcessingError.errorMessage">Error Message</Translate>
              </span>
            </dt>
            <dd>{emailProcessingErrorEntity.errorMessage}</dd>
            <dt>
              <span id="messageID">
                <Translate contentKey="rpaprojectApp.emailProcessingError.messageID">Message ID</Translate>
              </span>
            </dt>
            <dd>{emailProcessingErrorEntity.messageID}</dd>
            <dt>
              <span id="clientEmailAddress">
                <Translate contentKey="rpaprojectApp.emailProcessingError.clientEmailAddress">Client Email Address</Translate>
              </span>
            </dt>
            <dd>{emailProcessingErrorEntity.clientEmailAddress}</dd>
            <dt>
              <span id="receiveFrom">
                <Translate contentKey="rpaprojectApp.emailProcessingError.receiveFrom">Receive From</Translate>
              </span>
            </dt>
            <dd>{emailProcessingErrorEntity.receiveFrom}</dd>
            <dt>
              <span id="receivedTime">
                <Translate contentKey="rpaprojectApp.emailProcessingError.receivedTime">Received Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={emailProcessingErrorEntity.receivedTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="rpaprojectApp.emailProcessingError.emailMessages">Email Messages</Translate>
            </dt>
            <dd>{emailProcessingErrorEntity.emailMessagesId ? emailProcessingErrorEntity.emailMessagesId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/email-processing-error" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/email-processing-error/${emailProcessingErrorEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ emailProcessingError }: IRootState) => ({
  emailProcessingErrorEntity: emailProcessingError.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmailProcessingErrorDetail);
