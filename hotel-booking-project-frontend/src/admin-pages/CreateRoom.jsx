import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Header from "../components/Header";
import API from '../api';
import "../styles/RoomCreate.css"

function CreateRoom() {
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [role, setRole] = useState(localStorage.getItem("role") || "");

  const [roomNumber, setRoomNumber] = useState("");
  const [roomType, setRoomType] = useState("");
  const [capacity, setCapacity] = useState("");
  const [bedType, setBedType] = useState("");
  const [pricePerNight, setPricePerNight] = useState("");
  const [isAvailable, setIsAvailable] = useState(true);
  const [hasView, setHasView] = useState(false);

  const [roomTypes, setRoomTypes] = useState([]);
  const [bedTypes, setBedTypes] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    const role = localStorage.getItem("role").toLowerCase();
    setIsLoggedIn(!!token);
    setRole(role);


    if (!token) {
      navigate("/");
    }

    const fetchTypes = async () => {
      try {
        const roomTypeResponse = await API.get("/room/room-types");
        console.log(roomTypeResponse.data);

        setRoomTypes(roomTypeResponse.data);

        const bedTypeResponse = await API.get("/room/bed-types");
        console.log(bedTypeResponse.data);
        setBedTypes(bedTypeResponse.data);
      } catch (error) {
        console.error("Error fetching room/bed types:", error);
      }
    };

    fetchTypes();
  }, [navigate]);

  const handleCreateRoom = async (e) => {
    e.preventDefault();
    const newRoom = {
      roomNumber,
      roomType,
      capacity: parseInt(capacity),
      bedType,
      pricePerNight: parseFloat(pricePerNight),
      isAvailable: true,
      hasView,
    };

    try {
      const response = await API.post("/room/create", newRoom);
      alert("Room created successfully!");
      setRoomNumber("");
      setRoomType("");
      setCapacity("");
      setBedType("");
      setPricePerNight("");
      setIsAvailable(true);
      setHasView(false);
    } catch (error) {
      console.error("Error creating room:", error);
      alert("Failed to create room. Please try again.");
    }
  };

  return (
    <div>
      {isLoggedIn && (role === "admin" || role === "manager" || role === "personel") && (
        <>
          <Header isLoggedIn={isLoggedIn} role={role} />
          <br /><br />
          <div className="room-form-container">
            <h2>Room Form</h2>
            <form className="room-form" onSubmit={handleCreateRoom}>
              <div className="form-group">
                <label>Room Number:</label>
                <input
                  type="text"
                  value={roomNumber}
                  onChange={(e) => setRoomNumber(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <label>Room Type:</label>
                <select
                  value={roomType}
                  onChange={(e) => setRoomType(e.target.value)}
                  required
                >
                  <option value="">Select Room Type</option>
                  {roomTypes.map((type, index) => (
                    <option key={index} value={type}>{type}</option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Capacity:</label>
                <input
                  type="number"
                  value={capacity}
                  onChange={(e) => setCapacity(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <label>Bed Type:</label>
                <select
                  value={bedType}
                  onChange={(e) => setBedType(e.target.value)}
                  required
                >
                  <option value="">Select Bed Type</option>
                  {bedTypes.map((type, index) => (
                    <option key={index} value={type}>{type}</option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Price per Night:</label>
                <input
                  type="number"
                  step="10.0"
                  value={pricePerNight}
                  onChange={(e) => setPricePerNight(e.target.value)}
                  required
                />
              </div>
              <div className="form-group-inline">
                <label>Has View:</label>
                <input
                  type="checkbox"
                  checked={hasView}
                  onChange={(e) => setHasView(e.target.checked)}
                />
              </div>
              <button type="submit">Create Room</button>
            </form>
          </div>
        </>
      )}
    </div>
  );
}

export default CreateRoom;
