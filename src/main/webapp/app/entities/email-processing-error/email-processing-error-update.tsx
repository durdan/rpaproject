import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmailMessages } from 'app/shared/model/email-messages.model';
import { getEntities as getEmailMessages } from 'app/entities/email-messages/email-messages.reducer';
import { getEntity, updateEntity, createEntity, reset } from './email-processing-error.reducer';
import { IEmailProcessingError } from 'app/shared/model/email-processing-error.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmailProcessingErrorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IEmailProcessingErrorUpdateState {
  isNew: boolean;
  emailMessagesId: number;
}

export class EmailProcessingErrorUpdate extends React.Component<IEmailProcessingErrorUpdateProps, IEmailProcessingErrorUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getEmailMessages();
  }

  saveEntity = (event, errors, values) => {
    values.receivedTime = new Date(values.receivedTime);

    if (errors.length === 0) {
      const { emailProcessingErrorEntity } = this.props;
      const entity = {
        ...emailProcessingErrorEntity,
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
    this.props.history.push('/entity/email-processing-error');
  };

  render() {
    const { emailProcessingErrorEntity, emailMessages, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.emailProcessingError.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.emailProcessingError.home.createOrEditLabel">
                Create or edit a EmailProcessingError
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : emailProcessingErrorEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="email-processing-error-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="errorMessageLabel" for="errorMessage">
                    <Translate contentKey="rpaprojectApp.emailProcessingError.errorMessage">Error Message</Translate>
                  </Label>
                  <AvField id="email-processing-error-errorMessage" type="text" name="errorMessage" />
                </AvGroup>
                <AvGroup>
                  <Label id="messageIDLabel" for="messageID">
                    <Translate contentKey="rpaprojectApp.emailProcessingError.messageID">Message ID</Translate>
                  </Label>
                  <AvField
                    id="email-processing-error-messageID"
                    type="text"
                    name="messageID"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="clientEmailAddressLabel" for="clientEmailAddress">
                    <Translate contentKey="rpaprojectApp.emailProcessingError.clientEmailAddress">Client Email Address</Translate>
                  </Label>
                  <AvField id="email-processing-error-clientEmailAddress" type="text" name="clientEmailAddress" />
                </AvGroup>
                <AvGroup>
                  <Label id="receiveFromLabel" for="receiveFrom">
                    <Translate contentKey="rpaprojectApp.emailProcessingError.receiveFrom">Receive From</Translate>
                  </Label>
                  <AvField
                    id="email-processing-error-receiveFrom"
                    type="text"
                    name="receiveFrom"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="receivedTimeLabel" for="receivedTime">
                    <Translate contentKey="rpaprojectApp.emailProcessingError.receivedTime">Received Time</Translate>
                  </Label>
                  <AvInput
                    id="email-processing-error-receivedTime"
                    type="datetime-local"
                    className="form-control"
                    name="receivedTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.emailProcessingErrorEntity.receivedTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="emailMessages.id">
                    <Translate contentKey="rpaprojectApp.emailProcessingError.emailMessages">Email Messages</Translate>
                  </Label>
                  <AvInput id="email-processing-error-emailMessages" type="select" className="form-control" name="emailMessagesId">
                    <option value="" key="0" />
                    {emailMessages
                      ? emailMessages.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/email-processing-error" replace color="info">
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
  emailMessages: storeState.emailMessages.entities,
  emailProcessingErrorEntity: storeState.emailProcessingError.entity,
  loading: storeState.emailProcessingError.loading,
  updating: storeState.emailProcessingError.updating
});

const mapDispatchToProps = {
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
)(EmailProcessingErrorUpdate);
