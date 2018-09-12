import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FileForOCRProcessing from './file-for-ocr-processing';
import FileForOCRProcessingDetail from './file-for-ocr-processing-detail';
import FileForOCRProcessingUpdate from './file-for-ocr-processing-update';
import FileForOCRProcessingDeleteDialog from './file-for-ocr-processing-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FileForOCRProcessingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FileForOCRProcessingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FileForOCRProcessingDetail} />
      <ErrorBoundaryRoute path={match.url} component={FileForOCRProcessing} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FileForOCRProcessingDeleteDialog} />
  </>
);

export default Routes;
