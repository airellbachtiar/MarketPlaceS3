import React, { useState } from "react";
import UploadPackageForm from "../Components/UploadPackageForm";

const UploadPackage = () => {
  const [image, setImage] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("");

  return (
    <>
      <UploadPackageForm
        image={image}
        title={title}
        description={description}
        category={category}
        updateImageProps={setImage}
        updateTitleProps={setTitle}
        updateDescriptionProps={setDescription}
        updateCategoryProps={setCategory}
      />
    </>
  );
};

export default UploadPackage;
