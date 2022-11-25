import React, { useState } from "react";

const ProfileMenu = (props) => {

    const [open, setOpen] = useState(false);

    return (
        <div className="dropdown-container">
            <div className="profile-button" onClick={() => setOpen(!open)}>
                {props.icon}
            </div>
            {open && props.children}
        </div>
    );
}

export default ProfileMenu;