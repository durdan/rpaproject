import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmailProcessingError from './email-processing-error';
import EmailProcessingErrorDetail from './email-processing-error-detail';
import EmailProcessingErrorUpdate from './email-processing-error-update';
import EmailProcessingErrorDeleteDialog from './email-processing-error-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmailProcessingErrorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmailProcessingErrorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmailProcessingErrorDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmailProcessingError} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EmailProcessingErrorDeleteDialog} />
  </>
);

export default Routes;
