export interface IOutputTemplate {
  id?: number;
  outputTemplateName?: string;
  fieldName?: string;
  position?: string;
  fieldValidationRequire?: number;
  fieldValidationRule?: string;
  clientId?: number;
  clientDataOcrId?: number;
}

export const defaultValue: Readonly<IOutputTemplate> = {};
