import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "universal-cookie";

const Logout = (removeUser) => {

    let navigate = useNavigate();

    useEffect(() => {
        const cookies = new Cookies();
        cookies.remove("accessToken", { path: '/' });
        removeUser.removeUser();
        navigate("/");
    }, [navigate, removeUser]);

    return (
        <>
        </>
    )
}

export default Logout;