import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './email-attachment.reducer';
import { IEmailAttachment } from 'app/shared/model/email-attachment.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmailAttachmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class EmailAttachmentDetail extends React.Component<IEmailAttachmentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { emailAttachmentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.emailAttachment.detail.title">EmailAttachment</Translate> [<b>
              {emailAttachmentEntity.id}
            </b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="messageId">
                <Translate contentKey="rpaprojectApp.emailAttachment.messageId">Message Id</Translate>
              </span>
            </dt>
            <dd>{emailAttachmentEntity.messageId}</dd>
            <dt>
              <span id="clientEmailAddress">
                <Translate contentKey="rpaprojectApp.emailAttachment.clientEmailAddress">Client Email Address</Translate>
              </span>
            </dt>
            <dd>{emailAttachmentEntity.clientEmailAddress}</dd>
            <dt>
              <span id="fileName">
                <Translate contentKey="rpaprojectApp.emailAttachment.fileName">File Name</Translate>
              </span>
            </dt>
            <dd>{emailAttachmentEntity.fileName}</dd>
            <dt>
              <span id="fileExtension">
                <Translate contentKey="rpaprojectApp.emailAttachment.fileExtension">File Extension</Translate>
              </span>
            </dt>
            <dd>{emailAttachmentEntity.fileExtension}</dd>
            <dt>
              <span id="fileLocation">
                <Translate contentKey="rpaprojectApp.emailAttachment.fileLocation">File Location</Translate>
              </span>
            </dt>
            <dd>{emailAttachmentEntity.fileLocation}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.emailAttachment.emailMessages">Email Messages</Translate>
            </dt>
            <dd>{emailAttachmentEntity.emailMessagesMessageId ? emailAttachmentEntity.emailMessagesMessageId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/email-attachment" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/email-attachment/${emailAttachmentEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ emailAttachment }: IRootState) => ({
  emailAttachmentEntity: emailAttachment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmailAttachmentDetail);
