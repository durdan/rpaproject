import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FileToFtp from './file-to-ftp';
import FileToFtpDetail from './file-to-ftp-detail';
import FileToFtpUpdate from './file-to-ftp-update';
import FileToFtpDeleteDialog from './file-to-ftp-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FileToFtpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FileToFtpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FileToFtpDetail} />
      <ErrorBoundaryRoute path={match.url} component={FileToFtp} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FileToFtpDeleteDialog} />
  </>
);

export default Routes;
