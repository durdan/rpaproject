export interface IFileToFtp {
  id?: number;
  messageId?: string;
  clientEmailAddress?: string;
  status?: string;
  fileType?: string;
  clientDataOcrClientEmailAddress?: string;
  clientDataOcrId?: number;
}

export const defaultValue: Readonly<IFileToFtp> = {};
