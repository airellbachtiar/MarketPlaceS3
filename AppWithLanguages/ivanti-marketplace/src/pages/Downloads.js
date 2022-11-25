import React, { useState, useEffect } from "react";
import PackageCard from "../Components/PackageCard";
import PackageCardScroll from "../Components/PackageCardScroll";
import axios from "axios";
import PackageList from "../Components/Package/PackageList";

const Downloads = () => {
    // userId Borek: 282ce4a7-dc69-4adf-b352-ead34ddd875f
    // userId Airell: 392ce4a7-fo30-4fdf-b392-dij34ddd775f
    const userId = "392ce4a7-fo30-4fdf-b392-dij34ddd775f";

    const[user, setUser] = useState();
    const[packages, setPackages] = useState();
    const[prompt, setPrompt] = useState(null);

    useEffect(() => {        
        let config = {
            method: 'get',
            url: 'http://localhost:8080/users/' + userId,
            headers: { }
          };
          
          axios(config)
          .then((response) => {
              console.log("User:", response.data);
            setUser(response.data);
          })
          .catch((error) => {
            console.log("Got this error:", error);
          });

          getPackages();
    }, []);

    function getPackages() {
        let config = {
            method: 'get',
            url: 'http://localhost:8080/users/' + userId + "/downloadedPackages",
            headers: { }
          };

          axios(config)
          .then((response) => {
              console.log("Loaded packages:", response.data);
            setPackages(response.data);
          })
          .catch((error) => {
            console.log("Got this error:", error);
          });
    };

    let handleDelete = id => {
        let config = {
            method: 'delete',
            url: 'http://localhost:8080/users/' + user.id + '/downloadedPackages/' + id,
            headers: { }
          };
          
          axios(config)
          .then(() => {
              getPackages();
              showSuccesPrompt();
              setTimeout(hidePrompt, 5000);
          })
          .catch((error) => {
            console.log("Got this error:", error);
            showFailedPrompt();
            setTimeout(hidePrompt, 5000);
          });
    }

    let usernamePart = "Current user: User not found";
    if(user !== undefined) {
        usernamePart = "Current user: " + user.firstName;
    }

    function showSuccesPrompt() {
        console.log('Show success');
        let newPrompt = (
            <div style={{display: 'flex', justifyContent: 'center'}}>
            <div style={{marginTop: '2%', height: '5%', width: '50%', background: 'green', borderRadius: '8px'}}>
                <p style={{textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%'}}>Succesfully removed package.</p>
            </div>
            </div>
        );
        setPrompt(newPrompt);
    }

    function showFailedPrompt() {
        console.log('Show failure');
        let newPrompt = (
            <div style={{display: 'flex', justifyContent: 'center'}}>
            <div style={{marginTop: '2%', height: '5%', width: '50%', background: 'red', borderRadius: '8px'}}>
                <p style={{textAlign: 'center', color: "white", paddingTop: '1%', paddingBottom: '1%'}}>Something went wrong when removing package.</p>
            </div>
            </div>
        );
        setPrompt(newPrompt);
    }

    function hidePrompt() {
        console.log('Hide success');
        setPrompt(null);
    }

    return(
        <div>
            <h1 style={{textAlign: 'center', marginTop: '1%'}}>My downloads</h1>
            <p style={{textAlign: 'center'}}>See all packages you have downloaded.</p>
            <p style={{textAlign: 'center'}}>{usernamePart}</p>
            {prompt}
            <div style={{display: 'flex', justifyContent: 'center', marginTop: '1%'}}>
            <div style={{width: '90%'}}>
                <PackageList packages={packages} onClick={handleDelete} />
            </div>
            </div>
        </div>
    )
}

export default Downloads;