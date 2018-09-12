export interface IClientEmailDomain {
  id?: number;
  emailDomain?: string;
  description?: string;
  isActive?: number;
  clientClientName?: string;
  clientId?: number;
}

export const defaultValue: Readonly<IClientEmailDomain> = {};
