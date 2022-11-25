import React, { useState, useEffect } from "react";
import axios from "axios";
import { useTranslation } from "react-i18next";
import CategoryOption from "./CategoryOption";
import "../Components/styles/PackageForm.css";
import { useNavigate } from "react-router-dom";

const UploadPackageForm = (props) => {
  const { t } = useTranslation();
  const [result, setResult] = useState("");
  const [resultColor, setResultColor] = useState("");
  const [categories, setCategories] = useState(null);
  let navigate = useNavigate();

  let button = <button type="submit">{t("UploadPackage")}</button>;

  if (props.action === "update") {
    button = <button type="submit">{t("UpdatePackage")}</button>;
  }

  const getCategories = () => {
    axios
      .get("http://localhost:8080/categories")
      .then((response) => {
        console.log("Got categories: ", response.data);
        setCategories(response.data);
      })
      .catch((error) => {
        console.log("Error while loading categories: ", error);
      });
  };

  const postPackage = (packageObject) => {
    axios
      .post("http://localhost:8080/packages", packageObject, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
          },
          })
      .then((response) => {
        console.log("New package added:" + response.status);
        setResultColor("green");
        setResult("Successfully uploaded package");
        navigate("/uploads");
      })
      .catch((error) => {
        setResultColor("red");
        setResult("Got this error: " + error);
      });
  };

  const updatePackage = (packageObject) => {
    axios
      .put("http://localhost:8080/packages/" + props.id, packageObject, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
          },
          })
      .then((response) => {
        console.log("Was package updated?:" + response.status);
        setResultColor("green");
        setResult("Successfully updated package");
        navigate("/uploads");
      })
      .catch((error) => {
        setResultColor("red");
        setResult("Got this error: " + error);
      });
  };

  const uploadBlobImage = (e) => {
    var reader = new FileReader();
    reader.onload = function (e) {  
      props.updateImageProps(e.target.result);
    };
    reader.readAsDataURL(e.target.files[0]);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const image = props.image;
    const title = props.title;
    const description = props.description;
    const categoryId = props.category;

    console.log("Catgeroy id: ", { categoryId });

    const creatorId = props.loggedUser.id;

    const packageObject = {
      image,
      title,
      description,
      categoryId,
      creatorId,
    };

    console.log(packageObject);

    if (props.action === "update") {
      updatePackage(packageObject);
    } else {
      postPackage(packageObject);
    }
  };

  useEffect(() => {
    getCategories();
  }, []);

  return (
    <div className="upload_container">
      <form onSubmit={handleSubmit}>
        <label>{t("Image")}</label>
        <input
          type="file"
          required
          onChange={(e) => {
            uploadBlobImage(e);
          }}
          accept="image/*"
        />
        <label>{t("Title")}</label>
        <input
          required
          value={props.title}
          onChange={(e) => props.updateTitleProps(e.target.value)}
        />
        <label>{t("Description")}</label>
        <textarea
          required
          value={props.description}
          onChange={(e) => props.updateDescriptionProps(e.target.value)}
        />
        <label>{t("Category")}</label>
        <select
          value={props.category}
          placeholder="Choose category"
          onChange={(e) => props.updateCategoryProps(e.target.value)}
        >
          {props.action === "update" ? (
            <></>
          ) : (
            <CategoryOption category={
              {
                id: "",
                name: t("SelectCategory"),
              }
            } />
          )}
          {categories &&
            categories.map((category) => (
              <CategoryOption key={category.id} category={category} />
            ))}
        </select>
        {button}
        {result && (
          <div
            style={{
              width: "100%",
              height: "20px",
              background: resultColor,
              color: "white",
              marginTop: "10px",
            }}
          >
            {result}
          </div>
        )}
      </form>
    </div>
  );
};

export default UploadPackageForm;
