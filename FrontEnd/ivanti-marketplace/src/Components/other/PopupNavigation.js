import {useEffect, useState} from 'react';
import {useTranslation} from "react-i18next";

const PopupNavigation = (props) => {
    const { t } = useTranslation();

    let defaultBackButton = (
        <button className='back-button' onClick={props.onBack}>{t("Back")}</button>
    );

    let hiddenBackButton = (
        <button style={{visibility: 'hidden'}} className='back-button' onClick={props.onBack}>{t("Back")}</button>
    )

    let defaultNextButton = (
        <button className='next-button' onClick={props.onNext}>{t("Next")}</button>
    );

    let hiddenNextButton = (
        <button style={{visibility: 'hidden'}} className='next-button' onClick={props.onNext}>{t("Next")}</button>
    );

    let closeButton = (
        <button className='next-button' onClick={props.onClose}>{t("Close")}</button>
    );

    const [backButton, setBackButton] = useState(null);
    const [nextButton, setNextButton] = useState(null);

    useEffect(() => {
        if(props.buttons.includes('back')) {
            setBackButton(defaultBackButton);
        }
        else {
            setBackButton(hiddenBackButton);
        };

        if(props.buttons.includes('next')) {
            setNextButton(defaultNextButton);
        }
        else if (props.buttons.includes('close')) {
            setNextButton(closeButton);
        }
        else {
            setNextButton(hiddenNextButton);
        };

    }, [props.buttons]);

    return (
        <div className="popup-navigation">
            {backButton}
            <p>{props.step} {t("out of")} {props.totalSteps}</p>
            {nextButton}
        </div>
    )
}

export default PopupNavigation;