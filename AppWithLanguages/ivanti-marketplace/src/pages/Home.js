import React, {useContext} from "react";
import PackageCardScrollBar from "../Components/PackageCardScroll";
import PackageViewOptionBar from "../Components/PackageViewOptionBar";
import {useTranslation} from "react-i18next";
import LocaleContext from "../LocaleContext";
import i18n from "../i18n";
import {Link} from "react-router-dom";
import {useNavigate} from "react-router-dom";

const Home = () => {
    let navigate = useNavigate();
    const {t} = useTranslation();
    const { locale } = useContext(LocaleContext);
    function changeLocale (l) {
        if (locale !== l) {
            i18n.changeLanguage(l);
        }
    }
    return(
        <div>
            <PackageViewOptionBar />
           
        </div>
    )
}

export default Home;
