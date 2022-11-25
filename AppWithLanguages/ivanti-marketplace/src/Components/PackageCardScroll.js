import React, { useEffect, useRef, useState } from "react";
import PackageCard from "./PackageCard";
import "./PackageCardScroll.css"

const PackageCardScroll = (props) => {
    
    const [card] = useState([
        {
            title: "1",
            category: "test"
        },
        {
            title: "1",
            category: "test"
        },
        {
            title: "1",
            category: "test"
        },
        {
            title: "1",
            category: "test"
        },
    ])

    let scrollAmount = 320;

    const package_ = useRef(null);
    const packageContainer = useRef(null);
    const leftButton = useRef(null);
    const rightButton = useRef(null);

    const [maxScroll, setMaxScroll] = useState(0);
    const [minScroll, setMinScroll] = useState(0);
    const [currentPosition, setCurrentPosition] = useState(minScroll);

    useEffect(() => {
        setMaxScroll(-packageContainer.current.offsetWidth -rightButton.current.offsetWidth + package_.current.offsetWidth);
        setMinScroll(leftButton.current.offsetWidth);
        setCurrentPosition(minScroll);
      }, [maxScroll, minScroll]);


    function scrollLeft() {
        let newPosition = currentPosition + scrollAmount;
        setCurrentPosition(newPosition);
        if(newPosition >= minScroll)
        {
            setCurrentPosition(minScroll);
        }
    }

    function scrollRight() {
        let newPosition = currentPosition - scrollAmount;
        console.log(currentPosition);
        console.log(newPosition);
        setCurrentPosition(newPosition);
        if(newPosition <= maxScroll)
        {
            setCurrentPosition(maxScroll);
        }
    }

    return(
        <>
            <h1>{props.title}</h1>
            <div className="package" ref={package_} style={props.style}>
                <button className="button-scroll" id="left-button" ref={leftButton} onClick={scrollLeft}>Left</button>
                <button className="button-scroll" id="right-button" ref={rightButton} onClick={scrollRight}>Right</button>
                <div className="package-container" style={{left: currentPosition +"px"}} ref={packageContainer}>
                    {card.map((item, index) => {
                        return (
                            <div key={index} className="packagecard-container">
                                <PackageCard title={item.title} category={item.category} />
                            </div>
                        )
                    })}
                </div>
            </div>
        </>
    )
}

export default PackageCardScroll;