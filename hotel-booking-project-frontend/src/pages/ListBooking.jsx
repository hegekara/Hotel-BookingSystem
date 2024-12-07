import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../api';
import Header from '../components/Header';
import Booking from '../components/Booking';

function ListBooking() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [bookings, setBookings] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");
        setIsLoggedIn(!!token);
    
        if (!token) {
            navigate("/");
        } else {
            const email = localStorage.getItem("email");
    
            if (email) {
                API.get(`booking/customer/${email}`)
                    .then(response => {
                        setBookings(response.data);
                    })
                    .catch(error => {
                        console.error("Error fetching bookings:", error);
                    });
            }
        }
    }, [navigate]);

    return (
        <div>
            {isLoggedIn && bookings.length > 0 ? (
                <>
                <Header isLoggedIn={isLoggedIn}/>
                <Booking bookings={bookings} />
                </>
                ) : (
                    <p>No bookings found or you're not logged in.</p>
                )}
        </div>
    );
}

export default ListBooking;
