import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import backgroundImage4 from "../img/tile-4.png";
import "../Components/styles/LoginForm.css"
import axios from "axios";
import {useTranslation} from "react-i18next";

const LoginForm = (updateUser) => {
    const { t } = useTranslation();
    let navigate = useNavigate();

    const [formValue, setFormValue] = useState({
        email: "",
        password: ""
    });

    const [loginError, setLoginError] = useState(false);

    const onChange = event => {
        setFormValue({
            ...formValue,
            [event.target.name]: event.target.value
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        var data = JSON.stringify({
            "email": formValue.email,
            "password": formValue.password
        });

        var config = {
            method: 'post',
            url: 'http://localhost:8080/login',
            headers: {
                'Content-Type': 'application/json'
            },
            data: data
        };

        axios(config)
            .then(function (response) {
                localStorage.setItem("accessToken", response.data.accessToken);
                updateUser.updateUser();
                navigate("/");
            })
            .catch(function (error) {
                setLoginError(true);
                console.log(error);
            });
    }

    localStorage.setItem('lastpage', '/login');

    return (
        <>
            <img className="background_image_1" src={backgroundImage4} alt={backgroundImage4}></img>

            <div className="loginform">
                <div className="loginform-container">
                    <div className="loginform-header">
                        <h1>{t("Login")}</h1>
                    </div>
                    <div className="loginform-body">
                        <form onSubmit={handleSubmit} className="loginform-container">
                            <div className="loginform-errormessage">
                                <span data-cy="login-email-error">{!formValue.email && loginError ? t('EmailIsEmpty') : ""}</span>
                            </div>
                            <div className="loginform-input">
                                <label>{t("Email")}</label>
                                <input data-cy="login-email" type="email" placeholder={t('Email')} onChange={onChange} value={formValue.email} name="email"></input>
                            </div>

                            <div className="loginform-errormessage">
                                <span data-cy="login-password-error">{!formValue.password && loginError ? t('PasswordIsEmpty') : ""}</span>
                            </div>
                            <div className="loginform-input">
                                <label>{t("Password")}</label>
                                <input data-cy="login-password" type="password" placeholder={t('Password')} onChange={onChange} value={formValue.password} name="password"></input>
                            </div>

                            <div className="loginform-errormessage">
                                <span data-cy="login-error" >{loginError ? t('FailToLogin') : ""}</span>
                            </div>
                            <div>
                                <button data-cy="login-submit" type="submit">{t("Login")}</button>
                            </div>

                        </form>
                    </div>
                    <div className="loginform-footer">
                        <div>
                            <p>{t('AccountMessage')} <NavLink to="/signup">{t("here")}</NavLink>!</p>
                        </div>
                    </div>
                </div>
            </div>

        </>
    )
}

export default LoginForm;