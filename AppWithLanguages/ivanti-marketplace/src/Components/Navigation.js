import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import React, {useContext, useState} from "react";
import { Link, NavLink } from "react-router-dom";
import { NavbarData } from "./data/NavbarData";
import './Navigation.css';
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
import * as CgIcons from "react-icons/cg";
import { IconContext } from "react-icons";
import ProfileMenu from "./ProfileMenu";
import ProfileMenuItems from "./ProfileMenuItems";
import './ProfileMenu.css';
import LocaleContext from "../LocaleContext";
import {useTranslation} from "react-i18next";
import i18n from "../i18n";
import {LanguageData} from "./data/LanguageData";
import {ProfileMenuData} from "./data/ProfileMenuData";
import {ProfileMenuDataGerman} from "./data/ProfileMenuDataGerman";
import {ProfileMenuDataArabian} from "./data/ProfileMenuDataArabian";
import {NavbarDataGerman} from "./data/NavbarDataGerman";
import {NavbarDataArabian} from "./data/NavbarDataArabian";

const Navigation = () => {

    const [sidebar, setSidebar] = useState(false);

    const showSidebar = () => setSidebar(!sidebar);

    const {t} = useTranslation();
    const { locale } = useContext(LocaleContext);
    function changeLocale (l) {
        if (locale !== l) {
            i18n.changeLanguage(l);
        }
    }
    let code_block;
    if(t('language')==="Language")
    {
        code_block = NavbarData;

    }
    if(t('language')==="Sprache")
    {
        code_block = NavbarDataGerman;
    }
    if(t('language')===" اللغة")
    {
        code_block = NavbarDataArabian;
    }
    return(
        <div>
            <IconContext.Provider value={{color:'#fff'}}>
                <div className="navbar">
                    <Link to="#" className="menu-bars">
                        <FaIcons.FaBars onClick={showSidebar} />
                    </Link>
                    <div className="title">
                        <h1 className="ivanti-title">IVANTI MARKETPLACE</h1>
                    </div>
                    <Link to="#" className="profile">
                        <ProfileMenu icon={<CgIcons.CgProfile />}>
                            <ProfileMenuItems/>
                        </ProfileMenu>
                    </Link>
                    <div>
                        <button href="*" onClick={() => changeLocale('en')}>English</button>
                        <button href="*" onClick={() => changeLocale('ar')}>العربية</button>
                        <button href="*" onClick={() => changeLocale('de')}>Deutsch</button>
                    </div>
                </div>

                <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
                    <ul className='nav-menu-items'>
                        <li className="navbar-toggle" onClick={showSidebar}>
                            <Link to="#" className="menu-bars">
                                <AiIcons.AiOutlineClose />
                            </Link>
                        </li>
                        {code_block.map((item, index) => {
                            return (
                                <li key={index} className={item.className}>
                                    <NavLink to={item.path} onClick={showSidebar}>
                                        <span>{item.title}</span>
                                    </NavLink>
                                </li>
                            )
                        })}
                    </ul>
                </nav>
            </IconContext.Provider>
        </div>
    )
}

export default Navigation;