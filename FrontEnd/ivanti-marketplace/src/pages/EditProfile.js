import axios from "axios";
import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import "../Components/styles/EditProfile.css";
import backgroundImage6 from "../img/tile-6.png";
import { useTranslation } from "react-i18next";

const EditProfile = (props) => {
    const { t } = useTranslation();
    let navigate = useNavigate();

    const [profileDetail, setProfileDetail] = useState({
        "email": `${props.loggedUser.email}`,
        "firstName": `${props.loggedUser.firstName}`,
        "lastName": `${props.loggedUser.lastName}`
    });

    const onChange = event => {
        setProfileDetail({
            ...profileDetail,
            [event.target.name]: event.target.value
        })
    }

    console.log(props.loggedUser);

    const handleSubmit = (e) => {
        e.preventDefault();

        const token = localStorage.getItem("accessToken");

        var data = JSON.stringify({
            "userID": `${props.loggedUser.id}`,
            "email": `${profileDetail.email}`,
            "firstName": `${profileDetail.firstName}`,
            "lastName": `${profileDetail.lastName}`
        });

        var config = {
            method: 'put',
            url: `http://localhost:8080/users/${props.loggedUser.id}`,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            data: data
        };

        axios(config)
            .then(function () {
                props.updateUser();
                navigate("/myprofile");
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    return (
        <>
            <img className="background_image_1" src={backgroundImage6} alt={backgroundImage6}></img>
            <div className="editprofile">
                <div className="editprofile-title">
                    <h1>{t('EditProfile')}</h1>
                </div>
                <form className="editprofile-container" onSubmit={handleSubmit}>

                    <div className="editprofile-input">
                        <label>First Name</label>
                        <input type="text" onChange={onChange} value={profileDetail.firstName} name="firstName" placeholder={t('FirstName')}></input>
                    </div>
                    <div className="editprofile-input">
                        <label>Last Name</label>
                        <input type="text" onChange={onChange} value={profileDetail.lastName} name="lastName" placeholder={t('LastName')}></input>
                    </div>
                    <div className="editprofile-input">
                        <label>Email</label>
                        <input type="email" onChange={onChange} value={profileDetail.email} name="email" placeholder={t('Edit')}></input>
                    </div>

                    <div className="editprofile-footer">
                        <NavLink to="/myprofile" className="editprofile-button">
                            <button type="button">{t('cancel')}</button >
                        </NavLink>
                        <div className="editprofile-button">
                            <button type="submit">{t('Save')}</button>
                        </div>
                    </div>
                </form>
            </div>
        </>
    )
}

export default EditProfile;
