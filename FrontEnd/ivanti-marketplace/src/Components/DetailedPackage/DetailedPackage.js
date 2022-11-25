import React, { useEffect, useState } from "react";
import axios from "axios";
import "./DetailedPackage.css";
import { useParams } from "react-router-dom";
import backgroundImage2 from "../../img/tile-5.png";
import { ShimmerButton, ShimmerTitle, ShimmerThumbnail } from "react-shimmer-effects"
import RatingsOverview from "./RatingsOverview";
import {useTranslation} from "react-i18next";
import { useNavigate } from 'react-router-dom';

function DetailedPackage(props) {
    const { t } = useTranslation();
    let navigate = useNavigate();

    const id = useParams().id;
    const [Downloaded, setDownloaded] = useState(null);
    const [isActive, setIsActive] = useState(true);
    const [packageInfo, setPackageInfo] = useState({
        title: <ShimmerTitle line={1} variant="primary" />,
        category: <ShimmerTitle line={3} gap={10} variant="secondary" />,
        description: <p style={{ fontSize: '18px' }}>{t("Loading description...")}</p>,
        image: <div className="detailed-small-icon"><ShimmerThumbnail rounded width={180} height={180} /></div>
    });
    const [prompt, setPrompt] = useState(null);

    useEffect(() => {
        localStorage.setItem('lastpage', '/detailed/' + id);

        var config = {
            method: 'get',
            url: `http://localhost:8080/packages/${id}`,
            headers: { "Access-Control-Allow-Origin": "*" }
        };

        axios(config)
            .then(function (response) {
                setPackageInfo({
                    id: response.data.id,
                    title: <h1>{response.data.title}</h1>,
                    description: <h1>{response.data.description}</h1>,
                    category: <p>{t('Category')}: {response.data.category}</p>,
                    creator: <p>{t('Creator')}: {response.data.creatorFirstName} {response.data.creatorLastName}</p>,
                    averageStarRating: Math.round(response.data.averageStarRating * 10) / 10,
                    ratingsAmount: response.data.ratingsAmount,
                    image: <img style={{ borderRadius: '8px' }} className="detailed-small-icon" src={response.data.image} alt="icon"></img>
                });
                setIsActive(response.data.active);
            })
            .catch(function (error) {
                console.log(error);
            });

        let userID = 0;
        if(props.loggedUser) {
            userID = props.loggedUser.id;
        }

        var config2 = {
            method: 'get',
            url: `http://localhost:8080/users/${userID}/downloadedPackages/${id}`,
            headers: { "Access-Control-Allow-Origin": "*" }
        };

        axios(config2)
            .then(function (response) {
                if(response.data)
                {
                    setDownloaded(true);
                }
                else
                {
                    setDownloaded(false);
                }
            })
            .catch(function (error) {
                setDownloaded(false);
            });
    }, [id, prompt, props.loggedUser, t]);

    //#region downloadPrompts
    function showDownloadingPrompt() {
        console.log('Show downloading prompt');
        let newPrompt = (
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <div style={{ marginTop: '2%', height: '5%', width: '50%', background: 'green', borderRadius: '8px' }}>
                    <p style={{ textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%' }}>{t("Downloading package...")}</p>
                </div>
            </div>
        );
        setPrompt(newPrompt);
    }

    function showSuccesPrompt() {
        console.log('Show success');
        let newPrompt = (
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <div style={{ marginTop: '2%', height: '5%', width: '50%', background: 'green', borderRadius: '8px' }}>
                    <p style={{ textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%' }}>{t("Succesfully installed package.")}</p>
                </div>
            </div>
        );
        setPrompt(newPrompt);
    }

    function showFailedPrompt() {
        console.log('Show failure');
        let newPrompt = (
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <div style={{ marginTop: '2%', height: '5%', width: '50%', background: 'red', borderRadius: '8px' }}>
                    <p style={{ textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%' }}>{t("Something went wrong when installing package.")}</p>
                </div>
            </div>
        );
        setPrompt(newPrompt);
    }

    //#endregion

    //#region removePrompts
    function showRemovingPrompt() {
        console.log('Show removing prompt');
        let newPrompt = (
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <div style={{ marginTop: '2%', height: '5%', width: '50%', background: 'green', borderRadius: '8px' }}>
                    <p style={{ textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%' }}>{t("uninstalling package...")}</p>
                </div>
            </div>
        );
        setPrompt(newPrompt);
    }

    function showRemoveSuccesPrompt() {
        console.log('Show success');
        let newPrompt = (
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <div style={{ marginTop: '2%', height: '5%', width: '50%', background: 'green', borderRadius: '8px' }}>
                    <p style={{ textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%' }}>{t("Succesfully uninstalled package.")}</p>
                </div>
            </div>
        );
        setPrompt(newPrompt);
    }

    function showRemoveFailedPrompt() {
        console.log('Show failure');
        let newPrompt = (
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <div style={{ marginTop: '2%', height: '5%', width: '50%', background: 'red', borderRadius: '8px' }}>
                    <p style={{ textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%' }}>{t("Something went wrong when uninstalling package.")}</p>
                </div>
            </div>
        );
        setPrompt(newPrompt);
    }
    //#endregion
    function hidePrompt() {
        console.log('Hide prompt');
        setPrompt(null);
    }

    function handleInstall(packageID) {
        if(props.loggedUser === null) {
            navigate('/login');
        }
        console.log("installing...")
        const token = localStorage.getItem('accessToken');
        axios
            .post('http://localhost:8080/users/' + props.loggedUser.id + '/downloadedPackages', packageID, {
                headers: {
                    'Content-Type': 'text/plain',
                    'Authorization': 'Bearer ' + token
                }
            })
            .then(() => {
                showDownloadingPrompt();
                setTimeout(() => {
                    showSuccesPrompt();
                }, 2000);
                setTimeout(hidePrompt, 4000);
            })
            .catch((error) => {
                console.log("Got this error: " + error);
                showFailedPrompt();
                setTimeout(hidePrompt, 4000);
            });
    }

    function handleDelete(id) {
        console.log("deleting...")
        const token = localStorage.getItem('accessToken');
        let config = {
            method: 'delete',
            url: 'http://localhost:8080/users/' + props.loggedUser.id + '/downloadedPackages/' + id,
            headers: {
                'Authorization': 'Bearer ' + token
            }
        };

        axios(config)
            .then(() => {
                showRemovingPrompt();
                setTimeout(() => {
                    showRemoveSuccesPrompt();
                }, 2000);
                setTimeout(hidePrompt, 5000);
            })
            .catch((error) => {
                console.log("Got this error:", error);
                showRemoveFailedPrompt();
                setTimeout(hidePrompt, 4000);
            });
    }

    function IsDownloaded(packageID) {
        if (Downloaded === null) {
            return <ShimmerButton />
        }

        
        if (Downloaded === true) {
            return (
                <button className="detailed-button" onClick={() => handleDelete(packageID)}>{t('Uninstall')}</button>
            )
        }
        if(!isActive)
        {
            return (
                <p className="package-card-inactive-text">{t('Inactive')}</p>
            )
        }
        if (Downloaded === false) {
            return (
                <button className="detailed-button" onClick={() => handleInstall(packageID)}>{t("Download")}</button>
            )
        }
    }

    return (
        <div>
            <img className="background_image_1" src={backgroundImage2} alt={backgroundImage2}></img>
            {prompt}
            <div className="detailed-page">
                <div className="detailed-top-whitespace">
                    <p></p>
                </div>
                <div>
                    {packageInfo.image}
                </div>
                <div className="detailed-text-container">
                    <div className="detailed-title">
                        {packageInfo.title}
                    </div>
                    {packageInfo.category}
                    {packageInfo.creator}
                    {IsDownloaded(id)}
                </div>
                <div style={{ display: 'flex' }}>
                    <RatingsOverview packageId={id} loggedUser={props.loggedUser} downloaded={Downloaded} averageStarRating={packageInfo.averageStarRating} totalAmount={packageInfo.ratingsAmount} />
                    <div className="detailed-description">
                        {packageInfo.description}
                    </div>
                </div>
            </div>
        </div>
    )
}


export default DetailedPackage;
