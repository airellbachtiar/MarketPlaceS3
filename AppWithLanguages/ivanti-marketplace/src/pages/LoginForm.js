import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import "../Components/styles/LoginForm.css"

const LoginForm = () => {

    let navigate = useNavigate();

    const [formValue, setFormValue] = useState({
        username: "",
        password: ""
    });

    const onChange = event => {
        setFormValue({
            ...formValue,
            [event.target.name]: event.target.value
        })
    }

    const handleSubmit = () => {
        console.log(formValue);
        navigate("/");
    }

    return (
        <div className="loginform">
            <form onSubmit={handleSubmit} className="loginform-container">
                <div className="loginform-input">
                    <input type="text" placeholder="Username" onChange={onChange} value={formValue.username} name="username"></input>
                </div>
                <div className="loginform-input">
                    <input type="password" placeholder="Password" onChange={onChange} value={formValue.password} name="password"></input>
                </div>
                <div>
                    <button type="submit">Login</button>
                </div>
                <div>
                    <p>Don't have an account yet? Create one <NavLink to="/signup">here</NavLink>!</p>
                </div>
            </form>
        </div>
    )
}

export default LoginForm;