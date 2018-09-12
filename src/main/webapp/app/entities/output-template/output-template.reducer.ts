import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOutputTemplate, defaultValue } from 'app/shared/model/output-template.model';

export const ACTION_TYPES = {
  FETCH_OUTPUTTEMPLATE_LIST: 'outputTemplate/FETCH_OUTPUTTEMPLATE_LIST',
  FETCH_OUTPUTTEMPLATE: 'outputTemplate/FETCH_OUTPUTTEMPLATE',
  CREATE_OUTPUTTEMPLATE: 'outputTemplate/CREATE_OUTPUTTEMPLATE',
  UPDATE_OUTPUTTEMPLATE: 'outputTemplate/UPDATE_OUTPUTTEMPLATE',
  DELETE_OUTPUTTEMPLATE: 'outputTemplate/DELETE_OUTPUTTEMPLATE',
  RESET: 'outputTemplate/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOutputTemplate>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type OutputTemplateState = Readonly<typeof initialState>;

// Reducer

export default (state: OutputTemplateState = initialState, action): OutputTemplateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OUTPUTTEMPLATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OUTPUTTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_OUTPUTTEMPLATE):
    case REQUEST(ACTION_TYPES.UPDATE_OUTPUTTEMPLATE):
    case REQUEST(ACTION_TYPES.DELETE_OUTPUTTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_OUTPUTTEMPLATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OUTPUTTEMPLATE):
    case FAILURE(ACTION_TYPES.CREATE_OUTPUTTEMPLATE):
    case FAILURE(ACTION_TYPES.UPDATE_OUTPUTTEMPLATE):
    case FAILURE(ACTION_TYPES.DELETE_OUTPUTTEMPLATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_OUTPUTTEMPLATE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_OUTPUTTEMPLATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_OUTPUTTEMPLATE):
    case SUCCESS(ACTION_TYPES.UPDATE_OUTPUTTEMPLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_OUTPUTTEMPLATE):
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

const apiUrl = 'api/output-templates';

// Actions

export const getEntities: ICrudGetAllAction<IOutputTemplate> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_OUTPUTTEMPLATE_LIST,
    payload: axios.get<IOutputTemplate>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IOutputTemplate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OUTPUTTEMPLATE,
    payload: axios.get<IOutputTemplate>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOutputTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OUTPUTTEMPLATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOutputTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OUTPUTTEMPLATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOutputTemplate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OUTPUTTEMPLATE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
