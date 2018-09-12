import { IOutputTemplate } from 'app/shared/model//output-template.model';

export interface IClientDataOcr {
  id?: number;
  keyName?: string;
  value?: string;
  messageId?: string;
  clientEmailAddress?: string;
  attachmentId?: number;
  outputTemplates?: IOutputTemplate[];
  transactionId?: number;
}

export const defaultValue: Readonly<IClientDataOcr> = {};
