import React, { useState } from "react";
import UploadPackageForm from "../Components/UploadPackageForm";
import backgroundImage2 from "../img/tile-4.png";

const UploadPackage = (props) => {
  const [image, setImage] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("");

  localStorage.setItem('lastpage', '/newUpload');

  return (
    <>
      <img
        className="background_image_1"
        src={backgroundImage2}
        alt={backgroundImage2}
      ></img>
      <div className="package-form-container">
        <UploadPackageForm
          image={image}
          title={title}
          description={description}
          category={category}
          updateImageProps={setImage}
          updateTitleProps={setTitle}
          updateDescriptionProps={setDescription}
          updateCategoryProps={setCategory}
          loggedUser={props.loggedUser}
        />
      </div>
    </>
  );
};

export default UploadPackage;
