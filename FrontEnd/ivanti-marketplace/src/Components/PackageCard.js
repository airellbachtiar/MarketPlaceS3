import React from "react";
import "../Components/styles/Card.css";
import { Rating } from "react-simple-star-rating";
import trashcan from "../img/trashcan.png";
import { NavLink } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { ShimmerBadge } from "react-shimmer-effects";

function PackageCard(props) {
  const { t } = useTranslation();

  let removeButton = null;
  if (props.editStars) {
    removeButton = (
      <span
        className="package-card-container-button-trashcan"
        style={{ backgroundColor: "transparent" }}
        onClick={() => {
          props.onRemoveRating(props.ratingId);
        }}
      >
        <img alt="Delete icon" src={trashcan}></img>
      </span>
    );
  }

  if (props.ratingId === 0 || props.ratingId == null) {
    removeButton = null;
  }

  let rating = <ShimmerBadge width={140} />;

  let button = (
    <div className="package-card-install-button">
      <span
        onClick={() => {
          props.onClick(props.id);
        }}
      >
        Download
      </span>
    </div>
  );

  if (props.active) {
    if (props.installed) {
      button = <p className="package-card-installed-text">{t("Installed")}</p>;
    } else {
      if (props.buttonTitle === "udpateRemove") {
        button = (
          <div className="card-buttons">
            <button
              onClick={() => {
                props.handleUpdate(props.id);
              }}
            >
              Update
            </button>
            <button
              onClick={() => {
                props.handleDelete(props.id);
              }}
            >
              Remove
            </button>
          </div>
        );
      } else if (props.buttonTitle !== undefined) {
        button = (
          <div className="package-card-install-button">
            <span
              onClick={() => {
                props.onClick(props.id);
              }}
            >
              {props.buttonTitle}
            </span>
          </div>
        );
      }
    }
  } else {
    if (props.buttonTitle === "udpateRemove") {
      button = (
        <div className="card-buttons">
          <button
            onClick={() => {
              props.handleActivate(props.id);
            }}
          >
            Activate
          </button>
        </div>
      );
    } else {
      button = <p className="package-card-inactive-text">Inactive</p>;
    }
  }

  let ratingsAmount = 0;
  if (props.ratingsAmount) {
    ratingsAmount = props.ratingsAmount;
  }

  let ratingsPart = ratingsAmount + " ratings";
  if (ratingsAmount === 1) {
    ratingsPart = ratingsAmount + " rating";
  }

  const handleRating = (rate) => {
    props.onAddRating(props.id, rate / 20);
  };

  console.log("Loading ratings: ", props.loadingRatings);
  if (!props.loadingRatings) {
    rating = (
      <>
        <Rating
          onClick={handleRating}
          initialValue={props.stars}
          size={24}
          transition
          readonly={!props.editStars}
          fillColorArray={[
            "#f17a45",
            "#f19745",
            "#f1a545",
            "#f1b345",
            "#f1d045",
          ]}
        />
        {removeButton}
      </>
    );

    if (props.stars == null) {
      rating = null;
    }
  }

  return (
    <div className="package-card">
      <img
        src={props.image}
        alt="Avatar"
        className="package-card-picture"
      ></img>
      <div className="package-card-container">
        <svg className="package-card-title-container" viewBox="0 0 500 90">
          <foreignObject width="100%" height="100%">
            <NavLink
              to={`/detailed/${props.id}`}
              className="package-card-title"
            >
              {props.title}
            </NavLink>
          </foreignObject>
        </svg>
        <p className="package-card-category">{props.category}</p>
      </div>
      <div className="package-card-rating">
        <div>
          <div
            className="package-card-rating-container"
            style={{ display: "flex", justifyContent: "center" }}
          >
            {rating}
          </div>
          <p style={{ marginTop: "-6%", color: "gray" }}>{ratingsPart}</p>
        </div>
      </div>
      <div
        className="package-card-button"
        style={{ display: "flex", justifyContent: "center" }}
      >
        {button}
      </div>
    </div>
  );
}

export default PackageCard;
