import React from "react";
import { BeatLoader } from "react-spinners";

const LoadingSpinner = () => {

    return (
        <div style={{width: 'max-content'}}>
            <BeatLoader color="#670678" size={100} />
        </div>
    );
}

export default LoadingSpinner;