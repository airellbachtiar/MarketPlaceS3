import PackageCard from "../PackageCard";

const PackageListItem = (props) => {
  let p = props.package;

  return (
    <div style={{ marginRight: "1%" }}>
      <PackageCard
        key={p.id}
        id={p.id}
        title={p.title}
        image={p.image}
        category={p.category}
        buttonTitle="Uninstall"
        onClick={props.onClick}
        stars={props.stars}
        editStars={props.editStars}
        ratingId={props.ratingId}
        ratingsAmount={p.ratingsAmount}
        onAddRating={props.onAddRating}
        onRemoveRating={props.onRemoveRating}
        loadingRatings={props.loadingRatings}
        active={p.active}
      />
    </div>
  );
};

export default PackageListItem;
