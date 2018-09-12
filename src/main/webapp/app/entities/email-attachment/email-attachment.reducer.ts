import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmailAttachment, defaultValue } from 'app/shared/model/email-attachment.model';

export const ACTION_TYPES = {
  FETCH_EMAILATTACHMENT_LIST: 'emailAttachment/FETCH_EMAILATTACHMENT_LIST',
  FETCH_EMAILATTACHMENT: 'emailAttachment/FETCH_EMAILATTACHMENT',
  CREATE_EMAILATTACHMENT: 'emailAttachment/CREATE_EMAILATTACHMENT',
  UPDATE_EMAILATTACHMENT: 'emailAttachment/UPDATE_EMAILATTACHMENT',
  DELETE_EMAILATTACHMENT: 'emailAttachment/DELETE_EMAILATTACHMENT',
  RESET: 'emailAttachment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmailAttachment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EmailAttachmentState = Readonly<typeof initialState>;

// Reducer

export default (state: EmailAttachmentState = initialState, action): EmailAttachmentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMAILATTACHMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMAILATTACHMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMAILATTACHMENT):
    case REQUEST(ACTION_TYPES.UPDATE_EMAILATTACHMENT):
    case REQUEST(ACTION_TYPES.DELETE_EMAILATTACHMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMAILATTACHMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMAILATTACHMENT):
    case FAILURE(ACTION_TYPES.CREATE_EMAILATTACHMENT):
    case FAILURE(ACTION_TYPES.UPDATE_EMAILATTACHMENT):
    case FAILURE(ACTION_TYPES.DELETE_EMAILATTACHMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMAILATTACHMENT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMAILATTACHMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMAILATTACHMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_EMAILATTACHMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMAILATTACHMENT):
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

const apiUrl = 'api/email-attachments';

// Actions

export const getEntities: ICrudGetAllAction<IEmailAttachment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMAILATTACHMENT_LIST,
    payload: axios.get<IEmailAttachment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEmailAttachment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMAILATTACHMENT,
    payload: axios.get<IEmailAttachment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmailAttachment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMAILATTACHMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmailAttachment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMAILATTACHMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmailAttachment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMAILATTACHMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
