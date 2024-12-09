import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../styles/Header.css";
import { FaUserCircle } from "react-icons/fa";

const Header = ({ isLoggedIn }) => {
  const [isDropdownOpen, setDropdownOpen] = useState(false);
  const [role, setRole] = useState(localStorage.getItem("role") || "");
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
      {role==="customer" && (
      <Link to="/" className="header-logo">
        Ege Otel
      </Link>
      )}

      {((role==="admin") || (role==="manager") || (role==="personel")) && (
      <Link to="/admin-panel" className="header-logo">
        Ege Otel
      </Link>
      )}

      {isLoggedIn && (
        <div className="header-right">
          {(role ==="customer") && (
            <Link to="/room-filter" className="header-reservation-button">
              Book Now
            </Link>
          )}
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
                {(role === "customer") && (
                  <Link to="/list-booking" className="dropdown-item">Bookings</Link>
                )}
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