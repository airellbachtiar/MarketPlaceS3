import React, { useState } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import "./App.css";
import Navbar from "./Components/Navbar";
import Downloads from "./pages/Downloads";
import EditProfile from "./pages/EditProfile";
import Home from "./pages/Home";
import LoginForm from "./pages/LoginForm";
import MyProfile from "./pages/MyProfile";
import SignupForm from "./pages/SignupForm";
import Uploads from "./pages/Uploads";
import UploadPackage from "./pages/UploadPackage";
import Recommended from "./pages/Recommended";
import jwtDecode from "jwt-decode";
import axios from "axios";
import Logout from "./Components/Logout";
import UpdatePackage from "./pages/UpdatePackage";
import DetailedPackage from "./Components/DetailedPackage/DetailedPackage";
import i18n from './i18n';
import Loading from "./pages/Loading";
import LocaleContext from "./LocaleContext";

const App = () => {
  const [loggedUser, setLoggedUser] = useState(() => {
    const saved = localStorage.getItem("loggedUser");
    const initialValue = saved ? JSON.parse(saved) : null;
    return initialValue;});
  const [expirationDate, setExpirationDate] = useState(() => {
    const token = localStorage.getItem("accessToken");
    const initialValue = token ? JSON.parse(jwtDecode(token).exp) : 0;
    return initialValue;
});

  const [locale, setLocale] = useState(i18n.language);
  i18n.on('languageChanged', (lng) => setLocale(i18n.language));

  const updateUser = () => {
    const token = localStorage.getItem("accessToken");
    var decode = jwtDecode(token);
    const userID = decode.userId;

    var config = {
      method: "get",
      url: `http://localhost:8080/users/${userID}`,
      headers: {},
    };

    axios(config)
      .then(function (response) {
        setExpirationDate(decode.exp);
        setLoggedUser(response.data);
        localStorage.setItem("loggedUser", JSON.stringify(response.data));
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  const removeUser = () => {
    setLoggedUser(null);
    setExpirationDate(null);
    localStorage.removeItem("loggedUser");
    localStorage.removeItem("accessToken");
    navigate("/login");
  };

  let navigate = useNavigate();

  const checkTokenExpiration = () => {
    const currentTime = new Date().getTime() / 1000;
      if (currentTime > expirationDate) {
        removeUser();
        return true;
      }
  };

  return (
    <div>
      <>
        <LocaleContext.Provider value={{ locale, setLocale }}>
          <React.Suspense fallback={<Loading />}>
            <Navbar
              loggedUser={loggedUser}
            />

            <Routes>
              <Route path="/"
                element={<Home loggedUser={loggedUser} checkTokenExpiration={checkTokenExpiration}/>} 
                />
              <Route
                path="/downloads"
                element={<Downloads loggedUser={loggedUser} checkTokenExpiration={checkTokenExpiration} />}
              />
              <Route
                path="/uploads"
                element={<Uploads loggedUser={loggedUser} checkTokenExpiration={checkTokenExpiration} />}
              />
              <Route
                path="/updatePackage/:id"
                element={<UpdatePackage loggedUser={loggedUser} />}
              />
              <Route
                path="/newUpload"
                element={<UploadPackage loggedUser={loggedUser} />}
              />
              <Route
                path="/myprofile"
                element={<MyProfile loggedUser={loggedUser} checkTokenExpiration={checkTokenExpiration} />}
              />
              <Route
                path="/editprofile"
                element={
                  <EditProfile loggedUser={loggedUser} updateUser={updateUser} />
                }
              />
              <Route
                path="/login"
                element={<LoginForm updateUser={updateUser} />}
              />
              <Route path="/recommended" element={<Recommended loggedUser={loggedUser} checkTokenExpiration={checkTokenExpiration}/>}/>
              <Route path="/signup" element={<SignupForm />} />
              <Route path="/logout" element={<Logout removeUser={removeUser} />} />
              <Route exact path="/detailed/:id" element={<DetailedPackage loggedUser={loggedUser} />} />
            </Routes>
          </React.Suspense>
        </LocaleContext.Provider>
      </>
    </div>
  );
};

export default App;
