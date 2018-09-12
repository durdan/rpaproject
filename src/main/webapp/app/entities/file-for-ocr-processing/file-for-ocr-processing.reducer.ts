import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFileForOCRProcessing, defaultValue } from 'app/shared/model/file-for-ocr-processing.model';

export const ACTION_TYPES = {
  FETCH_FILEFOROCRPROCESSING_LIST: 'fileForOCRProcessing/FETCH_FILEFOROCRPROCESSING_LIST',
  FETCH_FILEFOROCRPROCESSING: 'fileForOCRProcessing/FETCH_FILEFOROCRPROCESSING',
  CREATE_FILEFOROCRPROCESSING: 'fileForOCRProcessing/CREATE_FILEFOROCRPROCESSING',
  UPDATE_FILEFOROCRPROCESSING: 'fileForOCRProcessing/UPDATE_FILEFOROCRPROCESSING',
  DELETE_FILEFOROCRPROCESSING: 'fileForOCRProcessing/DELETE_FILEFOROCRPROCESSING',
  RESET: 'fileForOCRProcessing/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFileForOCRProcessing>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type FileForOCRProcessingState = Readonly<typeof initialState>;

// Reducer

export default (state: FileForOCRProcessingState = initialState, action): FileForOCRProcessingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FILEFOROCRPROCESSING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILEFOROCRPROCESSING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FILEFOROCRPROCESSING):
    case REQUEST(ACTION_TYPES.UPDATE_FILEFOROCRPROCESSING):
    case REQUEST(ACTION_TYPES.DELETE_FILEFOROCRPROCESSING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FILEFOROCRPROCESSING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILEFOROCRPROCESSING):
    case FAILURE(ACTION_TYPES.CREATE_FILEFOROCRPROCESSING):
    case FAILURE(ACTION_TYPES.UPDATE_FILEFOROCRPROCESSING):
    case FAILURE(ACTION_TYPES.DELETE_FILEFOROCRPROCESSING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FILEFOROCRPROCESSING_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FILEFOROCRPROCESSING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FILEFOROCRPROCESSING):
    case SUCCESS(ACTION_TYPES.UPDATE_FILEFOROCRPROCESSING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FILEFOROCRPROCESSING):
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

const apiUrl = 'api/file-for-ocr-processings';

// Actions

export const getEntities: ICrudGetAllAction<IFileForOCRProcessing> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FILEFOROCRPROCESSING_LIST,
    payload: axios.get<IFileForOCRProcessing>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IFileForOCRProcessing> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FILEFOROCRPROCESSING,
    payload: axios.get<IFileForOCRProcessing>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFileForOCRProcessing> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FILEFOROCRPROCESSING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFileForOCRProcessing> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FILEFOROCRPROCESSING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFileForOCRProcessing> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FILEFOROCRPROCESSING,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
