import React, { useState } from "react";
import "./PackageViewOptionBar.css"

const PackageViewOptionBar = () => {

    const [categories, setCategories] = useState([
        {
            name: "category 1"
        },
        {
            name: "category 2"
        },
        {
            name: "category 3"
        },
        {
            name: "category 4"
        },
        {
            name: "category 5"
        },
        {
            name: "category 2"
        },
        {
            name: "category 3"
        },
        {
            name: "category 4"
        },
        {
            name: "category 5"
        },
        {
            name: "category 2"
        },
        {
            name: "category 3"
        },
        {
            name: "category 4"
        },
        {
            name: "category 5"
        },
        {
            name: "category 2"
        },
        {
            name: "category 3"
        },
        {
            name: "category 4"
        },
        {
            name: "category 5"
        },
    ]);

    return(
        <>
        <div className="package-view-option">
            <div className="all-tab">
                <button>All</button>
            </div>
            <div className="category-tabs">
                {categories.map((item, index) => {
                    return(
                        <button key={index}>{item.name}</button>
                    )
                })}
            </div>
            <div className="search-input">
                <form onSubmit="">
                    <input type="text" placeholder="Search"/>
                    <button type="submit">Search</button>
                </form>
            </div>
        </div>
        </>
    )
}

export default PackageViewOptionBar;