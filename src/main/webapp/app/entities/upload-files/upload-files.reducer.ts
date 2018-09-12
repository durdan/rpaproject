import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUploadFiles, defaultValue } from 'app/shared/model/upload-files.model';

export const ACTION_TYPES = {
  FETCH_UPLOADFILES_LIST: 'uploadFiles/FETCH_UPLOADFILES_LIST',
  FETCH_UPLOADFILES: 'uploadFiles/FETCH_UPLOADFILES',
  CREATE_UPLOADFILES: 'uploadFiles/CREATE_UPLOADFILES',
  UPDATE_UPLOADFILES: 'uploadFiles/UPDATE_UPLOADFILES',
  DELETE_UPLOADFILES: 'uploadFiles/DELETE_UPLOADFILES',
  RESET: 'uploadFiles/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUploadFiles>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type UploadFilesState = Readonly<typeof initialState>;

// Reducer

export default (state: UploadFilesState = initialState, action): UploadFilesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_UPLOADFILES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UPLOADFILES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_UPLOADFILES):
    case REQUEST(ACTION_TYPES.UPDATE_UPLOADFILES):
    case REQUEST(ACTION_TYPES.DELETE_UPLOADFILES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_UPLOADFILES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UPLOADFILES):
    case FAILURE(ACTION_TYPES.CREATE_UPLOADFILES):
    case FAILURE(ACTION_TYPES.UPDATE_UPLOADFILES):
    case FAILURE(ACTION_TYPES.DELETE_UPLOADFILES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_UPLOADFILES_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_UPLOADFILES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_UPLOADFILES):
    case SUCCESS(ACTION_TYPES.UPDATE_UPLOADFILES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_UPLOADFILES):
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

const apiUrl = 'api/upload-files';

// Actions

export const getEntities: ICrudGetAllAction<IUploadFiles> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_UPLOADFILES_LIST,
    payload: axios.get<IUploadFiles>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUploadFiles> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UPLOADFILES,
    payload: axios.get<IUploadFiles>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUploadFiles> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UPLOADFILES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUploadFiles> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UPLOADFILES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUploadFiles> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UPLOADFILES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
