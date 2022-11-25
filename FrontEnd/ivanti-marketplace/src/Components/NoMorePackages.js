import React from 'react';
import '../Components/styles/NoMorePackages.css';
import {useTranslation} from "react-i18next";

function PackageCard(props) {
    const { t } = useTranslation();
    return (
        <div className="package-card">
            <div className="package-card-container">
                <h1 className='package-card-title-nopackage'>{t('NoPackagesLeft')}</h1>
                <div className='package-card-button-container'>
                    <button className='package-card-show-more-button'>{t('MoreItems')}</button>
                </div>
            </div>
        </div>

    )
}

export default PackageCard