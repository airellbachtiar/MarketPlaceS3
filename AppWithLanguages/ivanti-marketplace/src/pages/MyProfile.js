import React, { useState } from "react";
import * as CgIcons from "react-icons/cg";
import { NavLink } from "react-router-dom";
import "../Components/styles/MyProfile.css"

const MyProfile = () => {

    const [profileDetail] = useState({
        "username": "TestUser",
        "email": "test@email.com",
        "description": "Test Description"
    });

    return(
        <div className="myprofile">
            <div className="myprofile-container">
                <div className="myprofile-picture">
                    <div className="myprofile-profilepicture">
                        <CgIcons.CgProfile />
                    </div>
                    <div className="myprofile-edit">
                        <span><NavLink to="/editprofile">edit</NavLink></span>
                    </div>
                </div>
                <div className="myprofile-detail">
                    {profileDetail.username}
                </div>
                <div className="myprofile-detail">
                    {profileDetail.email}
                </div>
                <div className="myprofile-detail">
                    {profileDetail.description}
                </div>
            </div>
        </div>
    )
}

export default MyProfile;