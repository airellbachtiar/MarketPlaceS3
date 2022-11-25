import PackageCard from "../PackageCard";

const UploadedPackageItem = (props) => {
  let p = props.package;

  return (
    <div style={{ marginRight: "1%" }}>
      <PackageCard
        id={p.id}
        title={p.title}
        image={p.image}
        category={p.category}
        loggedUser={props.loggedUser}
        creatorId={p.creatorId}
        buttonTitle="udpateRemove"
        handleDelete={props.handleDelete}
        handleUpdate={props.handleUpdate}
        handleActivate={props.handleActivate}
        active={p.active}
        ratingsAmount={p.ratingsAmount}
        stars={p.averageStarRating}
      />
    </div>
  );
};

export default UploadedPackageItem;
