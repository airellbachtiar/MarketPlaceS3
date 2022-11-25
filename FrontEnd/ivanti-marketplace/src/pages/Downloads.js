import React, { useState, useEffect } from "react";
import axios from "axios";
import PackageList from "../Components/Package/PackageList";
import backgroundImage2 from "../img/tile-2.png";
import "../Components/styles/HomeBackground.css";
import {useTranslation} from "react-i18next";

const Downloads = (props) => {
    const { t } = useTranslation();
    const[loadingRatings, setLoadingRatings] = useState(true);

    let defaultPrompt = (
        <div style={{ display: 'flex', justifyContent: 'center', visibility: 'hidden', marginTop: '-5px' }}>
            <div style={{ marginTop: '2%', height: '5%', width: '50%', background: 'green', borderRadius: '8px' }}>
                <p style={{ textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%' }}>You can't see me!</p>
            </div>
        </div>
    );

    const [packages, setPackages] = useState();
    const [prompt, setPrompt] = useState(defaultPrompt);
    const [ratings, setRatings] = useState([]);
    const [loading, setLoading] = useState(true);
    

    useEffect(() => {
        localStorage.setItem('lastpage', '/downloads');
        props.checkTokenExpiration();
        if (props.loggedUser) {
            getPackages();
            getRatings();
        }
    }, [props]); // eslint-disable-line react-hooks/exhaustive-deps

    function getPackages() {
        let config = {
            method: 'get',
            url: 'http://localhost:8080/users/' + props.loggedUser.id + "/downloadedPackages",
            headers: {}
        };

        axios(config)
            .then((response) => {
                console.log("Loaded packages:", response.data);
                setPackages(response.data);
                setLoading(false);
            })
            .catch((error) => {
                console.log("Got this error:", error);
            });
    };

    function getRatings(ratingId, action) {
        setLoadingRatings(true);
        if (ratingId !== null && ratingId !== undefined) {
            if (action === 'remove') {
                let packageRating = ratings.filter(r => r.id === ratingId);
                let newRatings = ratings;

                let ratingIndex = ratings.indexOf(packageRating);
                newRatings.splice(ratingIndex, 1);
                setRatings(newRatings);
                console.log('Ratings set to: ', newRatings);
                setLoadingRatings(false);
                return;
            }
        }

        let config = {
            method: 'get',
            url: 'http://localhost:8080/ratings/by-user-id/' + props.loggedUser.id,
            headers: {}
        };

        axios(config)
            .then((response) => {
                console.log("Loaded ratings:", response.data);
                setRatings(response.data);
                setLoadingRatings(false);
            })
            .catch((error) => {
                console.log("Got this error:", error);
            });
    };

    let handleDelete = id => {
        let config = {
            method: 'delete',
            url: 'http://localhost:8080/users/' + props.loggedUser.id + '/downloadedPackages/' + id,
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
            }
        };

        axios(config)
            .then(() => {
                getPackages();
                showSuccesPrompt();
                setTimeout(hidePrompt, 5000);
            })
            .catch((error) => {
                console.log("Got this error:", error);
                showFailedPrompt();
                setTimeout(hidePrompt, 4000);
            });
    }

    function showSuccesPrompt(text) {
        let displayText = t('SuccessfullyRemoved');
        if (text) {
            displayText = text;
        }
        console.log('Show success');
        let newPrompt = (
            <div style={{ display: 'flex', justifyContent: 'center', marginTop: '-5px' }}>
                <div style={{ marginTop: '2%', height: '5%', width: '50%', background: 'green', borderRadius: '8px' }}>
                    <p style={{ textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%' }}>{displayText}</p>
                </div>
            </div>
        );
        setPrompt(newPrompt);
    }

    function showFailedPrompt() {
        console.log('Show failure');
        let newPrompt = (
            <div style={{ display: 'flex', justifyContent: 'center', marginTop: '-5px' }}>
                <div style={{ marginTop: '2%', height: '5%', width: '50%', background: 'red', borderRadius: '8px' }}>
                    <p style={{ textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%' }}>{t('SomethingWentWrong')}</p>
                </div>
            </div>
        );
        setPrompt(newPrompt);
    }

    function hidePrompt() {
        console.log('Hide prompt');
        setPrompt(defaultPrompt);
    }

    let addRating = (packageId, stars) => {
        let review = '';
        let userId = props.loggedUser.id
        let ratingObject = {
            packageId,
            userId,
            stars,
            review,
        };

        let packageRating = ratings.filter(r => r.packageId === packageId);
        if (packageRating.length > 0) {
            let id = packageRating[0].id;
            ratingObject = {
                id,
                stars,
                review,
            };
            updateRating(ratingObject);
            return;
        }
        else {
            console.log('Adding rating for: ' + packageId + ' with stars: ' + stars);

            axios
                .post("http://localhost:8080/ratings", ratingObject, {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
                        }
                        })
                .then((response) => {
                    showSuccesPrompt(t('RatingAdded'));
                    setTimeout(hidePrompt, 3000);
                    console.log("New rating added:" + response.status);
                    getPackages();
                    getRatings(response.status, 'add');
                })
                .catch((error) => {
                    console.log("Got this error: " + error);
                });
        }
    }

    let updateRating = (ratingObject) => {
        console.log('Updating this rating: ', ratingObject);
        axios
            .put("http://localhost:8080/ratings", ratingObject, {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
                    }
                    })
            .then((response) => {
                showSuccesPrompt(t('RatingUpdated'));
                setTimeout(hidePrompt, 3000);
                console.log("Rating updated:" + response.status);
                getPackages();
                getRatings();
            })
            .catch((error) => {
                console.log("Got this error: " + error);
            });
    }

    let removeRating = (ratingId) => {
        console.log('Removing rating with id: ' + ratingId);
        axios
            .delete("http://localhost:8080/ratings/" + ratingId, {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
                    }
                    })
            .then((response) => {
                showSuccesPrompt(t('RatingRemoved'));
                setTimeout(hidePrompt, 3000);
                console.log('Removed rating with id: ' + ratingId);
                getPackages();
                getRatings(ratingId, 'remove');
            })
            .catch((error) => {
                console.log('Got this error: ' + error);
            })
    }

    return (
        <div>
            <img className="background_image_1" src={backgroundImage2} alt={backgroundImage2}></img>

            <h1 style={{ textAlign: 'center', marginTop: '1%' }}>{t('myDownloads')}</h1>
            <p style={{ textAlign: 'center' }}>{t('SeeDownloadedPackages')}</p>
            {prompt}
            <div style={{ display: 'flex', justifyContent: 'center', marginTop: '1%' }}>
                <div style={{ width: '90%', marginTop: '-15px' }}>
                    <PackageList
                        packages={packages}
                        onClick={handleDelete}
                        ratings={null}
                        onAddRating={addRating}
                        onRemoveRating={removeRating}
                        loading={loading}
                        loadingRatings={loadingRatings}
                    />
                    <div style={{ marginTop: '2%' }}>
                        <strong >{t('Hint')}</strong>
                        <p>{t('ClickTheStars')}</p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Downloads;