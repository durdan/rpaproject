export interface ITemplateFields {
  id?: number;
  fieldName?: string;
  fieldZoneMinX?: number;
  fieldZoneMinY?: number;
  fieldZoneMaxX?: number;
  fieldZoneMaxY?: number;
  fieldValidationRequire?: number;
  fieldValidationRule?: string;
  inputTemplateId?: number;
}

export const defaultValue: Readonly<ITemplateFields> = {};
