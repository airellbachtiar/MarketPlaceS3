import PackageCard from "../PackageCard";

const PackageListItem = props => {

    let p = props.package;

    return (
        <div style={{marginRight: '1%'}}>
            <PackageCard
                id={p.id}
                title={p.title}    
                category={p.category}
                buttonTitle="Remove"
                onClick={props.onClick}
             />
        </div>
    );
};

export default PackageListItem;