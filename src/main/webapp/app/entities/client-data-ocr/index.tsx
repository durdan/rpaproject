import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClientDataOcr from './client-data-ocr';
import ClientDataOcrDetail from './client-data-ocr-detail';
import ClientDataOcrUpdate from './client-data-ocr-update';
import ClientDataOcrDeleteDialog from './client-data-ocr-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClientDataOcrUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClientDataOcrUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClientDataOcrDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClientDataOcr} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ClientDataOcrDeleteDialog} />
  </>
);

export default Routes;
