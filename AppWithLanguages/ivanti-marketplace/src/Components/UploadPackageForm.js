import React from "react";
import axios from "axios";

const UploadPackageForm = (props) => {
  const handleSubmit = (e) => {
    e.preventDefault();
    const objImage = props.image;
    const objTitle = props.title;
    const objDescription = props.description;
    const objCategory = props.category;

    const packageObject = { objImage, objTitle, objDescription, objCategory };

    let config = {
      method: "post",
      url: "http://localhost:8080/packages",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(packageObject),
    };

    axios(config)
      .then((response) => {
        console.log("New package added:" + response.status);
      })
      .catch((error) => {
        console.log("Got this error:", error);
      });
  };

  return (
    <div className="upload_container">
      <form onSubmit={handleSubmit}>
        <label>Image</label>
        <input
          type="file"
          required
          value={props.image}
          onChange={(e) => props.updateImageProps(e.target.value)}
        />
        <label>Title</label>
        <input
          required
          value={props.title}
          onChange={(e) => props.updateTitleProps(e.target.value)}
        />
        <label>Description</label>
        <textarea
          required
          value={props.description}
          onChange={(e) => props.updateDescriptionProps(e.target.value)}
        />
        <label>Category</label>
        <select
          value={props.category}
          onChange={(e) => props.updateCategoryProps(e.target.value)}
        >
          <option value="category1">Category1</option>
          <option value="category2">Category2</option>
          <option value="category3">Category3</option>
        </select>
        <button type="submit">Upload package</button>
      </form>
    </div>
  );
};

export default UploadPackageForm;
