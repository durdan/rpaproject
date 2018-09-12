import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmailMessages from './email-messages';
import EmailMessagesDetail from './email-messages-detail';
import EmailMessagesUpdate from './email-messages-update';
import EmailMessagesDeleteDialog from './email-messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmailMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmailMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmailMessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmailMessages} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EmailMessagesDeleteDialog} />
  </>
);

export default Routes;
