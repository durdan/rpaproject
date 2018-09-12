import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, getPaginationItemsNumber, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './output-template.reducer';
import { IOutputTemplate } from 'app/shared/model/output-template.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IOutputTemplateProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IOutputTemplateState = IPaginationBaseState;

export class OutputTemplate extends React.Component<IOutputTemplateProps, IOutputTemplateState> {
  state: IOutputTemplateState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { outputTemplateList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="output-template-heading">
          <Translate contentKey="rpaprojectApp.outputTemplate.home.title">Output Templates</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="rpaprojectApp.outputTemplate.home.createLabel">Create new Output Template</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('outputTemplateName')}>
                  <Translate contentKey="rpaprojectApp.outputTemplate.outputTemplateName">Output Template Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fieldName')}>
                  <Translate contentKey="rpaprojectApp.outputTemplate.fieldName">Field Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('position')}>
                  <Translate contentKey="rpaprojectApp.outputTemplate.position">Position</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fieldValidationRequire')}>
                  <Translate contentKey="rpaprojectApp.outputTemplate.fieldValidationRequire">Field Validation Require</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fieldValidationRule')}>
                  <Translate contentKey="rpaprojectApp.outputTemplate.fieldValidationRule">Field Validation Rule</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rpaprojectApp.outputTemplate.client">Client</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rpaprojectApp.outputTemplate.clientDataOcr">Client Data Ocr</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {outputTemplateList.map((outputTemplate, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${outputTemplate.id}`} color="link" size="sm">
                      {outputTemplate.id}
                    </Button>
                  </td>
                  <td>{outputTemplate.outputTemplateName}</td>
                  <td>{outputTemplate.fieldName}</td>
                  <td>{outputTemplate.position}</td>
                  <td>{outputTemplate.fieldValidationRequire}</td>
                  <td>{outputTemplate.fieldValidationRule}</td>
                  <td>{outputTemplate.clientId ? <Link to={`client/${outputTemplate.clientId}`}>{outputTemplate.clientId}</Link> : ''}</td>
                  <td>
                    {outputTemplate.clientDataOcrId ? (
                      <Link to={`client-data-ocr/${outputTemplate.clientDataOcrId}`}>{outputTemplate.clientDataOcrId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${outputTemplate.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${outputTemplate.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${outputTemplate.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ outputTemplate }: IRootState) => ({
  outputTemplateList: outputTemplate.entities,
  totalItems: outputTemplate.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OutputTemplate);
