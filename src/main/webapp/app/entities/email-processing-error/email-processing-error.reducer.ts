import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmailProcessingError, defaultValue } from 'app/shared/model/email-processing-error.model';

export const ACTION_TYPES = {
  FETCH_EMAILPROCESSINGERROR_LIST: 'emailProcessingError/FETCH_EMAILPROCESSINGERROR_LIST',
  FETCH_EMAILPROCESSINGERROR: 'emailProcessingError/FETCH_EMAILPROCESSINGERROR',
  CREATE_EMAILPROCESSINGERROR: 'emailProcessingError/CREATE_EMAILPROCESSINGERROR',
  UPDATE_EMAILPROCESSINGERROR: 'emailProcessingError/UPDATE_EMAILPROCESSINGERROR',
  DELETE_EMAILPROCESSINGERROR: 'emailProcessingError/DELETE_EMAILPROCESSINGERROR',
  RESET: 'emailProcessingError/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmailProcessingError>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EmailProcessingErrorState = Readonly<typeof initialState>;

// Reducer

export default (state: EmailProcessingErrorState = initialState, action): EmailProcessingErrorState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMAILPROCESSINGERROR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMAILPROCESSINGERROR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMAILPROCESSINGERROR):
    case REQUEST(ACTION_TYPES.UPDATE_EMAILPROCESSINGERROR):
    case REQUEST(ACTION_TYPES.DELETE_EMAILPROCESSINGERROR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMAILPROCESSINGERROR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMAILPROCESSINGERROR):
    case FAILURE(ACTION_TYPES.CREATE_EMAILPROCESSINGERROR):
    case FAILURE(ACTION_TYPES.UPDATE_EMAILPROCESSINGERROR):
    case FAILURE(ACTION_TYPES.DELETE_EMAILPROCESSINGERROR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMAILPROCESSINGERROR_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMAILPROCESSINGERROR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMAILPROCESSINGERROR):
    case SUCCESS(ACTION_TYPES.UPDATE_EMAILPROCESSINGERROR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMAILPROCESSINGERROR):
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

const apiUrl = 'api/email-processing-errors';

// Actions

export const getEntities: ICrudGetAllAction<IEmailProcessingError> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMAILPROCESSINGERROR_LIST,
    payload: axios.get<IEmailProcessingError>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEmailProcessingError> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMAILPROCESSINGERROR,
    payload: axios.get<IEmailProcessingError>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmailProcessingError> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMAILPROCESSINGERROR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmailProcessingError> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMAILPROCESSINGERROR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmailProcessingError> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMAILPROCESSINGERROR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
