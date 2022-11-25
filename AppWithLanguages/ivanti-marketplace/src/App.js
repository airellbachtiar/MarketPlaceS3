import React, {useState} from "react";
import {BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import Navigation from "./Components/Navigation";
import Downloads from "./pages/Downloads";
import EditProfile from "./pages/EditProfile";
import Home from './pages/Home';
import LoginForm from "./pages/LoginForm";
import MyProfile from "./pages/MyProfile";
import SignupForm from "./pages/SignupForm";
import Uploads from './pages/Uploads';
import UploadPackage from "./pages/UploadPackage";
import i18n from "./i18n";
import LocaleContext from "./LocaleContext";
import Loading from "./Loading";

const App = () => {
    const [locale, setLocale] = useState(i18n.language);
    i18n.on('languageChanged', (lng) => setLocale(i18n.language));
 /* return (
   <div>
     <>
         <Router>
             <LocaleContext.Provider value={{locale, setLocale}}>
                 <React.Suspense fallback={<Loading />}>
      <Navigation />
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/downloads" element={<Downloads />}/>
        <Route path="/uploads" element={<Uploads />}/>
        <Route path="/newUpload" element={<UploadPackage />} />
        <Route path="/myprofile" element={<MyProfile />}/>
        <Route path="/editprofile" element={<EditProfile />}/>
        <Route path="/login" element={<LoginForm />}/>
        <Route path="/signup" element={<SignupForm   />}/>
      </Routes>
                 </React.Suspense>
             </LocaleContext.Provider>
         </Router>

     </>
   </div>
  );*/
    return (
        <div>
            <>
                    <LocaleContext.Provider value={{locale, setLocale}}>
                        <React.Suspense fallback={<Loading />}>
                            <Navigation />
                            <Routes>
                                <Route path="/" element={<Home />}/>
                                <Route path="/downloads" element={<Downloads />}/>
                                <Route path="/uploads" element={<Uploads />}/>
                                <Route path="/newUpload" element={<UploadPackage />} />
                                <Route path="/myprofile" element={<MyProfile />}/>
                                <Route path="/editprofile" element={<EditProfile />}/>
                                <Route path="/login" element={<LoginForm />}/>
                                <Route path="/signup" element={<SignupForm   />}/>
                            </Routes>
                        </React.Suspense>
                    </LocaleContext.Provider>
            </>
        </div>
    );
};

export default App;
