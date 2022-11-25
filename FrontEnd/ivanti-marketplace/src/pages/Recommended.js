import React, { useEffect, useState } from "react";
import PackageDisplay from "../Components/HomePage/PackageDisplay";
import axios from "axios";
import backgroundImage1 from "../img/tile-1.png";
import "../Components/styles/HomeBackground.css";
import "../Components/styles/Home.css";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import "../Components/other/PopupStyle.css";

const Recommended = (props) => {
    const { t } = useTranslation();

    const [recommendedPackagesList, setRecommendedPackagesList] = useState([]);
    const [loading, setLoading] = useState(true);

    const [downloadedPackages, setDownloadedPackages] = useState();

    const [prompt, setPrompt] = useState(null);
    const [filteredPackages, setFilteredPackages] = useState([]);

    let navigate = useNavigate();


    useEffect(() => {
        localStorage.setItem('lastpage', '/');
        getPackages();
        if (props.loggedUser !== null) {
            getRecommendedPackages();
        }
        if (props.loggedUser) {
            setDownloadedPackages([]);
            loadDownloadedInfo();
        }
    }, [props.loggedUser]);

    const getPackages = async () => {
        let config = {
            method: 'get',
            url: 'http://localhost:8080/packages',
            headers: {}
        };
        axios(config)
            .then(function (response) {
                setFilteredPackages(response.data)
                if (props.loggedUser) {
                    getDownloadedPackages();
                }
                setLoading(false);
            })
            .catch(function (error) {
                console.log(error);
            });

    };

    function getDownloadedPackages() {
        let config = {
            method: 'get',
            url: 'http://localhost:8080/users/' + props.loggedUser.id + "/downloadedPackages",
            headers: {}
        };

        axios(config)
            .then((response) => {
                setDownloadedPackages(response.data);
            })
            .catch((error) => {
                console.log("Got this error:", error);
            });
    };

    function getRecommendedPackages() {
        let config = {
            method: 'get',
            url: 'http://localhost:8080/packages/' + props.loggedUser.id + "/recommended",
            headers: {}
        };

        axios(config)
            .then((response) => {
                setRecommendedPackagesList(response.data);
            })
            .catch((error) => {
                console.log("Got this error:", error);
            });
    }

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

    function hidePrompt() {
        console.log('Hide prompt');
        setPrompt(null);
    }

    function loadDownloadedInfo() {
        if (filteredPackages === undefined || downloadedPackages === undefined) {
        }
        else {
            recommendedPackagesList.forEach(p => {
                p.installed = false;
                downloadedPackages.forEach(dp => {
                    if (p.id === dp.id) {
                        p.installed = true;
                    }
                });
            })
        }

    }

    let handleInstall = id => {
        if (props.loggedUser && !props.checkTokenExpiration()) {
            axios
                .post('http://localhost:8080/users/' + props.loggedUser.id + '/downloadedPackages', id, {
                    headers: {
                        'Content-Type': 'text/plain',
                        'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
                    }
                })
                .then(() => {
                    showDownloadingPrompt();
                    setTimeout(() => {
                        getDownloadedPackages();
                        loadDownloadedInfo();
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
        else {
            navigate('/login');
        }
    }

    loadDownloadedInfo();

    console.log("Filtered packages:", filteredPackages);

    return (
        <div>
            <img className="background_image_1" src={backgroundImage1} alt={backgroundImage1}></img>

            {prompt}
            {
                props.loggedUser && !props.checkTokenExpiration() && 
                <div className="home-recommended">
                    <h1 className="home-list-title">{t("Recommended for you")}</h1>
                    <PackageDisplay onClickPackage={handleInstall} packagesList={recommendedPackagesList} setPackagesList={setRecommendedPackagesList} loading={loading} />
                </div>
            }
        </div>
    )
}

export default Recommended;
