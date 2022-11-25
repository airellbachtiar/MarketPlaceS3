import PackageListItem from "./PackageListItem";
import {useTranslation} from "react-i18next";
import LoadingSpinner from "../other/LoadingSpinner";

const PackageList = props => {
    const { t } = useTranslation();

    let packages = <h1 style={{textAlign: 'center', marginTop: '3%'}}>{t('NoDownloadedPackages')}</h1>
    if(!props.packages) {
    }
    else {
        console.log('Packages have been loaded.');
        packages = props.packages.map(p => {
            if(props.ratings == null) {
                return(
                    <PackageListItem 
                    key={p.id} 
                    package={p} 
                    editStars={true} 
                    onClick={props.onClick} 
                    loadingRatings={props.loadingRatings}
                     />
                )
            }
            let starRating = props.ratings.filter(r => r.packageId === p.id);

            let stars = 0;
            let ratingId = 0;
            if(starRating[0] === null || starRating[0] === undefined) {
                stars = 0;
            }
            else {
                stars = starRating[0].stars;
                ratingId = starRating[0].id;
            }
            
            return(
            <PackageListItem 
            key={p.id} 
            package={p} 
            editStars={true} 
            stars={stars}
            onClick={props.onClick} 
            ratingId={ratingId}
            onAddRating={props.onAddRating}
            onRemoveRating={props.onRemoveRating}
            loadingRatings={props.loadingRatings}
             />
        )});
    }

    let divStyle = {
        display: 'flex',
        flexWrap: 'wrap'
    };

    if(props.loading) {
        packages = <LoadingSpinner />;
    }

    return (
        <div style={divStyle}>
            {packages}
        </div>
    );
}

export default PackageList;