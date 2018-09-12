export interface IEmailAttachment {
  id?: number;
  messageId?: string;
  clientEmailAddress?: string;
  fileName?: string;
  fileExtension?: string;
  fileLocation?: string;
  fileForOCRProcessingId?: number;
  emailMessagesMessageId?: string;
  emailMessagesId?: number;
}

export const defaultValue: Readonly<IEmailAttachment> = {};
