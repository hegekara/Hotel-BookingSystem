import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../styles/Header.css";
import { FaUserCircle } from "react-icons/fa";

const Header = ({ isLoggedIn }) => {
  const [isDropdownOpen, setDropdownOpen] = useState(false);
  const navigate = useNavigate();

  const toggleDropdown = () => {
    setDropdownOpen(!isDropdownOpen);
  };

  const logOut = () => {
    localStorage.clear();
    navigate("/home");
    window.location.reload();
  };

  return (
    <header className="header">
      <Link to="/" className="header-logo">
        Ege Otel
      </Link>

      {isLoggedIn && (
        <div className="header-right">
          <Link to="/room-filter" className="header-reservation-button">
            Book Now
          </Link>
          <div className="user-menu">
            <FaUserCircle
              className="user-icon"
              onClick={toggleDropdown}
              size={32}
            />
            {isDropdownOpen && (
              <div className="dropdown-menu">
                <Link to="/profile" className="dropdown-item">Profil</Link>
                <Link to="/password-settings" className="dropdown-item">Change Password</Link>
                <Link to="/list-booking" className="dropdown-item">Bookings</Link>
                <button onClick={logOut} className="dropdown-item">Log Out</button>
              </div>
            )}
          </div>
        </div>
      )}
    </header>
  );
};

export default Header;