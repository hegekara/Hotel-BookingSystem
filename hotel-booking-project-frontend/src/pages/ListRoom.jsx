import React from 'react';
import { useLocation } from 'react-router-dom';
import Room from '../components/Room';

function ListRoom() {
  const location = useLocation();
  const { rooms } = location.state || { rooms: [] };

  const handleReserve = (roomNumber) => {
    alert(`Room ${roomNumber} reserved!`);
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Available Rooms</h2>
      <Room rooms={rooms} onReserve={handleReserve} />
    </div>
  );
}

export default ListRoom;