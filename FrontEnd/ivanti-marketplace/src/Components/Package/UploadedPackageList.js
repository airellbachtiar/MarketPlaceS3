import LoadingSpinner from "../other/LoadingSpinner";
import UploadedPackageItem from "./UploadedPackageItem";
import React, { useEffect, useState } from "react";

const UploadedPackageList = (props) => {
  const [list, setList] = useState(<LoadingSpinner />);

  useEffect(() => {
    console.log("Loading: ", props.loading);
    if (!props.loading) {
      setList(
        props.packages.map((p) => (
          <UploadedPackageItem
            key={p.id}
            package={p}
            loggedUser={props.loggedUser}
            handleDelete={props.handleDelete}
            handleUpdate={props.handleUpdate}
            handleActivate={props.handleActivate}
          />
        ))
      );
    }
  }, [props.loading, props.packages]);

  let divStyle = {
    display: "flex",
    flexWrap: "wrap",
  };
  return <div style={divStyle}>{list}</div>;
};

export default UploadedPackageList;
