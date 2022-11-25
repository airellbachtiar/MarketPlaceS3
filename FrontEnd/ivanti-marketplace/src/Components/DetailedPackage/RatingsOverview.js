import React, { useState, useEffect } from "react";
import axios from 'axios';
import RatingItem from ".//RatingItem";
import { Rating } from 'react-simple-star-rating';
import { ShimmerBadge, ShimmerThumbnail } from "react-shimmer-effects";
import {useTranslation} from "react-i18next";

const RatingsOverview = (props) => {
    const { t } = useTranslation();

    const[ratingItems, setRatingItems] = useState(null);
    const[createRatingPart, setCreateRatingPart] = useState(<p>{t('DownloadToGiveRating')}.</p>);
    const[userRating, setUserRating] = useState(null);
    const[loadingUserRating, setLoadingUserRating] = useState(true);
    const[viewingExisting, setViewingExisting] = useState(false);
    const[message, setMessage] = useState(null);
    const[totalAmount, setTotalAmount] = useState(props.totalAmount);
    const[averageStarRating, setAverageStarRating] = useState(props.averageStarRating);
    const[render, setRender] = useState({avgRating: null, totalRatings: null, userRating: null});
    const[hasUserRating, setHasUserRating] = useState(false);


    useEffect(() => {
        if(!ratingItems) {
            let thumbnail = (<div style={{marginBottom: '-18px'}}><ShimmerThumbnail height={65} rounded /></div>)
            setRatingItems([thumbnail, thumbnail, thumbnail]);
            loadRatingItems();
        }

        if(props.downloaded) {
            setCreateRatingPart(<button onClick={showCreateRating} style={{width: '45%', backgroundColor: '#670678', padding: '5px', borderRadius: '8px', color: 'white', border: 'none', cursor: 'pointer'}}>Leave a rating</button>);
        }

        if(userRating) {
            showCreateRating();
        }

        createRender();
    }, [props.downloaded, userRating, averageStarRating, totalAmount, loadingUserRating])

    const createRender = () => {
        let totalPart = <div style={{marginTop: '5px'}}><ShimmerBadge width={195} /></div>;
        let ratingPart = <ShimmerBadge width={180} />;
        if(averageStarRating != null) {
            ratingPart = (
            <>
                <Rating
                key={1}
                initialValue={averageStarRating}
                size={24}
                transition
                readonly={true}
                fillColorArray={['#f17a45', '#f19745', '#f1a545', '#f1b345', '#f1d045']}
            />
            <p>({averageStarRating})</p>
            </>
            );

            totalPart = <p style={{marginTop: '5px', color: 'gray'}}>{t('Showing') + ' ' + totalAmount + ' ' + t('OffThe') + ' ' + totalAmount + ' ' + t('Ratings') + '.'}</p>;
        }

        setRender({avgRating: ratingPart, totalRatings: totalPart});

        if(loadingUserRating) {
            setCreateRatingPart(<ShimmerThumbnail height={80} className="m-0" rounded />);
        }
        if(!loadingUserRating) {
            if(userRating) {
                showCreateRating();
            }
            else {
                hideShowCreateRating();
            }

            if(props.downloaded === false) {
                setCreateRatingPart(<p>{t('DownloadToGiveRating')}.</p>);
                return;
            }
        }
    }

    const loadRatingItems = () => {
        let config = {
            method: 'get',
            url: 'http://localhost:8080/ratings/by-package-id/' + props.packageId,
            headers: {}
        };
    
        axios(config)
            .then((response) => {
                console.log("Loaded ratings:", response.data);
                setNewAverage(response.data);
                let newArray = response.data.reverse();
                if(newArray.length === 0) {
                    setLoadingUserRating(false);
                }
                let newRatingItems = newArray.map(generateItems);
                setRatingItems(newRatingItems);
                setTotalAmount(newRatingItems.length);
            })
            .catch((error) => {
                console.log("Got this error:", error);
            });
    }

    const hideShowCreateRating = () => {
        setCreateRatingPart(<button onClick={showCreateRating} style={{width: '45%', backgroundColor: '#670678', padding: '5px', borderRadius: '8px', color: 'white', border: 'none', cursor: 'pointer'}}>{t("Leave a rating")}</button>);
    }

    let addRating = () => {
        if(!viewingExisting) {
            createRating();
        }
        else {
            updateRating();
        }
    }

    let createRating = () => {
        let review = userRating.review;
        let userId = props.loggedUser.id;
        let packageId = props.packageId;
        let stars = userRating.stars;
        if(stars == null || stars === 0) {
            stars = 1;
        }

        let ratingObject = {
            packageId,
            userId,
            stars,
            review,
        };

        console.log('Adding rating for: ' + packageId + ' with stars: ' + stars);

        const token = localStorage.getItem('accessToken');

        axios
            .post("http://localhost:8080/ratings", ratingObject, {
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            })
            .then((response) => {
                //setAverageStarRating(null);
                console.log("New rating added:" + response.status);
                setViewingExisting(true);
                showMessage(<p style={{color: 'green'}}>{t("Rating added")}</p>);
                loadRatingItems();
                setLoadingUserRating(true);
            })
            .catch((error) => {
                console.log("Got this error: " + error);
                showMessage(<p style={{color: 'red'}}>{t("Something went wrong")}</p>);
            });
    }

    let updateRating = () => {
        let review = userRating.review;
        let stars = userRating.stars;
        if(stars == null || stars === 0) {
            stars = 1;
        }
        let id = userRating.id;

        let ratingObject = {
            id,
            stars,
            review,
        };

        console.log('Updating rating with id: ' + id + ' with stars: ' + stars + ' and review: ', review);

        axios
            .put("http://localhost:8080/ratings", ratingObject, {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
                }
            })
            .then((response) => {
                //setAverageStarRating(null);
                console.log("Rating updated:" + response.status);
                setViewingExisting(true);
                showMessage(<p style={{color: 'green'}}>{t("Rating updated")}</p>);
                loadRatingItems();
                setLoadingUserRating(true);
            })
            .catch((error) => {
                console.log("Got this error: " + error);
                showMessage(<p style={{color: 'red'}}>{t("Something went wrong")}</p>);
            });
    }

    const setNewAverage = (array) => {
        if(array.length === 0) {
            setAverageStarRating(0);
            return;
        }

        if(!array) {
            return;
        }
        let allStars = 0;

        for (var i = 0; i < array.length; i++) {
            allStars += array[i].stars;
        }

        let ratingTotal = allStars/array.length;

        setAverageStarRating(Math.round(ratingTotal * 10)/10);
    }

    let functionalCreateRatingPart = (
        <div style={{background: 'rgba(239, 239, 239, 0.8)', padding: '4px', borderRadius: '8px'}}>
        <div style={{display: 'flex', justifyContent: 'begin', columnGap: '2%'}}>
            <p style={{width: 'max-content'}}>{t("Your review:")}</p>
            <Rating
                initialValue={(userRating && userRating.stars)}
                size={24}
                transition
                fillColorArray={['#f17a45', '#f19745', '#f1a545', '#f1b345', '#f1d045']}
                onClick={(num) => setUserRating({...userRating, ['stars']: num/20})}
            />
            {!viewingExisting && <p onClick={hideShowCreateRating} style={{marginLeft: 'auto', paddingRight: '5px', fontSize: '25px', marginTop: '-5px', cursor: 'pointer'}}>x</p>}
        </div>
        <textarea onChange={(event) => setUserRating({...userRating, ['review']: event.target.value})} style={{width: '100%', borderRadius: '8px', padding: '4px'}} placeholder="Type a review" value={userRating && userRating.review}></textarea>
        <div style={{display: 'flex', justifyContent: 'center', columnGap: '10%'}}>
            <button onClick={addRating} style={{width: '45%', backgroundColor: '#670678', padding: '5px', borderRadius: '8px', color: 'white', border: 'none', cursor: 'pointer'}}>{t("Save review")}</button>
            {viewingExisting && <button onClick={() => removeRating(userRating && userRating.id)} style={{width: '45%', backgroundColor: 'red', padding: '5px', borderRadius: '8px', color: 'white', border: 'none', cursor: 'pointer'}}>{t("Delete review")}</button>}
        </div>    
        </div>
    );

    let removeRating = (ratingId) => {
        console.log('Removing rating with id: ' + ratingId);
        axios
            .delete("http://localhost:8080/ratings/" + ratingId, {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
                }
            })
            .then((response) => {
                //setAverageStarRating(null);
                console.log('Removed rating with id: ' + ratingId);
                hideShowCreateRating();
                setViewingExisting(false);
                showMessage(<p style={{color: 'green'}}>{t("Rating removed")}</p>);
                loadRatingItems();
                setUserRating(null);
                setHasUserRating(false);
                setLoadingUserRating(true);
            })
            .catch((error) => {
                console.log('Got this error: ' + error);
                showMessage(<p style={{color: 'red'}}>{t("Something went wrong")}</p>);
            })
    }

    const showCreateRating = () => {
        setCreateRatingPart(functionalCreateRatingPart);
    }

    const generateItems = (obj, index) => {
        let last = false;
        if(index === 0) {
            last = true;
        }

        if(props.loggedUser) {
            if(props.loggedUser.id === obj.userId) {
                setViewingExisting(true);
                setHasUserRating(true);
                setUserRating({id: obj.id, stars: obj.stars, review: obj.review});
                showCreateRating();
            }
        }
        setLoadingUserRating(false);
        if(last) {
            return (
                <div key={obj.id}>
                    <RatingItem 
                    key={obj.id}
                    description={obj.review}
                    averageStarRating={obj.stars}
                    name={obj.userFirstName + ' ' + obj.userLastName}
                    />
                    </div>
            )
        }
        else {
            return (
                <div key={obj.id}>
                    <RatingItem 
                    key={obj.id}
                    description={obj.review}
                    averageStarRating={obj.stars}
                    name={obj.userFirstName + ' ' + obj.userLastName}
                    />
                 </div>
            )
        }
    }

    const showMessage = (message) => {
        setMessage(message);
        setTimeout(setMessage(null), 5000);
    }

    return (
        <div style={{marginLeft: '2%', backgroundColor: 'rgba(211, 211, 211, 0.8)', borderRadius: '10px', padding: '15px', minWidth: '45%'}}>
        <div>
        <div style={{display: 'flex', flexWrap: 'no-wrap'}}>
        <p>{t('AvgRating')}:</p>
        {render.avgRating}
        </div>
        {createRatingPart}
        {message}
        <hr style={{marginTop: '5px', border: '1px solid rgb(103, 6, 120)', borderRadius: '8px'}} />
            <p style={{paddingBottom: '5px'}}>{t('AllRatings')}:</p>
            <div style={{overflowX: 'hidden', overflowY: 'auto', maxHeight: '236px', paddingRight: '5px', scrollSnapType: 'y mandatory'}}>
            {ratingItems}
            </div>
            {render.totalRatings}
        </div>
        </div>
    )
}

export default RatingsOverview;