import React, { useState } from "react";
import * as CgIcons from "react-icons/cg";
import { useNavigate } from "react-router-dom";
import "../Components/styles/EditProfile.css"

const EditProfile = () => {

    let navigate = useNavigate();

    const [profileDetail, setProfileDetail] = useState({
        "username": "TestUser",
        "email": "test@email.com",
        "description": "Test Description"
    });

    const onChange = event => {
        setProfileDetail({
            ...profileDetail,
            [event.target.name]: event.target.value
        })
    }

    const handleSubmit = () => {
        console.log(profileDetail);
        navigate("/myprofile");
    }

    const changePicture = () => {
        console.log("change profile picture");
    }

    console.log(profileDetail);
    return(
        <div className="editprofile">
            <form className="editprofile-container" onSubmit={handleSubmit}>

                <div className="editprofile-picture">
                    <div className="editprofile-profilepicture">
                        <CgIcons.CgProfile />
                    </div>
                    <button type="button" onClick={changePicture}>change</button>
                </div>

                <div className="editprofile-input">
                    <input type="text" onChange={onChange} value={profileDetail.username} name="username" placeholder="Username"></input>
                </div>
                <div className="editprofile-input">
                    <input type="email" onChange={onChange} value={profileDetail.email} name="email" placeholder="Email"></input>
                </div>
                <div className="editprofile-input">
                    <input type="text" onChange={onChange} value={profileDetail.description} name="description" placeholder="Description"></input>
                </div>
                <div className="editprofile-button">
                    <button type="submit">Edit</button>
                </div>
            </form>
        </div>
    )
}

export default EditProfile;