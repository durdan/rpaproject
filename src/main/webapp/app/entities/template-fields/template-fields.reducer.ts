import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITemplateFields, defaultValue } from 'app/shared/model/template-fields.model';

export const ACTION_TYPES = {
  FETCH_TEMPLATEFIELDS_LIST: 'templateFields/FETCH_TEMPLATEFIELDS_LIST',
  FETCH_TEMPLATEFIELDS: 'templateFields/FETCH_TEMPLATEFIELDS',
  CREATE_TEMPLATEFIELDS: 'templateFields/CREATE_TEMPLATEFIELDS',
  UPDATE_TEMPLATEFIELDS: 'templateFields/UPDATE_TEMPLATEFIELDS',
  DELETE_TEMPLATEFIELDS: 'templateFields/DELETE_TEMPLATEFIELDS',
  RESET: 'templateFields/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITemplateFields>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TemplateFieldsState = Readonly<typeof initialState>;

// Reducer

export default (state: TemplateFieldsState = initialState, action): TemplateFieldsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TEMPLATEFIELDS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TEMPLATEFIELDS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TEMPLATEFIELDS):
    case REQUEST(ACTION_TYPES.UPDATE_TEMPLATEFIELDS):
    case REQUEST(ACTION_TYPES.DELETE_TEMPLATEFIELDS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TEMPLATEFIELDS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TEMPLATEFIELDS):
    case FAILURE(ACTION_TYPES.CREATE_TEMPLATEFIELDS):
    case FAILURE(ACTION_TYPES.UPDATE_TEMPLATEFIELDS):
    case FAILURE(ACTION_TYPES.DELETE_TEMPLATEFIELDS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEMPLATEFIELDS_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEMPLATEFIELDS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TEMPLATEFIELDS):
    case SUCCESS(ACTION_TYPES.UPDATE_TEMPLATEFIELDS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TEMPLATEFIELDS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/template-fields';

// Actions

export const getEntities: ICrudGetAllAction<ITemplateFields> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TEMPLATEFIELDS_LIST,
    payload: axios.get<ITemplateFields>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITemplateFields> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TEMPLATEFIELDS,
    payload: axios.get<ITemplateFields>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITemplateFields> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TEMPLATEFIELDS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITemplateFields> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TEMPLATEFIELDS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITemplateFields> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TEMPLATEFIELDS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
