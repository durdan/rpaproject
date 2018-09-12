import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrgEmailConfig from './org-email-config';
import OrgEmailConfigDetail from './org-email-config-detail';
import OrgEmailConfigUpdate from './org-email-config-update';
import OrgEmailConfigDeleteDialog from './org-email-config-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrgEmailConfigUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrgEmailConfigUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrgEmailConfigDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrgEmailConfig} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OrgEmailConfigDeleteDialog} />
  </>
);

export default Routes;
