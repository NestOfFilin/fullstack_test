import React, {Fragment} from "react";

export const ListOfOrganizations = ({listOfOrganizations}) => {

    return (
        <ul className="list-group">
            {listOfOrganizations.map(organization => (
                < li
                    className="list-group-item note"
                    key={organization.data["id"]}>
                    <div>
                        <strong>{organization.data["name"]}</strong>
                        <small>
                            ({organization.data["workersCount"]})
                        </small>
                    </div>
                    {!organization.flagLeaf
                        ? <button type="button" className="btn btn-outline-danger btn-sm">
                            &times;
                            </button>
                        : <Fragment/>
                    }
                </li>
            ))}
        </ul>
    )
};