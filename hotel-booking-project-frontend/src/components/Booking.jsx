import React, { useEffect, useState } from 'react';
import '../styles/Booking.css';
import API from "../api";
import axios from 'axios';

function Booking({ bookings, onCancel }) {

    const [role, setRole] = useState(localStorage.getItem("role") || "");

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

    useEffect(() => {
        const role = (localStorage.getItem("role")).toLowerCase();
        setRole(role);
      }, []);


    const handleCancel = (id) => {
        if (window.confirm('Are you sure you want to cancel this booking?')) {
            API.delete(`/booking/${id}`)
                .then((response) => {
                    alert(response.data);
                    window.location.reload();
                })
                .catch((error) => {
                    console.error('Failed to cancel booking:', error);
                    alert('An error occurred while trying to cancel the booking.');
                });
        }
    };

    const handleAccept = async (id) => {
        try {
            const response = await API.patch(`/booking/accept/${id}`);
            alert(response.data); 
        } catch (error) {
            console.error("Error accepting reservation:", error);
            alert("An error occurred while accepting the reservation.");
        }
    };
      
    const handleReject = async (id) => {
        try {
            const response = await API.patch(`/booking/reject/${id}`);
            alert(response.data);
        } catch (error) {
            console.error("Error rejecting reservation:", error);
            alert("An error occurred while rejecting the reservation.");
        }
    };

    return (
        <div className="booking-grid">
            {bookings.map((booking, index) => (
                <div key={index} className="booking-item">
                    <div className="booking-details">
                        <p><strong>Room:</strong> {booking.room.roomNumber}</p>
                        {((role ==="admin") || (role ==="manager") || (role ==="personel")) && (
                            <>
                                <p><strong>Customer Name:</strong> {booking.customer.firstName} {booking.customer.lastName}</p>
                                <p><strong>Customer Email:</strong> {booking.customer.email}</p>
                            </>
                        )}
                        <p><strong>Room Type:</strong> {booking.room.roomType}</p>
                        <p><strong>Capacity:</strong> {booking.room.capacity}</p>
                        <p><strong>Bed Type:</strong> {booking.room.bedType}</p>
                        <p><strong>Has View:</strong> {booking.room.hasView ? 'Yes' : 'No'}</p>
                        <p><strong>Price:</strong> ${booking.room.pricePerNight}</p>
                        <p><strong>Check-In Date:</strong> {booking.checkInDate}</p>
                        <p><strong>Check-Out Date:</strong> {booking.checkOutDate}</p>
                        <p><strong>Status:</strong> {booking.status}</p>
                        {((role ==="admin") || (role ==="manager") || (role ==="personel")) && (
                            <>
                                <button
                                    className="accept-button"
                                    onClick={() => handleAccept(booking.id)}>
                                    Accept
                                </button>
                                
                                <button
                                    className="cancel-button"
                                    onClick={() => handleReject(booking.id)}>
                                    Reject
                                </button>
                            </>
                        )}
                        {(role ==="customer") && (
                            <button
                                className="cancel-button"
                                onClick={() => handleCancel(booking.id)}>Cancel
                            </button>
                        )}

                    </div>
                </div>
            ))}
        </div>
    );
}

export default Booking;
