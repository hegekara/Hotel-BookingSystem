import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import API from '../api';
import Header from '../components/Header';
import Personel from '../components/Personel';

function ListPersonel() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [personels, setPersonels] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    setIsLoggedIn(!!token);

    if (!token) {
      navigate("/");
    } else {
      API.get(`/personel/list`)
        .then((response) => {
          setPersonels(response.data);
        })
        .catch((error) => {
          console.error("Error fetching personnel:", error);
        });
    }
  }, [navigate]);

  return (
    <div>
      {isLoggedIn && personels.length > 0 ? (
        <>
          <Header isLoggedIn={isLoggedIn} />
          <div style={{padding:"4rem"}}>
            <div style={{ display: "flex", "justifyContent": "space-between" }}>
                <h2>Personel List</h2>
                <Link to="/add-personel" className="home-button">Add Personel</Link>
            </div>
            <Personel personels={personels} />
          </div>
          
        </>
      ) : (
        <>
          <Header isLoggedIn={isLoggedIn} />
          <br /><br />
          <h2 style={{ margin: "25% 40%" }}>No personnel found or you're not logged in.</h2>
        </>
      )}
    </div>
  );
}

export default ListPersonel;