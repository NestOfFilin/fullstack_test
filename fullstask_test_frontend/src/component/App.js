import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import {Navigation} from "./Navigation";
import {PageListOfOrgs} from "./page/PageListOfOrgs";
import {TreeOfOrgs} from "./page/TreeOfOrgs";
import {PageListOfWorkers} from "./page/PageListOfWorkers";
import {TreeOfWorkers} from "./page/TreeOfWorkers";
import {AddFormOfOrg} from "./page/AddFormOfOrg";
import {AddFormOfWorker} from "./page/AddFormOfWorker";
import {Alert} from "./Alert";
import {AlertState} from "../context/alert/AlertState";
import {BackendState} from "../context/backend/BackendState";

function App() {

    return (
        <BackendState>
            <AlertState>
                <BrowserRouter>
                    <Navigation/>
                    <div className="container pt-4">
                        <Alert/>
                        <Switch>
                            <Route path={'/list_orgs'} exact component={PageListOfOrgs}/>
                            <Route path={'/tree_orgs'} component={TreeOfOrgs}/>
                            <Route path={'/list_workers'} component={PageListOfWorkers}/>
                            <Route path={'/tree_workers'} component={TreeOfWorkers}/>
                            <Route path={'/add_org'} component={AddFormOfOrg}/>
                            <Route path={'/add_worker'} component={AddFormOfWorker}/>
                        </Switch>
                    </div>
                </BrowserRouter>
            </AlertState>
        </BackendState>
    );
}

export default App;
