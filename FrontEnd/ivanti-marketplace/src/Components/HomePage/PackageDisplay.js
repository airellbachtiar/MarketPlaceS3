import React, { useState, useEffect } from "react";
import PackageCard from "../PackageCard";
import "./PackageDisplay.css";
import LoadingSpinner from "../other/LoadingSpinner";
import {useTranslation} from "react-i18next";

function PackageDisplay(props) {
    const { t } = useTranslation();
    const [seeMore, setSeeMore] = useState(false);

    useEffect(() => {
        console.log("reloading packages...")
        LoadMoreOrLessPackages();
    }, [props.packagesList]);


    function LoadGrid(limit) {
        var newlimit = limit;
        const packageCards = [];
        if (props.packagesList.length < limit) {
            newlimit = props.packagesList.length;
        }
        for (let index = 0; index < newlimit; index++) {
            packageCards.push(<PackageCard
                id={props.packagesList[index].id}
                installed={props.packagesList[index].installed}
                title={props.packagesList[index].title}
                active={props.packagesList[index].active}
                category={props.packagesList[index].category}
                onClick={props.onClickPackage}
                ratingsAmount={props.packagesList[index].ratingsAmount}
                stars={props.packagesList[index].averageStarRating}
                image={props.packagesList[index].image} />
            )

        }
        if (limit === 10) {
            return (
                <>
                    <div className="Home-Display-Less">
                        {
                            packageCards.map((card, index) => {
                                if (index < 10) {
                                    return (
                                        <div className={`div-Less-${index + 1}`}> {card} </div>
                                    )
                                }
                                else
                                {
                                    return <></>;
                                }
                            })
                        }
                    </div>
                    <p className="SeeMoreLess" onClick={() => setSeeMore(true)}>{t("See more")}</p>
                </>
            )
        }
        if (limit === 30) {
            return (
                <>
                    <div className="Home-Display-More">
                        {
                            packageCards.map((card, index) => {
                                if (index < 30) {
                                    return (
                                        <div className={`div-More-${index + 1}`}> {card} </div>
                                    )
                                }
                                else
                                {
                                    return <></>;
                                }
                            })
                        }
                    </div>
                    <p className="SeeMoreLess" onClick={() => setSeeMore(false)}>{t("See less")}</p>
                </>
            )
        }

    }

    function LoadMoreOrLessPackages() {
        if (props.packagesList.length === 0) {
            return (
                <div className="package-display-no-packages">
                    {t("No packages Found!")}
                </div>
            )
        }
        if (seeMore === false) {
            return (
                <>
                    {LoadGrid(10)}
                </>
            )
        }
        return (
            <>
                {LoadGrid(30)}
            </>
        )

    }
    return (
        <div className="Home-Block">
            {
                props.loading ?
                    <div className="package-display-loading">
                        <LoadingSpinner />
                    </div>
                    :
                    LoadMoreOrLessPackages()
            }
        </div>
    )
}

export default PackageDisplay;