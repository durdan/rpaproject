import { Moment } from 'moment';

export interface IOrganization {
  id?: number;
  orgName?: string;
  description?: string;
  orgAddress?: string;
  orgEmail?: string;
  isActive?: number;
  createDate?: Moment;
  createdBy?: string;
  updateDate?: Moment;
  updatedBy?: string;
}

export const defaultValue: Readonly<IOrganization> = {};
