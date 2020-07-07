import React, {useReducer} from 'react';
import {BackendContext} from "./backendContext";
import {backendReducer} from "./backendReducer";
import {FETCH_COUNT_PAGES_ORG, FETCH_PAGE_ORG, SET_PAGE_ORG, SHOW_LOADER} from "../types";

const URL_API = 'http://localhost:8080/api';
const headers = new Headers({
    'Content-Type': 'application/json',
    'Origin': window.location.href
});

export const BackendState = ({children}) => {
    const initialState = {
        loading: false,
        orgs: [],
        pageCount: 0,
        pageNumber: 1,
        pageSize: 5
    };
    const [state, dispatch] = useReducer(backendReducer, initialState);

    const showLoader = () => dispatch({type: SHOW_LOADER});

    const fetchCountPagesOrg = async (pageSize) => {
        const url = `${URL_API}/organizations/pageCount`;

        const ourl = new URL(url);
        ourl.searchParams.append("pageSize", pageSize);

        const requestOptions = {
            method: 'GET',
            headers: headers
        };

        const res = await fetch(ourl.toString(), requestOptions)
            .then(response => response.json())
            .catch(error => console.log('error', error));

        dispatch({
            type: FETCH_COUNT_PAGES_ORG,
            payload: res
        });
    };

    const fetchPageOrg = async (pageSize, pageNumber) => {
        showLoader();
        fetchCountPagesOrg(pageSize);

        const url = `${URL_API}/organizations`;

        const ourl = new URL(url);
        ourl.searchParams.append("pageSize", pageSize);
        ourl.searchParams.append("pageNumber", pageNumber);

        const requestOptions = {
            method: 'GET',
            headers: headers
        };

        const res = await fetch(ourl.toString(), requestOptions)
            .then(result => result.json())
            .catch(error => {
                console.log('Не удалось загрузить данные');
                console.log('error', error)
            });
        const payload = res["pageBody"];

        dispatch({
            type: FETCH_PAGE_ORG,
            payload: payload
        });
    };

    const setPageOrg = (pageNumber) => dispatch({
            type: SET_PAGE_ORG,
            payload: pageNumber
        });

    return (
        <BackendContext.Provider
            value={{ showLoader, fetchCountPagesOrg,
                fetchPageOrg, setPageOrg,
                loading: state.loading,
                orgs: state.orgs,
                pageCount: state.pageCount,
                pageNumber: state.pageNumber,
                pageSize: state.pageSize
            }}>
            {children}
        </BackendContext.Provider>
    );
};