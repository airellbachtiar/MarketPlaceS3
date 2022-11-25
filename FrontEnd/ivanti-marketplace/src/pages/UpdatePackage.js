import React, { useState, useEffect } from "react";
import UploadPackageForm from "../Components/UploadPackageForm";
import { useParams } from "react-router-dom";
import axios from "axios";
import backgroundImage2 from "../img/tile-4.png";

const UpdatePackage = (props) => {
  const [image, setImage] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("");

  let { id } = useParams();

  const getPackage = async () => {
    console.log("Id: ", id);
    let config = {
      method: "get",
      url: "http://localhost:8080/packages/" + id,
      headers: {},
    };

    await axios(config)
      .then(function (response) {
        let { image, title, description, categoryId } = response.data;
        setImage(image);
        setTitle(title);
        setDescription(description);
        setCategory(categoryId);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  useEffect(() => {
    localStorage.setItem('lastpage', '/updatePackage/' + id);
    getPackage();
    console.log("Setthe categoryId: ", category);
  }, []);

  return (
    <>
      <img
        className="background_image_1"
        src={backgroundImage2}
        alt={backgroundImage2}
      ></img>
      <div className="package-form-container">
        <UploadPackageForm
          id={id}
          image={image}
          title={title}
          description={description}
          category={category}
          updateImageProps={setImage}
          updateTitleProps={setTitle}
          updateDescriptionProps={setDescription}
          updateCategoryProps={setCategory}
          loggedUser={props.loggedUser}
          action="update"
        />
      </div>

    </>
  );
};

export default UpdatePackage;
