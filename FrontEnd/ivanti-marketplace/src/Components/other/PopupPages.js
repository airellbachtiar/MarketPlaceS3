import packagecardsc from "../../img/packagecard.png";
import filterbar from "../../img/filterbar.png";
import navigationclosed from "../../img/navigationclosed.png";
import navigationopen from "../../img/navigationopen.png";
import profileclosed from "../../img/profileclosed.png";
import profileopen from "../../img/profileopen.png";
import languagesopen from "../../img/languagesopen.png";
import "./PopupPagesStyle.css"
import {useTranslation} from "react-i18next";

const PopupPages = (props) => {
    const { t } = useTranslation();

    let page1 = (
        <>
            {t("The Ivanti Marketplace allows you to quickly access all packages Ivanti offers.")}
            <br />
            {t("We will show you a few basic functions of the Marketplace.")}
        </>
    )

    let page2 = (
        <div className="page2-container">
            <p className="home-page-p">{t("Home page information:")}</p>
            <div className="page2-content-container">
                <img className="packagecard-image" src={packagecardsc} alt="Package card screenshot"></img>
                <div className="page2-second-container">
                <p>{t("In the background you can see the home page. This page shows all the packages that are available. To the left you can see a package card.")} </p>
                <ul>
                    <li>{t("At the top you can see the logo of a package.")}</li>
                    <li>{t("Below this you can see the title of the package. Click the title to see more information about the package.")}</li>
                    <li>{t("Below the package title is the category of the package.")}</li>
                    <li>{t("The stars show the average rating of a package.")}</li>
                    <li>{t("At the bottom is the download/uninstall button. This button speaks for itself.")}</li>
                </ul>
                <div className="page2-third-container">
                    <p className="page2-filter-title">{t("Filter bar:")}</p>
                    <img className="filterbar-image" src={filterbar} alt="Filter bar"></img>
                    <div className="page2-fourth-container">
                        <p>{t("Using the filter bar you can select a category to filter packages. You can also choose to sort by package title from A-Z or from Z-A. Finally it is possible to search through the packages by the title.")}</p>
                    </div>
                </div>
                </div>
            </div>
        </div>
    )

    let page3 = (
        <div className="page34-first-container">
        <div className="width48">
            <p className="smallTitle">{t("Open navigation:")}</p>
            <p>{t("To open the navigation panel click the button in the top left of your screen. Make sure you are logged in to be able to access all pages.")}</p>
            <div className="page34-second-container">
            <img className="profileclosed-image" src={navigationclosed} alt="Button to open the navigation tab"></img>
            <p style={{marginLeft: '4px'}}>{t("This is the button to look for.")}</p>
            </div>
        </div>
        <div className="width48">
            <p className="smallTitle">{t("Page navigation:")}</p>
            <p>{t("When the navigation panel is opened you can click any of the links. Click the X to close the page navigation.")}</p>
            <img className="navigationopen-image" src={navigationopen} alt="Page navigation"></img>
        </div>
        </div>
    )

    let page4 = (
        <div className="page34-first-container">
        <div className="width48">
            <p className="smallTitle">{t("Open profile and languages:")}</p>
            <p>{t("To open the profile and languages tab click the button in the top right of your screen.")}</p>
            <div className="page34-second-container">
            <img className="profileclosed-image" src={profileclosed} alt="Button to open the profile and languages tab"></img>
            <p style={{marginLeft: '4px'}}>{t("This is the button to look for.")}</p>
            </div>
            <p className="small-title-2">{t("Languages:")}</p>
            <p>{t("At this moment you can change the language of the app to English or Dutch.")}</p>
            <img className="languages-open-image" src={languagesopen} alt="Choose a language"></img>

        </div>
        <div className="width48">
            <p className="smallTitle">{t("Profile and languages tab:")}</p>
            <p>{t('When the profile and languages tab is opened you can click your username to go to your profile page. You can change the language of the web app by clicking on languages. Finally close the tab by clicking the open button again.')}</p>
            <img className="languages-open-image" src={profileopen} alt="Profile navigation"></img>
            <p style={{marginTop: '10px'}}>{t("Finally this tab also enables you to log in or log out.")}</p>
        </div>
        </div>
    )

    let page5  = (
        <div className="page56-container">
        <p className="page56-title">{t("My downloads")}</p>
        <p className="page56-content" style={{marginTop: '10px'}}>{t("When you have downloaded a package it will appear on the my downloads page. You can access this page in the menu on the left side of the screen. On this page you will also be able to uninstall downloaded packages.")}</p>
        </div>
    )

    let page6 = (
        <div className="page56-container">
            <p className="page56-title">{t("My uploads & uploading a package")}</p>
            <p className="page56-content">{t("This section is only relevant to content creators. To view your uploads go to the uploads page. This page can be found in the menu on the left. To upload a new package go to the upload package page which also can be found on the left. To upload a package, choose an icon, think of a title and description and finally select a category. On the uploads page you can find all the packages you have uploaded. If you click remove a package will be set to inactive and won't be shown to new users. Current users can still see the package in their downloads. If you click update you will be able to update the details of a package.")}</p>
        </div>
    )

    let pages = [page1, page2, page3, page4, page5, page6];

    return (
        <p>{pages[props.step - 1]}</p>
    )
 }

 export default PopupPages;