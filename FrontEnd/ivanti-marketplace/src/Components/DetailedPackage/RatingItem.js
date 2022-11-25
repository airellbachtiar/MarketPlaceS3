import { Rating } from 'react-simple-star-rating';
import {useTranslation} from "react-i18next";

const RatingItem = (props) => {
    const { t } = useTranslation();

    return (
        <div style={{scrollSnapAlign: 'start' ,backgroundColor: 'rgb(239 239 239 / 80%)', borderRadius: '10px', marginBottom: '6px', padding: '4px'}} >
            <p>"{props.description}"</p>
            <Rating
                initialValue={props.averageStarRating}
                size={24}
                transition
                readonly={true}
                fillColorArray={['#f17a45', '#f19745', '#f1a545', '#f1b345', '#f1d045']}
            />
            <p>{t('By')}: {props.name}</p>
        </div>
    )
}

export default RatingItem;