import React, {Fragment} from "react";

export const PaginationOfOrganizations = ({pageCount, pageNumber, onClick}) => {
    const pages = [];
    for (let i = 1; i <= pageCount; i++)
        pages.push(i);

    return (
        <nav aria-label="Page navigation example">
            <ul className="pagination justify-content-center">
                {
                    (pageNumber !== 1)
                        ? (
                            <Fragment>
                                <li className="page-item">
                                    <button className="page-link" onClick={() => onClick(--pageNumber)} aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </button>
                                </li>
                            </Fragment>
                        )
                        : <Fragment/>
                }

                {
                    pages.map(pageIdx => (
                        <Fragment key={pageIdx}>
                            <li className={`page-item ${pageIdx === pageNumber ? 'active' : ''}`}>
                                <button className="page-link" onClick={() => onClick(pageIdx)}>
                                    {pageIdx}
                                </button>
                            </li>
                        </Fragment>
                    ))
                }

                {
                    (pageNumber !== pageCount)
                        ? (
                            <Fragment>
                                <li className="page-item">
                                    <button className="page-link" onClick={() => onClick(++pageNumber)} aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </button>
                                </li>
                            </Fragment>
                        )
                        : <Fragment/>
                }
            </ul>
        </nav>
    );
};