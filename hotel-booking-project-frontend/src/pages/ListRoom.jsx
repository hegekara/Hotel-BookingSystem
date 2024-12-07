import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Room from "../components/Room";
import Header from "../components/Header";

function ListRoom() {
  const location = useLocation();
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const { rooms = [], checkInDate = "", checkOutDate = "" } = location.state || {};

  const handleReserve = (roomNumber) => {
    alert(`Room ${roomNumber} reserved!`);
  };

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    setIsLoggedIn(!!token);

    if (!token) {
      navigate("/");
    }
  }, [navigate]);

  return (
    <div>
      {isLoggedIn && (
        <>
          <Header isLoggedIn={isLoggedIn} />
          <div style={{ padding: "4rem", paddingTop: "4rem" }}>
            <h2>Available Rooms</h2>
            <Room
              rooms={rooms}
              checkInDate={checkInDate}
              checkOutDate={checkOutDate}
              onReserve={handleReserve}
            />
          </div>
        </>
      )}
    </div>
  );
}

export default ListRoom;