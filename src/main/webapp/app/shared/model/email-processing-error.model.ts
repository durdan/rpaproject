import { Moment } from 'moment';

export interface IEmailProcessingError {
  id?: number;
  errorMessage?: string;
  messageID?: string;
  clientEmailAddress?: string;
  receiveFrom?: string;
  receivedTime?: Moment;
  emailMessagesId?: number;
}

export const defaultValue: Readonly<IEmailProcessingError> = {};
