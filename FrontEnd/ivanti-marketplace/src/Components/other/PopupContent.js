import React from "react";
import PopupNavigation from "./PopupNavigation";
import {useState, useEffect} from "react";
import PopupPages from "./PopupPages";
import {useTranslation} from "react-i18next";

const PopupContent = ({ close }) => {
    let totalSteps = 6;
    const [step, setStep] = useState(1);
    const [buttons, setButtons] = useState(['next']);
    const { t } = useTranslation();

    useEffect(() => {
        if (step === totalSteps) {
            setButtons(['back', 'close']);
        }
        else if (step === 1) {
            setButtons(['next']);
        }
        else {
            setButtons(['back', 'next']);
        }
    }, [step]);

    const onBack = () => {
        if (step > 1) {
            setStep(step - 1);
        };
    }

    const onNext = () => {  
        if (step < totalSteps) {
            setStep(step + 1);
        };
    }

    const handleKey = (event) => {
        if (event.key === 'ArrowRight') {
            onNext();
        }
        if (event.key === 'ArrowLeft') {
            onBack();
        }
    }

    return (
    <div onKeyDown={handleKey} tabIndex="0" className="modal">
        <a className="close" onClick={close}>
        &times;
        </a>
        <div className="header"> {t("Welcome!")} </div>
        <div className="content">
        {" "}
        <PopupPages step={step} />
        </div>
        <PopupNavigation step={step} totalSteps={totalSteps} onClose={close} onBack={onBack} onNext={onNext} buttons={buttons} />
    </div>
    );
};

export default PopupContent;