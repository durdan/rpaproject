import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFileToFtp, defaultValue } from 'app/shared/model/file-to-ftp.model';

export const ACTION_TYPES = {
  FETCH_FILETOFTP_LIST: 'fileToFtp/FETCH_FILETOFTP_LIST',
  FETCH_FILETOFTP: 'fileToFtp/FETCH_FILETOFTP',
  CREATE_FILETOFTP: 'fileToFtp/CREATE_FILETOFTP',
  UPDATE_FILETOFTP: 'fileToFtp/UPDATE_FILETOFTP',
  DELETE_FILETOFTP: 'fileToFtp/DELETE_FILETOFTP',
  RESET: 'fileToFtp/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFileToFtp>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type FileToFtpState = Readonly<typeof initialState>;

// Reducer

export default (state: FileToFtpState = initialState, action): FileToFtpState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FILETOFTP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILETOFTP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FILETOFTP):
    case REQUEST(ACTION_TYPES.UPDATE_FILETOFTP):
    case REQUEST(ACTION_TYPES.DELETE_FILETOFTP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FILETOFTP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILETOFTP):
    case FAILURE(ACTION_TYPES.CREATE_FILETOFTP):
    case FAILURE(ACTION_TYPES.UPDATE_FILETOFTP):
    case FAILURE(ACTION_TYPES.DELETE_FILETOFTP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FILETOFTP_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FILETOFTP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FILETOFTP):
    case SUCCESS(ACTION_TYPES.UPDATE_FILETOFTP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FILETOFTP):
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

const apiUrl = 'api/file-to-ftps';

// Actions

export const getEntities: ICrudGetAllAction<IFileToFtp> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FILETOFTP_LIST,
    payload: axios.get<IFileToFtp>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IFileToFtp> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FILETOFTP,
    payload: axios.get<IFileToFtp>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFileToFtp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FILETOFTP,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFileToFtp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FILETOFTP,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFileToFtp> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FILETOFTP,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
