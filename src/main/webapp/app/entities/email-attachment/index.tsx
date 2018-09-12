import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmailAttachment from './email-attachment';
import EmailAttachmentDetail from './email-attachment-detail';
import EmailAttachmentUpdate from './email-attachment-update';
import EmailAttachmentDeleteDialog from './email-attachment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmailAttachmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmailAttachmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmailAttachmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmailAttachment} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EmailAttachmentDeleteDialog} />
  </>
);

export default Routes;
