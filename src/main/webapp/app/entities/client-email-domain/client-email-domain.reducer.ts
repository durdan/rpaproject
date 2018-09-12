import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClientEmailDomain, defaultValue } from 'app/shared/model/client-email-domain.model';

export const ACTION_TYPES = {
  FETCH_CLIENTEMAILDOMAIN_LIST: 'clientEmailDomain/FETCH_CLIENTEMAILDOMAIN_LIST',
  FETCH_CLIENTEMAILDOMAIN: 'clientEmailDomain/FETCH_CLIENTEMAILDOMAIN',
  CREATE_CLIENTEMAILDOMAIN: 'clientEmailDomain/CREATE_CLIENTEMAILDOMAIN',
  UPDATE_CLIENTEMAILDOMAIN: 'clientEmailDomain/UPDATE_CLIENTEMAILDOMAIN',
  DELETE_CLIENTEMAILDOMAIN: 'clientEmailDomain/DELETE_CLIENTEMAILDOMAIN',
  RESET: 'clientEmailDomain/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClientEmailDomain>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ClientEmailDomainState = Readonly<typeof initialState>;

// Reducer

export default (state: ClientEmailDomainState = initialState, action): ClientEmailDomainState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLIENTEMAILDOMAIN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLIENTEMAILDOMAIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CLIENTEMAILDOMAIN):
    case REQUEST(ACTION_TYPES.UPDATE_CLIENTEMAILDOMAIN):
    case REQUEST(ACTION_TYPES.DELETE_CLIENTEMAILDOMAIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CLIENTEMAILDOMAIN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLIENTEMAILDOMAIN):
    case FAILURE(ACTION_TYPES.CREATE_CLIENTEMAILDOMAIN):
    case FAILURE(ACTION_TYPES.UPDATE_CLIENTEMAILDOMAIN):
    case FAILURE(ACTION_TYPES.DELETE_CLIENTEMAILDOMAIN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTEMAILDOMAIN_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTEMAILDOMAIN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLIENTEMAILDOMAIN):
    case SUCCESS(ACTION_TYPES.UPDATE_CLIENTEMAILDOMAIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLIENTEMAILDOMAIN):
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

const apiUrl = 'api/client-email-domains';

// Actions

export const getEntities: ICrudGetAllAction<IClientEmailDomain> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTEMAILDOMAIN_LIST,
    payload: axios.get<IClientEmailDomain>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IClientEmailDomain> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTEMAILDOMAIN,
    payload: axios.get<IClientEmailDomain>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IClientEmailDomain> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLIENTEMAILDOMAIN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClientEmailDomain> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLIENTEMAILDOMAIN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClientEmailDomain> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLIENTEMAILDOMAIN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
