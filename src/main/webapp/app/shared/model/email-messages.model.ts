import { Moment } from 'moment';
import { IEmailAttachment } from 'app/shared/model//email-attachment.model';

export interface IEmailMessages {
  id?: number;
  messageId?: string;
  emailSubject?: string;
  emailBody?: string;
  status?: string;
  clientEmailAddress?: string;
  receiveFrom?: string;
  receivedTime?: Moment;
  numberOfAttachments?: number;
  attachments?: string;
  emailAttachments?: IEmailAttachment[];
  clientClientEmailAddress?: string;
  clientId?: number;
  emailProcessingErrorId?: number;
}

export const defaultValue: Readonly<IEmailMessages> = {};
