import React from 'react';
import '../styles/Room.css';
import API from '../api';

function Room({ rooms, checkInDate, checkOutDate }) {
  const getRoomImage = (roomType) => {
    switch (roomType.toLowerCase()) {
      case 'deluxe':
        return 'src/static/images/deluxe-room.jpeg';
      case 'standart':
        return 'src/static/images/standart-room.jpeg';
      case 'suite':
        return 'src/static/images/suit-room.jpeg';
      default:
        return 'src/static/images/standart-room.jpeg';
    }
  }
  
  const handleReserve = async (roomNumber) => {
    try {
      const reservation = {
        email: localStorage.getItem("email"),
        roomNumber: roomNumber,
        checkInDate: checkInDate,
        checkOutDate: checkOutDate,
        status: " ",
      };

      console.log(reservation.email);

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
                <p><strong>Price:</strong> ${room.pricePerNight}</p>
                <button className="reserve-button" onClick={() => handleReserve(room.roomNumber)}>
                  Reserve
                </button>
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