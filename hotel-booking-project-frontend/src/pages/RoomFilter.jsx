import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "../styles/RoomFilter.css"
import API from '../api';

function RoomFilter() {
  const [roomTypes, setRoomTypes] = useState([]);
  const [checkInDate, setCheckInDate] = useState('');
  const [checkOutDate, setCheckOutDate] = useState('');
  const [selectedRoomType, setSelectedRoomType] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchEnums = async () => {
      try {
        const roomTypesResponse = await API.get('/room/room-types');
        setRoomTypes(roomTypesResponse.data);
      } catch (error) {
        console.error('Error fetching enums:', error);
      }
    };
    fetchEnums();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const filterData = {
      roomType: selectedRoomType,
      checkInDate,
      checkOutDate,
    };

    try {
      const response = await API.get('/room/filter', { params: filterData });
      console.log(response);
      navigate('/list-room', { state: { rooms: response.data } });
    } catch (error) {
      console.error('Error filtering rooms:', error);
    }
  };

  return (
    <div className="room-filter-container">
      <h2>Room Filter</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Room Type:</label>
          <select
            value={selectedRoomType}
            onChange={(e) => setSelectedRoomType(e.target.value)}
          >
            <option value="">Select Room Type</option>
            {roomTypes.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Check-In Date:</label>
          <input
            type="date"
            value={checkInDate}
            onChange={(e) => setCheckInDate(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Check-Out Date:</label>
          <input
            type="date"
            value={checkOutDate}
            onChange={(e) => setCheckOutDate(e.target.value)}
          />
        </div>

        <button type="submit">Filter Rooms</button>
      </form>
    </div>
  );
}

export default RoomFilter;
