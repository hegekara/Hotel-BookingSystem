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

  const rooms = [
    {
      id: 1,
      name: "Deluxe Room",
      description: "Experience luxury with our deluxe rooms.",
      price: "$150/night",
    },
    {
      id: 2,
      name: "Standard Room",
      description: "Comfortable and affordable standard rooms.",
      price: "$100/night",
    },
    {
      id: 3,
      name: "Suite",
      description: "Spacious suites for a premium stay.",
      price: "$250/night",
    },
  ];

  return (
    <div className="home-container">
      <header className="home-header">
        <h1>Welcome to Our Hotel</h1>
        {!isLoggedIn && (
          <div className="auth-buttons">
            <Link to="/login" className="home-button">Login</Link>
            <Link to="/register" className="home-button">Register</Link>
          </div>
        )}
      </header>

      <section className="hotel-gallery">
        <h2>Explore Our Rooms</h2>
        <div className="room-list">
          {rooms.map((room) => (
            <div key={room.id} className="room-card">
              <h3>{room.name}</h3>
              <p>{room.description}</p>
              <p className="room-price">{room.price}</p>
              <button
                className="home-button"
                onClick={() => alert(`Booking ${room.name}`)}>Book Now</button>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};

export default Home;