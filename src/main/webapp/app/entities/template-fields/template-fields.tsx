import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, getPaginationItemsNumber, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './template-fields.reducer';
import { ITemplateFields } from 'app/shared/model/template-fields.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ITemplateFieldsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ITemplateFieldsState = IPaginationBaseState;

export class TemplateFields extends React.Component<ITemplateFieldsProps, ITemplateFieldsState> {
  state: ITemplateFieldsState = {
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
    const { templateFieldsList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="template-fields-heading">
          <Translate contentKey="rpaprojectApp.templateFields.home.title">Template Fields</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="rpaprojectApp.templateFields.home.createLabel">Create new Template Fields</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fieldName')}>
                  <Translate contentKey="rpaprojectApp.templateFields.fieldName">Field Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fieldZoneMinX')}>
                  <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMinX">Field Zone Min X</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fieldZoneMinY')}>
                  <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMinY">Field Zone Min Y</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fieldZoneMaxX')}>
                  <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMaxX">Field Zone Max X</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fieldZoneMaxY')}>
                  <Translate contentKey="rpaprojectApp.templateFields.fieldZoneMaxY">Field Zone Max Y</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fieldValidationRequire')}>
                  <Translate contentKey="rpaprojectApp.templateFields.fieldValidationRequire">Field Validation Require</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fieldValidationRule')}>
                  <Translate contentKey="rpaprojectApp.templateFields.fieldValidationRule">Field Validation Rule</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rpaprojectApp.templateFields.inputTemplate">Input Template</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {templateFieldsList.map((templateFields, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${templateFields.id}`} color="link" size="sm">
                      {templateFields.id}
                    </Button>
                  </td>
                  <td>{templateFields.fieldName}</td>
                  <td>{templateFields.fieldZoneMinX}</td>
                  <td>{templateFields.fieldZoneMinY}</td>
                  <td>{templateFields.fieldZoneMaxX}</td>
                  <td>{templateFields.fieldZoneMaxY}</td>
                  <td>{templateFields.fieldValidationRequire}</td>
                  <td>{templateFields.fieldValidationRule}</td>
                  <td>
                    {templateFields.inputTemplateId ? (
                      <Link to={`input-template/${templateFields.inputTemplateId}`}>{templateFields.inputTemplateId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${templateFields.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${templateFields.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${templateFields.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ templateFields }: IRootState) => ({
  templateFieldsList: templateFields.entities,
  totalItems: templateFields.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TemplateFields);
