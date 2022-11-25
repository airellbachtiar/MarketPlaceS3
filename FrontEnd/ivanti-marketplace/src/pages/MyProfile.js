import React, { useEffect, useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import "../Components/styles/MyProfile.css";
import backgroundImage6 from "../img/tile-6.png";
import { useTranslation } from "react-i18next";

const MyProfile = (loggedUser) => {
    const { t } = useTranslation();

    const [lastPage, setLastPage] = useState('/');
    let navigate = useNavigate();
    useEffect(() => {
        setLastPage(localStorage.getItem('lastpage'));
        loggedUser.checkTokenExpiration();
        if (!loggedUser) {
            navigate("/login");
        }
    }, [loggedUser, navigate]);
    return (
        <>
            <img className="background_image_1" src={backgroundImage6} alt={backgroundImage6}></img>
            <div className="myprofile">
                <div className="myprofile-title">
                    <h1>{t('myProfile')}</h1>
                </div>
                <div className="myprofile-container">
                    <div className="myprofile-detail">
                        <label>{t("First Name")}</label>
                        <input
                            disabled
                            value={loggedUser.loggedUser.firstName}
                        />
                    </div>
                    <div className="myprofile-detail">
                        <label>{t("Last Name")}</label>
                        <input
                            disabled
                            value={loggedUser.loggedUser.lastName}
                        />
                    </div>
                    <div className="myprofile-detail">
                        <label>{t("Email")}</label>
                        <input
                            disabled
                            value={loggedUser.loggedUser.email}
                        />
                    </div>

                    <div className="editprofile-footer">
                        <NavLink to={lastPage} className="editprofile-button">
                            <button type="button">{t('Back')}</button>
                        </NavLink>
                        <div className="editprofile-button">
                            <button onClick={() => navigate("/editprofile")}>{t('Edit')}</button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default MyProfile;
