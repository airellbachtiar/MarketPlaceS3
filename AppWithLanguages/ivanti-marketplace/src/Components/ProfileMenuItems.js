import React, {useContext, useState} from "react";
import { NavLink } from "react-router-dom";
import { LanguageData } from "./data/LanguageData";
import { ProfileMenuData } from "./data/ProfileMenuData";
import {useTranslation} from "react-i18next";
import LocaleContext from "../LocaleContext";
import i18n from "../i18n";
import {ProfileMenuDataGerman} from "./data/ProfileMenuDataGerman";
import {ProfileMenuDataArabian} from "./data/ProfileMenuDataArabian";

const ProfileMenuItems = () => {
    const [activeMenu, setActiveMenu] = useState('main');
    const {t} = useTranslation();
    const DropdownItem = (props) => {
            return (
                <div className="dropdown-items">

                    {props.data.map((item, index) => {
                        return (
                            <NavLink key={index} to={item.path} className={item.className}
                                     onClick={() => item.goToMenu && setActiveMenu(item.goToMenu)}>
                                <span>{item.title}</span>
                            </NavLink>
                        )
                    })}

                </div>
            )

    }

    console.log(activeMenu);
if(t('language')==="Language")
{
    return (
        <>
            {activeMenu === "language" &&
                <DropdownItem data={LanguageData}/>
            }
            {activeMenu === "main" &&
                <DropdownItem data={ProfileMenuData}/>}
        </>
    );
}
    if(t('language')==="Sprache")
    {
        return (
            <>
                {activeMenu === "language" &&
                    <DropdownItem data={LanguageData}/>
                }
                {activeMenu === "main" &&
                    <DropdownItem data={ProfileMenuDataGerman}/>}
            </>
        );
    }
    if(t('language')===" اللغة")
    {
        return (
            <>
                {activeMenu === "language" &&
                    <DropdownItem data={LanguageData}/>
                }
                {activeMenu === "main" &&
                    <DropdownItem data={ProfileMenuDataArabian}/>}
            </>
        );
    }
}

export default ProfileMenuItems;