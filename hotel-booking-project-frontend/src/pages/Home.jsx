import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../styles/Home.css";
import "../styles/Auth.css"

const Home = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    setIsLoggedIn(!!token);
  }, []);

  return (

    <div className="homepage-img">
      <img src="../src/static/images/homepage-img.jpeg" alt="Hotel Image" />
      <div className="content">
        <h1>Welcome to Our Hotel</h1>
        {!isLoggedIn && (
          <div className="auth-buttons">
            <Link to="/login" className="home-button">Login</Link>
            <Link to="/register" className="home-button">Register</Link>
          </div>
        )}
        {isLoggedIn && (
          <div>
            <p className="desc">Enjoy the luxury and comfort of our premium rooms.</p>
            <Link to="/room-filter" className="home-button">Explore Rooms</Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;