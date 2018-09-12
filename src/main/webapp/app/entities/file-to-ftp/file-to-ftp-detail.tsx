import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './file-to-ftp.reducer';
import { IFileToFtp } from 'app/shared/model/file-to-ftp.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileToFtpDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class FileToFtpDetail extends React.Component<IFileToFtpDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { fileToFtpEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rpaprojectApp.fileToFtp.detail.title">FileToFtp</Translate> [<b>{fileToFtpEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="messageId">
                <Translate contentKey="rpaprojectApp.fileToFtp.messageId">Message Id</Translate>
              </span>
            </dt>
            <dd>{fileToFtpEntity.messageId}</dd>
            <dt>
              <span id="clientEmailAddress">
                <Translate contentKey="rpaprojectApp.fileToFtp.clientEmailAddress">Client Email Address</Translate>
              </span>
            </dt>
            <dd>{fileToFtpEntity.clientEmailAddress}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="rpaprojectApp.fileToFtp.status">Status</Translate>
              </span>
            </dt>
            <dd>{fileToFtpEntity.status}</dd>
            <dt>
              <span id="fileType">
                <Translate contentKey="rpaprojectApp.fileToFtp.fileType">File Type</Translate>
              </span>
            </dt>
            <dd>{fileToFtpEntity.fileType}</dd>
            <dt>
              <Translate contentKey="rpaprojectApp.fileToFtp.clientDataOcr">Client Data Ocr</Translate>
            </dt>
            <dd>{fileToFtpEntity.clientDataOcrClientEmailAddress ? fileToFtpEntity.clientDataOcrClientEmailAddress : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/file-to-ftp" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/file-to-ftp/${fileToFtpEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ fileToFtp }: IRootState) => ({
  fileToFtpEntity: fileToFtp.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileToFtpDetail);
