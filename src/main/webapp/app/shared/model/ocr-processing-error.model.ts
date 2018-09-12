import { Moment } from 'moment';

export interface IOcrProcessingError {
  id?: number;
  clientEmailAddress?: string;
  messageId?: string;
  attachmentId?: number;
  fileId?: string;
  errorMessage?: string;
  createdDateTime?: Moment;
  errorType?: string;
  transactionId?: number;
}

export const defaultValue: Readonly<IOcrProcessingError> = {};
