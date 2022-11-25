import React, { useContext, useState } from "react";
import { NavLink } from "react-router-dom";
import { useTranslation } from "react-i18next";
import i18n from "../i18n";
import LocaleContext from "../LocaleContext";

const ProfileMenuItems = (props) => {
    const [activeMenu, setActiveMenu] = useState('main');
    const { t } = useTranslation();
    const { locale } = useContext(LocaleContext);
    function changeLocale(l) {
        if (locale !== l) {
            i18n.changeLanguage(l);
        }
        props.showProfileMenu();
    }

    return (
        <div className="dropdown-items">
            {
                activeMenu === 'main' ?
                    <>
                        <NavLink to="#" className="dropdown-text" onClick={() => setActiveMenu("language")}>
                            <span>{t("Language")}</span>
                        </NavLink>
                        {
                            props.loggedUser ?
                                <>
                                    <NavLink to="/myprofile" className="dropdown-text" onClick={() => props.showProfileMenu()}>
                                        <span>{props.loggedUser.firstName}</span>
                                    </NavLink>

                                    <NavLink to="/logout" className="dropdown-text" onClick={() => props.showProfileMenu()}>
                                        <span data-cy="profile-menu-logout">{t('Logout')}</span>
                                    </NavLink>
                                </>
                                :
                                <NavLink to="/login" className="dropdown-text" onClick={() => props.showProfileMenu()}>
                                    <span data-cy="profile-menu-login">{t('Login')}</span>
                                </NavLink>
                        }
                    </>
                    :
                    <>
                        <NavLink to="#" className="dropdown-text" onClick={() => setActiveMenu("main")}>
                            <span>{t('Back')}</span>
                        </NavLink>
                        <NavLink to="#" className="dropdown-text" onClick={() => changeLocale('en')}>
                            <span>English</span>
                        </NavLink>
                        <NavLink to="#" className="dropdown-text" onClick={() => changeLocale('ne')}>
                            <span>Dutch</span>
                        </NavLink>
                    </>
            }

        </div>
    )
}

export default ProfileMenuItems;