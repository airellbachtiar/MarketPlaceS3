import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import backgroundImage4 from "../img/tile-4.png";
import "../Components/styles/SignupForm.css"
import axios from "axios";
import {useTranslation} from "react-i18next";

const SignupForm = () => {
    const { t } = useTranslation();
    let navigate = useNavigate();

    const [formValue, setFormValue] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        repeatPassword: ""
    });
    const [signupError, setSignupError] = useState(false);

    const onChange = event => {
        setFormValue({
            ...formValue,
            [event.target.name]: event.target.value
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if (formValue.password === formValue.repeatPassword) {
            var data = JSON.stringify({
                "firstName": formValue.firstName,
                "lastName": formValue.lastName,
                "email": formValue.email,
                "password": formValue.password
            });

            var config = {
                method: 'post',
                url: 'http://localhost:8080/users',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: data
            };
            axios(config)
                .then(function (response) {
                    console.log(response.data);
                    if (response.data) {
                        navigate("/login");
                    } else {
                        setSignupError(true);
                        throw new Error("Error");
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }

    return (
        <>
            <img className="background_image_1" src={backgroundImage4} alt={backgroundImage4}></img>

            <div className="signupform">
                <div className="signupform-container">
                    <div className="signupform-header">
                        <h1>{t("Signup")}</h1>
                    </div>
                    <div className="signupform-body">
                        <form onSubmit={handleSubmit} className="signupform-container">

                            <div className="signupform-errormessage">
                                <span>{!formValue.firstName && signupError ? t('FirstNameIsEmpty') : ""}</span>
                            </div>

                            <div className="signupform-input">
                                <span>{t('FirstName')}</span>
                                <input type="text" onChange={onChange} value={formValue.username} name="firstName"></input>
                            </div>

                            <div className="signupform-errormessage">
                                <span>{!formValue.lastName && signupError ? t('LastNameIsEmpty') : ""}</span>
                            </div>

                            <div className="signupform-input">
                                <span>{t('LastName')}</span>
                                <input type="text" onChange={onChange} value={formValue.username} name="lastName"></input>
                            </div>

                            <div className="signupform-errormessage">
                                <span>{!formValue.email && signupError ? t('EmailIsEmpty') : ""}</span>
                            </div>

                            <div className="signupform-input">
                                <span>{t('Email')}</span>
                                <input type="email" onChange={onChange} value={formValue.email} name="email"></input>
                            </div>

                            <div className="signupform-errormessage">
                                <span>{formValue.password !== formValue.repeatPassword && signupError ? t('PasswordsMatch') : ""}</span>
                                <span>{!formValue.password && signupError ? t('PasswordIsEmpty') : ""}</span>
                            </div>

                            <div className="signupform-input">
                                <span>{t('Password')}</span>
                                <input type="password" onChange={onChange} value={formValue.password} name="password"></input>
                            </div>
                            <div className="signupform-input">
                                <span>{t('RepeatPassword')}</span>
                                <input type="password" onChange={onChange} value={formValue.repeatPassword} name="repeatPassword"></input>
                            </div>

                            <div className="signupform-errormessage">
                                <span>
                                    {signupError ? t('Error') : ""}
                                </span>
                            </div>

                            <div>
                                <button type="submit">{t('Register')}</button>
                            </div>

                        </form>
                    </div>
                    <div className="signupform-footer">
                        <div>
                            <p>{t('Already')}<NavLink to="/login">{t('here')}</NavLink>!</p>
                        </div>
                    </div>
                </div>
            </div>
        </>

    )
}

export default SignupForm;