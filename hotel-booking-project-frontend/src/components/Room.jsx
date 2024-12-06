import React from 'react';
import '../styles/Room.css';

function Room({ rooms, onReserve }) {
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
                <p><strong>Room Number:</strong> {room.roomNumber}</p>
                <p><strong>Capacity:</strong> {room.capacity}</p>
                <p><strong>Room Type:</strong> {room.roomType}</p>
                <p><strong>Price:</strong> ${room.pricePerNight}</p>
                <button 
                  className="reserve-button" 
                  onClick={() => onReserve(room.roomNumber)}
                >
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