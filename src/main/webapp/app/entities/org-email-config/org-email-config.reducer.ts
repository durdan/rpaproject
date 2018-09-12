import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrgEmailConfig, defaultValue } from 'app/shared/model/org-email-config.model';

export const ACTION_TYPES = {
  FETCH_ORGEMAILCONFIG_LIST: 'orgEmailConfig/FETCH_ORGEMAILCONFIG_LIST',
  FETCH_ORGEMAILCONFIG: 'orgEmailConfig/FETCH_ORGEMAILCONFIG',
  CREATE_ORGEMAILCONFIG: 'orgEmailConfig/CREATE_ORGEMAILCONFIG',
  UPDATE_ORGEMAILCONFIG: 'orgEmailConfig/UPDATE_ORGEMAILCONFIG',
  DELETE_ORGEMAILCONFIG: 'orgEmailConfig/DELETE_ORGEMAILCONFIG',
  RESET: 'orgEmailConfig/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrgEmailConfig>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type OrgEmailConfigState = Readonly<typeof initialState>;

// Reducer

export default (state: OrgEmailConfigState = initialState, action): OrgEmailConfigState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORGEMAILCONFIG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORGEMAILCONFIG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORGEMAILCONFIG):
    case REQUEST(ACTION_TYPES.UPDATE_ORGEMAILCONFIG):
    case REQUEST(ACTION_TYPES.DELETE_ORGEMAILCONFIG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ORGEMAILCONFIG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORGEMAILCONFIG):
    case FAILURE(ACTION_TYPES.CREATE_ORGEMAILCONFIG):
    case FAILURE(ACTION_TYPES.UPDATE_ORGEMAILCONFIG):
    case FAILURE(ACTION_TYPES.DELETE_ORGEMAILCONFIG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGEMAILCONFIG_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGEMAILCONFIG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORGEMAILCONFIG):
    case SUCCESS(ACTION_TYPES.UPDATE_ORGEMAILCONFIG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORGEMAILCONFIG):
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

const apiUrl = 'api/org-email-configs';

// Actions

export const getEntities: ICrudGetAllAction<IOrgEmailConfig> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ORGEMAILCONFIG_LIST,
    payload: axios.get<IOrgEmailConfig>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IOrgEmailConfig> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORGEMAILCONFIG,
    payload: axios.get<IOrgEmailConfig>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrgEmailConfig> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORGEMAILCONFIG,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrgEmailConfig> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORGEMAILCONFIG,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrgEmailConfig> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORGEMAILCONFIG,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
