import React from "react";

function Sort(props) {

    return (
        <>
            <select className="package-sorting" onChange={props.onChangeSortingOrder}>
                <option value="asc">A-Z</option>
                <option value="desc">Z-A</option>
            </select>
        </>
    )

}

export default Sort;