import React, { useState, useEffect } from "react";
import axios from "axios";
import UploadedPackageList from "../Components/Package/UploadedPackageList";
import { NavLink, useNavigate } from "react-router-dom";
import "../Components/styles/Card.css";
import backgroundImage2 from "../img/tile-4.png";
import { useTranslation } from "react-i18next";
import "../Components/styles/Uploads.css"

const Uploads = (props) => {
  const { t } = useTranslation();
  const [packages, setPackages] = useState(null);
  const [loading, setLoading] = useState(true);
  let navigate = useNavigate();

  function getPackages() {
    if (props.loggedUser) {
      let config = {
        method: "get",
        url: "http://localhost:8080/users/" + props.loggedUser.id + "/uploads",
        headers: {},
      };

      axios(config)
        .then((response) => {
          setPackages(response.data);
          setLoading(false);
        })
        .catch((error) => {
          console.log("Got this error:", error);
        });
    }
  }

  let handleDelete = (id) => {
    let config = {
      method: "delete",
      url: "http://localhost:8080/packages/" + id,
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
      },
    };

    axios(config)
      .then(() => {
        getPackages();
      })
      .catch((error) => {
        console.log("Got this error:", error);
      });
  };

  let handleUpdate = (id) => {
    let url = "/updatePackage/" + id;
    navigate(url);
  };

  let handleActivate = (id) => {
    let config = {
      method: "put",
      url: "http://localhost:8080/packages/activate/" + id,
      headers: {},
    };

    axios(config)
      .then(() => {
        getPackages();
      })
      .catch((error) => {
        console.log("Got this error:", error);
      });
  };

  useEffect(() => {
    localStorage.setItem('lastpage', '/uploads');
    props.checkTokenExpiration();
    if (props.loggedUser) {
      getPackages();
    }
  }, [props.loggedUser]); // eslint-disable-line react-hooks/exhaustive-deps

  return (
    <div className="wrapper-overview">
      <img
        className="background_image_1"
        src={backgroundImage2}
        alt={backgroundImage2}
      ></img>

      <div>
        <h1 style={{ textAlign: "center", marginTop: "1%" }}>{t("myUploads")}</h1>
        <p style={{ textAlign: "center" }}>
          {t("See all packages you have uploaded.")}
        </p>
        <div className="upload-add-package-button-container">
          <NavLink className="upload-add-package-button" to="/newUpload">
            {t("Add Package")}
          </NavLink>
        </div>
      </div>

      <div
        style={{ display: "flex", justifyContent: "center", marginTop: "1%" }}
      >
        <div style={{ width: "90%", marginTop: "-15px" }}>
          {
            <UploadedPackageList
              loggedUser={props.loggedUser}
              packages={packages}
              handleDelete={handleDelete}
              handleUpdate={handleUpdate}
              handleActivate={handleActivate}
              loading={loading}
            />
          }
        </div>
      </div>
    </div>
  );
};

export default Uploads;
