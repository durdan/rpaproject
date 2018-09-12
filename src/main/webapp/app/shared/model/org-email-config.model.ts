import { Moment } from 'moment';

export interface IOrgEmailConfig {
  id?: number;
  emailServerHost?: string;
  emailServerPort?: number;
  emailServerUserId?: string;
  emailServerPassword?: string;
  createDate?: Moment;
  createdBy?: string;
  updateDate?: Moment;
  updatedBy?: string;
  orgNameOrgName?: string;
  orgNameId?: number;
}

export const defaultValue: Readonly<IOrgEmailConfig> = {};
