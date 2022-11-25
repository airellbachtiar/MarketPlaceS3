import React, { useEffect, useState } from "react";
import PackageDisplay from "../Components/HomePage/PackageDisplay";
import axios from "axios";
import backgroundImage1 from "../img/tile-1.png";
import "../Components/styles/HomeBackground.css";
import "../Components/styles/Home.css";
import { useTranslation } from "react-i18next";
import Sort from "../Components/Sorting";
import { useNavigate } from "react-router-dom";
import Popup from 'reactjs-popup';
import PopupContent from "../Components/other/PopupContent";
import "../Components/other/PopupStyle.css";

const Home = (props) => {
    const { t } = useTranslation();

    const [packagesList, setPackagesList] = useState([]);
    const [topRatedPackagesList, setTopRatedPackagesList] = useState([]);
    const [loading, setLoading] = useState(true);

    const [downloadedPackages, setDownloadedPackages] = useState();
    const [category, setCategory] = useState();
    const [categories, setCategories] = useState([]);

    const [prompt, setPrompt] = useState(null);
    const [filteredPackages, setFilteredPackages] = useState([]);
    const [search, setSearch] = useState("");
    const [sortingOrder, setSortingOrder] = useState("asc");
    const [tutorialStep, setTutorialStep] = useState(null);

    let navigate = useNavigate();


    useEffect(() => {
        localStorage.setItem('lastpage', '/');
        getPackages();
        getTopRatedPackages();
        getCategories();
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
                setPackagesList(response.data)
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

    function getTopRatedPackages() {
        var config = {
            method: 'get',
            url: 'http://localhost:8080/packages/topRated',
            headers: {}
        };

        axios(config)
            .then(function (response) {
                setTopRatedPackagesList(response.data);
                setLoading(false);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

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

    function showDownloadingPrompt() {
        console.log('Show downloading prompt');
        let newPrompt = (
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                <div style={{ marginTop: '2%', height: '5%', width: '50%', background: 'green', borderRadius: '8px' }}>
                    <p style={{ textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%' }}>{t("Downloading package..")}.</p>
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
            filteredPackages.forEach(p => {
                p.installed = false;
                downloadedPackages.forEach(dp => {
                    if (p.id === dp.id) {
                        p.installed = true;
                    }
                });
            })
            topRatedPackagesList.forEach(p => {
                p.installed = false;
                downloadedPackages.forEach(dp => {
                    if (p.id === dp.id) {
                        p.installed = true;
                    }
                });
            })
        }

    }

    function getCategories() {
        axios
            .get("http://localhost:8080/categories")
            .then((response) => {
                console.log("Got categories: ", response.data);
                setCategories(response.data)
            })
            .catch((error) => {
                console.log("Error while loading categories: ", error);
            });
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

    const searchBarOnChange = (e) => {
        setSearch(e.target.value);
    }

    const onChangeCategory = event => {
        setCategory(event.target.value);
    }

    const onChangeSortingOrder = event => {
        setSortingOrder(event.target.value);
    }

    useEffect(() => {
        let filtered = packagesList;
        console.log(filtered);
        if (search !== "") {
            filtered = filtered.filter(p => {
                return p.title.toLowerCase().includes(search.toLowerCase());
            });
        }
        if (category !== "all" && category !== undefined) {
            filtered = filtered.filter(p => {
                return p.categoryId.includes(category);
            })
        }

        //Sorting
        if (sortingOrder === 'asc') {
            filtered = [].concat(filtered)
                .sort((a, b) => a.title > b.title ? 1 : -1);
        }
        if (sortingOrder === 'desc') {

            filtered = [].concat(filtered)
                .sort((a, b) => a.title > b.title ? -1 : 1);
        }
        setFilteredPackages(filtered);
    }, [search, category, packagesList, sortingOrder]);

    loadDownloadedInfo();

    console.log("Filtered packages:", filteredPackages);

    return (
        <div>
            <img className="background_image_1" src={backgroundImage1} alt={backgroundImage1}></img>

            <div className="package_modifier_container">
                <Popup modal trigger={<button className="popup-button">{t("New here?")}</button>}>
                    {close => <PopupContent close={close} />}
                </Popup>
                <select className="package-filter" onChange={onChangeCategory}>
                    <option key={"all"} value="all">{t("All")}</option>
                    {
                        categories.map(c => {
                            return <option key={c.id} value={c.id}>{c.name}</option>
                        })
                    }
                </select>
                <Sort onChangeSortingOrder={onChangeSortingOrder} />
                <div className="home-search-bar-container">
                    <input type="text" placeholder={t('Search')} className="home-search-bar" value={search} onChange={searchBarOnChange}></input>
                </div>
            </div>

            {prompt}
            <div className="home-discover">
                <h1 className="home-list-title">{t("Discover")}</h1>
                <PackageDisplay onClickPackage={handleInstall} packagesList={filteredPackages} setPackagesList={setFilteredPackages} loading={loading} />
            </div>
            <div className="home-top-rated">
                <h1 className="home-list-title">{t("Top Rated")}</h1>
                <PackageDisplay onClickPackage={handleInstall} packagesList={topRatedPackagesList} setPackagesList={setTopRatedPackagesList} loading={loading} />
            </div>
            
        </div>
    )
}

export default Home;
