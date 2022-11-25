import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import "../Components/styles/SignupForm.css"

const SignupForm = () => {

    let navigate = useNavigate();

    const [formValue, setFormValue] = useState({
        username: "",
        email: "",
        password: "",
        repeatPassword: ""
    });

    const onChange = event => {
        setFormValue({
            ...formValue,
            [event.target.name]: event.target.value
        })
    }

    const handleSubmit = () => {
        console.log(formValue);
        navigate("/login");
    }

    console.log(formValue);

    return (
        <div className="signupform">
            <form onSubmit={handleSubmit} className="signupform-container">
                <div className="signupform-input">
                    <span>Username</span>
                    <input type="text" onChange={onChange} value={formValue.username} name="username"></input>
                </div>
                <div className="signupform-input">
                    <span>Email</span>
                    <input type="email" onChange={onChange} value={formValue.email} name="email"></input>
                </div>
                <div className="signupform-input">
                    <span>Password</span>
                    <input type="password" onChange={onChange} value={formValue.password} name="password"></input>
                </div>
                <div className="signupform-input">
                    <span>Repeat Password</span>
                    <input type="password" onChange={onChange} value={formValue.repeatPassword} name="repeatPassword"></input></div>
                <div>
                    <button type="submit">Register</button>
                </div>
                <div>
                    <p>Already have an account? Login <NavLink to="/login">here</NavLink>!</p>
                </div>
            </form>
        </div>
    )
}

export default SignupForm;