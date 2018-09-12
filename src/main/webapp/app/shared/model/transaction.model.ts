import { Moment } from 'moment';
import { IClientDataOcr } from 'app/shared/model//client-data-ocr.model';

export interface ITransaction {
  id?: number;
  createdDateTime?: Moment;
  status?: string;
  clientEmailAddress?: string;
  messageId?: string;
  fileName?: string;
  processType?: string;
  createDate?: Moment;
  createdBy?: string;
  updateDate?: Moment;
  updatedBy?: string;
  fileForOCRProcessingId?: number;
  ocrProcessingErrorId?: number;
  clientDataOcrs?: IClientDataOcr[];
}

export const defaultValue: Readonly<ITransaction> = {};
