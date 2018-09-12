import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClientDataOcr, defaultValue } from 'app/shared/model/client-data-ocr.model';

export const ACTION_TYPES = {
  FETCH_CLIENTDATAOCR_LIST: 'clientDataOcr/FETCH_CLIENTDATAOCR_LIST',
  FETCH_CLIENTDATAOCR: 'clientDataOcr/FETCH_CLIENTDATAOCR',
  CREATE_CLIENTDATAOCR: 'clientDataOcr/CREATE_CLIENTDATAOCR',
  UPDATE_CLIENTDATAOCR: 'clientDataOcr/UPDATE_CLIENTDATAOCR',
  DELETE_CLIENTDATAOCR: 'clientDataOcr/DELETE_CLIENTDATAOCR',
  RESET: 'clientDataOcr/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClientDataOcr>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ClientDataOcrState = Readonly<typeof initialState>;

// Reducer

export default (state: ClientDataOcrState = initialState, action): ClientDataOcrState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLIENTDATAOCR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLIENTDATAOCR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CLIENTDATAOCR):
    case REQUEST(ACTION_TYPES.UPDATE_CLIENTDATAOCR):
    case REQUEST(ACTION_TYPES.DELETE_CLIENTDATAOCR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CLIENTDATAOCR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLIENTDATAOCR):
    case FAILURE(ACTION_TYPES.CREATE_CLIENTDATAOCR):
    case FAILURE(ACTION_TYPES.UPDATE_CLIENTDATAOCR):
    case FAILURE(ACTION_TYPES.DELETE_CLIENTDATAOCR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTDATAOCR_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTDATAOCR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLIENTDATAOCR):
    case SUCCESS(ACTION_TYPES.UPDATE_CLIENTDATAOCR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLIENTDATAOCR):
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

const apiUrl = 'api/client-data-ocrs';

// Actions

export const getEntities: ICrudGetAllAction<IClientDataOcr> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTDATAOCR_LIST,
    payload: axios.get<IClientDataOcr>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IClientDataOcr> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTDATAOCR,
    payload: axios.get<IClientDataOcr>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IClientDataOcr> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLIENTDATAOCR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClientDataOcr> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLIENTDATAOCR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClientDataOcr> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLIENTDATAOCR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
