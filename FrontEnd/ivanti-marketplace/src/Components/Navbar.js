import React, {useState} from "react";
import { Link, NavLink } from "react-router-dom";
import { NavbarData } from "./data/NavbarData";
import '../Components/styles/Navbar.css';
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
import * as CgIcons from "react-icons/cg";
import { IconContext } from "react-icons";
import ProfileMenu from "./ProfileMenu";
import ProfileMenuItems from "./ProfileMenuItems";
import '../Components/styles/ProfileMenu.css';
import {useTranslation} from "react-i18next";
import { NavbarDataAfterLogin } from "./data/NavbarDataAfterLogin";

const Navbar = (loggedUser) => {

    const { t } = useTranslation();

    const [sidebar, setSidebar] = useState(false);
    const [profileMenuState, setProfileMenuState] = useState(false);

    const showSidebar = () => 
    {
        setSidebar(!sidebar);
        if(profileMenuState)
        {
            setProfileMenuState(!profileMenuState);
        }
    };

    const showProfileMenu = () =>
    {
        setProfileMenuState(!profileMenuState);
        if(sidebar)
        {
            setSidebar(!sidebar);
        }
    };

    return(
        <div>
            <IconContext.Provider value={{color:"var(--gray-dark)"}}>
                <div className="navbar">
                    <Link to="#" className="menu-bars">
                        <FaIcons.FaBars onClick={showSidebar} />
                    </Link>
                    <div className="title">
                        <h1 className="ivanti-title">{t('title')}</h1>
                    </div>
                    <div data-cy="profile-menu" to="#" className="profile">
                        <ProfileMenu icon={<CgIcons.CgProfile />} profileMenuState={profileMenuState} showProfileMenu={showProfileMenu}>
                            <ProfileMenuItems showProfileMenu={showProfileMenu} loggedUser={loggedUser.loggedUser}/>
                        </ProfileMenu>
                    </div>
                </div>

                <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
                    <ul className='nav-menu-items'>
                        <li className="navbar-toggle" onClick={showSidebar}>
                            <Link to="#" className="menu-bars">
                                <AiIcons.AiOutlineClose />
                            </Link>
                        </li>
                        {NavbarData.map((item, index) => {
                            return (
                                <li key={index} className={item.className}>
                                    <NavLink to={item.path} onClick={showSidebar}>
                                        <span>{t(item.title)}</span>
                                    </NavLink>
                                </li>
                            )
                        })}
                        {loggedUser.loggedUser ?
                            NavbarDataAfterLogin.map((item, index) => {
                                return (
                                    <li key={index} className={item.className}>
                                        <NavLink to={item.path} onClick={showSidebar}>
                                            <span>{t(item.title)}</span>
                                        </NavLink>
                                    </li>
                                )
                            })
                            :
                            null
                        }
                    </ul>
                </nav>
            </IconContext.Provider>
        </div>
    )
}

export default Navbar;