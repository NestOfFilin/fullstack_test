import {FETCH_COUNT_PAGES_ORG, FETCH_PAGE_ORG, SET_PAGE_ORG, SHOW_LOADER} from "../types";


const handlers = {
    [SHOW_LOADER]: state => ({...state, loading: true}),

    [FETCH_COUNT_PAGES_ORG]: (state, {payload}) => ({
        ...state, pageCount: payload
    }),
    [FETCH_PAGE_ORG]: (state, {payload}) => ({
        ...state, orgs: payload, loading: false
    }),
    [SET_PAGE_ORG]: (state, {payload}) => ({
        ...state, pageNumber: payload
    }),

    DEFAULT: state => state
};

export const backendReducer = (state, action) => {
    const handle = handlers[action.type] || handlers.DEFAULT;
    return handle(state, action);
};