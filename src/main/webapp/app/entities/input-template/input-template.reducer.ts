import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInputTemplate, defaultValue } from 'app/shared/model/input-template.model';

export const ACTION_TYPES = {
  FETCH_INPUTTEMPLATE_LIST: 'inputTemplate/FETCH_INPUTTEMPLATE_LIST',
  FETCH_INPUTTEMPLATE: 'inputTemplate/FETCH_INPUTTEMPLATE',
  CREATE_INPUTTEMPLATE: 'inputTemplate/CREATE_INPUTTEMPLATE',
  UPDATE_INPUTTEMPLATE: 'inputTemplate/UPDATE_INPUTTEMPLATE',
  DELETE_INPUTTEMPLATE: 'inputTemplate/DELETE_INPUTTEMPLATE',
  RESET: 'inputTemplate/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInputTemplate>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type InputTemplateState = Readonly<typeof initialState>;

// Reducer

export default (state: InputTemplateState = initialState, action): InputTemplateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_INPUTTEMPLATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INPUTTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_INPUTTEMPLATE):
    case REQUEST(ACTION_TYPES.UPDATE_INPUTTEMPLATE):
    case REQUEST(ACTION_TYPES.DELETE_INPUTTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_INPUTTEMPLATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INPUTTEMPLATE):
    case FAILURE(ACTION_TYPES.CREATE_INPUTTEMPLATE):
    case FAILURE(ACTION_TYPES.UPDATE_INPUTTEMPLATE):
    case FAILURE(ACTION_TYPES.DELETE_INPUTTEMPLATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_INPUTTEMPLATE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_INPUTTEMPLATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_INPUTTEMPLATE):
    case SUCCESS(ACTION_TYPES.UPDATE_INPUTTEMPLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_INPUTTEMPLATE):
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

const apiUrl = 'api/input-templates';

// Actions

export const getEntities: ICrudGetAllAction<IInputTemplate> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_INPUTTEMPLATE_LIST,
    payload: axios.get<IInputTemplate>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IInputTemplate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INPUTTEMPLATE,
    payload: axios.get<IInputTemplate>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IInputTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INPUTTEMPLATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInputTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INPUTTEMPLATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInputTemplate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INPUTTEMPLATE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
