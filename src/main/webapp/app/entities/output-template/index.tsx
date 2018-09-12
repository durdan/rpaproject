import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OutputTemplate from './output-template';
import OutputTemplateDetail from './output-template-detail';
import OutputTemplateUpdate from './output-template-update';
import OutputTemplateDeleteDialog from './output-template-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OutputTemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OutputTemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OutputTemplateDetail} />
      <ErrorBoundaryRoute path={match.url} component={OutputTemplate} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OutputTemplateDeleteDialog} />
  </>
);

export default Routes;
