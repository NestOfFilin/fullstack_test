import React, {Fragment, useContext, useEffect} from "react";
import {ListOfOrganizations} from "../ListOfOrganizations";
import {Loader} from "../Loader";
import {BackendContext} from "../../context/backend/backendContext";
import {PaginationOfOrganizations} from "../PaginationOfOrganizations";

export const PageListOfOrgs = () => {
    const {loading, orgs, pageCount, pageNumber, pageSize,
        fetchPageOrg, setPageOrg} = useContext(BackendContext);

    useEffect(() => {
        if (!loading)
            fetchPageOrg(pageSize, pageNumber);
        // eslint-disable-next-line
    }, []);

    const onClick = (pgNumber) => {
        setPageOrg(pgNumber);
        fetchPageOrg(pageSize, pgNumber);
    };

    return (
        <Fragment>
            {
                loading
                ? <Loader/>
                : (
                    <div>
                        <ListOfOrganizations listOfOrganizations={orgs}/>
                        <PaginationOfOrganizations pageCount={pageCount} pageNumber={pageNumber} onClick={onClick}/>
                    </div>
                )
            }
        </Fragment>
    );
};