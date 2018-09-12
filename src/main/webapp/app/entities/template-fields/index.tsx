import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TemplateFields from './template-fields';
import TemplateFieldsDetail from './template-fields-detail';
import TemplateFieldsUpdate from './template-fields-update';
import TemplateFieldsDeleteDialog from './template-fields-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TemplateFieldsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TemplateFieldsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TemplateFieldsDetail} />
      <ErrorBoundaryRoute path={match.url} component={TemplateFields} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TemplateFieldsDeleteDialog} />
  </>
);

export default Routes;
