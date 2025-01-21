import React from 'react';
import {useState, useEffect} from 'react';
import { Link, useNavigate } from "react-router-dom";
import '../styles/Room.css';
import API from '../api';

function Room({ rooms, checkInDate, checkOutDate }) {
  const [role, setRole] = useState(localStorage.getItem("role") || " ");

  const getRoomImage = (roomType) => {
    switch (roomType.toLowerCase()) {
      case 'deluxe':
        return 'src/static/images/deluxe-room.jpeg';
      case 'standart':
        return 'src/static/images/standart-room.jpeg';
      case 'suite':
        return 'src/static/images/suit-room.png';
      default:
        return 'src/static/images/standart-room.jpeg';
    }
  }

  useEffect(() => {
    const role = (localStorage.getItem("role")).toLowerCase();
    setRole(role);
  }, []);
  
  const handleReserve = async (roomId) => {
    try {
      const reservation = {
        customerId: localStorage.getItem("id"),
        roomId: roomId,
        checkInDate: checkInDate || "",
        checkOutDate: checkOutDate || "",
        status: " ",
      };

      const response = await API.post('/booking/create', reservation);
      console.log('Reservation successful:', response.data);
      alert('Reservation successful!');
    } catch (error) {
      console.error('Error creating reservation:', error);
      alert('Failed to reserve room. Please try again.');
    }
  };


  return (
    <div>
      {rooms.length > 0 ? (
        <div className="room-grid">
          {rooms.map((room, index) => (
            <div key={index} className="room-item">
              <img 
                src={getRoomImage(room.roomType)} 
                alt={`${room.roomType} Room`} 
                className="room-image" 
              />
              <div className="room-details">
                <p><strong>Room:</strong> {room.roomNumber}</p>
                <p><strong>Room Type:</strong> {room.roomType}</p>
                <p><strong>Capacity:</strong> {room.capacity}</p>
                <p><strong>Bed Type:</strong> {room.bedType}</p>
                <p><strong>Has View:</strong> {room.hasView ? 'Yes' : 'No'}</p>
                <p><strong>Is Available Now:</strong> {room.available ? 'Yes' : 'No'}</p>
                <p><strong>Price:</strong> ${room.pricePerNight}</p>
                {(role ==="customer") && (
                  <button className="reserve-button" onClick={() => handleReserve(room.id)}>
                    Reserve
                  </button>
                )}
                {(role === "admin" || role === "manager" || role === "personel") && (
                  <Link style={{width: "90%"}} to={`/edit-room/${room.id}`} className="reserve-button">Edit Room</Link>
                )}
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p>No rooms found. Please try another filter.</p>
      )}
    </div>
  );
}

export default Room;