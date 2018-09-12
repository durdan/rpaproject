import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, reset } from './client-email-domain.reducer';
import { IClientEmailDomain } from 'app/shared/model/client-email-domain.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClientEmailDomainUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IClientEmailDomainUpdateState {
  isNew: boolean;
  clientId: number;
}

export class ClientEmailDomainUpdate extends React.Component<IClientEmailDomainUpdateProps, IClientEmailDomainUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      clientId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getClients();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { clientEmailDomainEntity } = this.props;
      const entity = {
        ...clientEmailDomainEntity,
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
    this.props.history.push('/entity/client-email-domain');
  };

  render() {
    const { clientEmailDomainEntity, clients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rpaprojectApp.clientEmailDomain.home.createOrEditLabel">
              <Translate contentKey="rpaprojectApp.clientEmailDomain.home.createOrEditLabel">Create or edit a ClientEmailDomain</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : clientEmailDomainEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="client-email-domain-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="emailDomainLabel" for="emailDomain">
                    <Translate contentKey="rpaprojectApp.clientEmailDomain.emailDomain">Email Domain</Translate>
                  </Label>
                  <AvField
                    id="client-email-domain-emailDomain"
                    type="text"
                    name="emailDomain"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="rpaprojectApp.clientEmailDomain.description">Description</Translate>
                  </Label>
                  <AvField id="client-email-domain-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="isActiveLabel" for="isActive">
                    <Translate contentKey="rpaprojectApp.clientEmailDomain.isActive">Is Active</Translate>
                  </Label>
                  <AvField id="client-email-domain-isActive" type="number" className="form-control" name="isActive" />
                </AvGroup>
                <AvGroup>
                  <Label for="client.clientName">
                    <Translate contentKey="rpaprojectApp.clientEmailDomain.client">Client</Translate>
                  </Label>
                  <AvInput id="client-email-domain-client" type="select" className="form-control" name="clientId">
                    <option value="" key="0" />
                    {clients
                      ? clients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.clientName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/client-email-domain" replace color="info">
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
  clients: storeState.client.entities,
  clientEmailDomainEntity: storeState.clientEmailDomain.entity,
  loading: storeState.clientEmailDomain.loading,
  updating: storeState.clientEmailDomain.updating
});

const mapDispatchToProps = {
  getClients,
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
)(ClientEmailDomainUpdate);
