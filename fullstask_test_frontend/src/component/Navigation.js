import React from "react";
import {NavLink} from "react-router-dom";

export const Navigation = () => (
    <nav className="navbar navbar-dark navbar-expand-lg bg-primary">
        <div className="navbar-brand">
            ProIT App
        </div>
        <ul className="navbar-nav">
            <li className="nav-item">
                <NavLink
                    className="nav-link"
                    to="/list_orgs"
                    exact>
                    Список организаций
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink
                    className="nav-link"
                    to="/tree_orgs"
                >
                    Дерево организаций
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink
                    className="nav-link"
                    to="/list_workers"
                >
                    Список работников
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink
                    className="nav-link"
                    to="/tree_workers"
                >
                    Дерево работников
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink
                    className="nav-link"
                    to="/add_org"
                >
                    Добавить организацию
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink
                    className="nav-link"
                    to="/add_worker"
                >
                    Добавить работника
                </NavLink>
            </li>
        </ul>
    </nav>
);