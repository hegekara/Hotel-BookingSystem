import React from 'react';
import '../styles/Booking.css';
import API from "../api";

function Booking({ bookings, onCancel }) {
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
    };


    const handleCancel = (id) => {
        if (window.confirm('Are you sure you want to cancel this booking?')) {
            API.delete(`/booking/${id}`)
                .then((response) => {
                    alert(response.data);
                    localStorage.clear();
                    window.location.reload();
                })
                .catch((error) => {
                    console.error('Failed to cancel booking:', error);
                    alert('An error occurred while trying to cancel the booking.');
                });
        }
    };

    return (
        <div className="booking-grid">
            {bookings.map((booking, index) => (
                <div key={index} className="booking-item">
                    <div className="booking-details">
                        <p><strong>Room:</strong> {booking.room.roomNumber}</p>
                        <p><strong>Room Type:</strong> {booking.room.roomType}</p>
                        <p><strong>Capacity:</strong> {booking.room.capacity}</p>
                        <p><strong>Bed Type:</strong> {booking.room.bedType}</p>
                        <p><strong>Has View:</strong> {booking.room.hasView ? 'Yes' : 'No'}</p>
                        <p><strong>Price:</strong> ${booking.room.pricePerNight}</p>
                        <p><strong>Check-In Date:</strong> {booking.checkInDate}</p>
                        <p><strong>Check-Out Date:</strong> {booking.checkOutDate}</p>
                        <p><strong>Status:</strong> {booking.status}</p>
                        <button
                            className="cancel-button"
                            onClick={() => handleCancel(booking.id)}>Cancel</button>
                    </div>
                </div>
            ))}
        </div>
    );
}

export default Booking;
