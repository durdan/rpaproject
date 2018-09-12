import { Moment } from 'moment';

export interface IInputTemplate {
  id?: number;
  templateName?: string;
  templateDescription?: string;
  isActive?: number;
  createDate?: Moment;
  createdBy?: string;
  updateDate?: Moment;
  updatedBy?: string;
  clientClientEmailAddress?: string;
  clientId?: number;
}

export const defaultValue: Readonly<IInputTemplate> = {};
