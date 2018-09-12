import { Moment } from 'moment';

export interface IFileForOCRProcessing {
  id?: number;
  fileInputType?: string;
  status?: string;
  messageId?: string;
  clientEmailAddress?: string;
  retry?: string;
  createdDateTime?: Moment;
  createdBy?: string;
  updateTimeStamp?: Moment;
  updateBy?: Moment;
  emailAttachmentId?: number;
  uploadFilesId?: number;
  transactionId?: number;
}

export const defaultValue: Readonly<IFileForOCRProcessing> = {};
