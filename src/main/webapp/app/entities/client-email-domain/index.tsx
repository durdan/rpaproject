import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClientEmailDomain from './client-email-domain';
import ClientEmailDomainDetail from './client-email-domain-detail';
import ClientEmailDomainUpdate from './client-email-domain-update';
import ClientEmailDomainDeleteDialog from './client-email-domain-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClientEmailDomainUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClientEmailDomainUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClientEmailDomainDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClientEmailDomain} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ClientEmailDomainDeleteDialog} />
  </>
);

export default Routes;
