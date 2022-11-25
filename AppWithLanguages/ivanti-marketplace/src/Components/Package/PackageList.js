import PackageCard from "../PackageCard";
import PackageListItem from "./PackageListItem";

const PackageList = props => {

    let packages = <h1 style={{textAlign: 'center', marginTop: '3%'}}>There are not downloaded packages yet.</h1>
    if(props.packages === undefined || props.packages.length === 0) {
    }
    else {
        packages = props.packages.map(p => (
            <PackageListItem key={p.id} package={p} onClick={props.onClick} />
        ));
    }

    let divStyle = {
        display: 'flex',
        flexWrap: 'wrap'
    };

    return (
        <div style={divStyle}>
            {packages}
        </div>
    );
}

export default PackageList;