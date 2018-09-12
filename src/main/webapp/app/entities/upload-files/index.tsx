import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UploadFiles from './upload-files';
import UploadFilesDetail from './upload-files-detail';
import UploadFilesUpdate from './upload-files-update';
import UploadFilesDeleteDialog from './upload-files-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UploadFilesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UploadFilesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UploadFilesDetail} />
      <ErrorBoundaryRoute path={match.url} component={UploadFiles} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UploadFilesDeleteDialog} />
  </>
);

export default Routes;
