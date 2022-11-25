import React from 'react';
import './Card.css';
import panda from './PandaProfile2.jpg';

function PackageCard(props) {
    let buttonText = "Free";
    if(props.buttonTitle !== undefined) {
        buttonText = props.buttonTitle;
    }

    return (
            <div className="card">
                <img src={panda} alt="Avatar"></img>
                <div className="container">
                    <h1><b>{props.title}</b></h1>
                    <p>{props.category}</p>
                    <div className='install-button'>
                    <button onClick={() => {props.onClick(props.id)}}>{buttonText}</button>
                    </div>
                </div>
            </div>

    )
}

export default PackageCard