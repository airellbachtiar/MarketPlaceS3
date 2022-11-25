import React from "react";

const ProfileMenu = (props) => {

    return (
        <div className="dropdown-container">
            <div className="profile-button" onClick={props.showProfileMenu}>
                {props.icon}
            </div>
            {props.profileMenuState && props.children}
        </div>
    );
}

export default ProfileMenu;