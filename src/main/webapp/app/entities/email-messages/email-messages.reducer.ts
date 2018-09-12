import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmailMessages, defaultValue } from 'app/shared/model/email-messages.model';

export const ACTION_TYPES = {
  FETCH_EMAILMESSAGES_LIST: 'emailMessages/FETCH_EMAILMESSAGES_LIST',
  FETCH_EMAILMESSAGES: 'emailMessages/FETCH_EMAILMESSAGES',
  CREATE_EMAILMESSAGES: 'emailMessages/CREATE_EMAILMESSAGES',
  UPDATE_EMAILMESSAGES: 'emailMessages/UPDATE_EMAILMESSAGES',
  DELETE_EMAILMESSAGES: 'emailMessages/DELETE_EMAILMESSAGES',
  RESET: 'emailMessages/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmailMessages>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EmailMessagesState = Readonly<typeof initialState>;

// Reducer

export default (state: EmailMessagesState = initialState, action): EmailMessagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMAILMESSAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMAILMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMAILMESSAGES):
    case REQUEST(ACTION_TYPES.UPDATE_EMAILMESSAGES):
    case REQUEST(ACTION_TYPES.DELETE_EMAILMESSAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMAILMESSAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMAILMESSAGES):
    case FAILURE(ACTION_TYPES.CREATE_EMAILMESSAGES):
    case FAILURE(ACTION_TYPES.UPDATE_EMAILMESSAGES):
    case FAILURE(ACTION_TYPES.DELETE_EMAILMESSAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMAILMESSAGES_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMAILMESSAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMAILMESSAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_EMAILMESSAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMAILMESSAGES):
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

const apiUrl = 'api/email-messages';

// Actions

export const getEntities: ICrudGetAllAction<IEmailMessages> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMAILMESSAGES_LIST,
    payload: axios.get<IEmailMessages>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEmailMessages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMAILMESSAGES,
    payload: axios.get<IEmailMessages>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmailMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMAILMESSAGES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmailMessages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMAILMESSAGES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmailMessages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMAILMESSAGES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
