import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../styles/Home.css";
import "../styles/Auth.css"
import Header from "../components/Header";

const Home = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [role] = useState(localStorage.getItem("role"));
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    setIsLoggedIn(!!token);

    if((role=="admin")|| (role=="manager")|| (role=="personel")){
      navigate("/admin-panel")
    }
  }, []);

  return (

    <div className="homepage-img">
      <Header isLoggedIn={isLoggedIn} />
      <img src="../src/static/images/homepage-img.jpeg" alt="Hotel Image" />
      <div className="content">
        <h1>Welcome to Ege Hotel</h1>
        {!isLoggedIn && (
          <div className="auth-buttons">
            <Link to="/login" className="home-button">Login</Link>
            <Link to="/register" className="home-button">Register</Link><br /><br /><br />
            <Link to="/personel-login" className="home-button">Personel Login</Link>
          </div>
        )}
        {isLoggedIn && (
          <div>
            <p className="desc">Enjoy the luxury and comfort of our premium rooms.</p>
            <Link to="/rooms" className="home-button">Explore Rooms</Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;