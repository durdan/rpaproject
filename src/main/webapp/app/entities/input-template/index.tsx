import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InputTemplate from './input-template';
import InputTemplateDetail from './input-template-detail';
import InputTemplateUpdate from './input-template-update';
import InputTemplateDeleteDialog from './input-template-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InputTemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InputTemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InputTemplateDetail} />
      <ErrorBoundaryRoute path={match.url} component={InputTemplate} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={InputTemplateDeleteDialog} />
  </>
);

export default Routes;
