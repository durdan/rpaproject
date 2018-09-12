import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OcrProcessingError from './ocr-processing-error';
import OcrProcessingErrorDetail from './ocr-processing-error-detail';
import OcrProcessingErrorUpdate from './ocr-processing-error-update';
import OcrProcessingErrorDeleteDialog from './ocr-processing-error-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OcrProcessingErrorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OcrProcessingErrorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OcrProcessingErrorDetail} />
      <ErrorBoundaryRoute path={match.url} component={OcrProcessingError} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OcrProcessingErrorDeleteDialog} />
  </>
);

export default Routes;
