import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../api';
import Header from '../components/Header';
import "../styles/Profile.css";

function Profile() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [email, setEmail] = useState(localStorage.getItem("email") || "");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");
        setIsLoggedIn(!!token);

        if (!token) {
            navigate("/");
        } else if (email) {
            API.get(`/customer/${email}`)
                .then((response) => {
                    const customer = response.data;
                    setEmail(customer.email);
                    setFirstName(customer.firstName);
                    setLastName(customer.lastName);
                    setPhoneNumber(customer.phoneNumber);
                })
                .catch((error) => {
                    console.error("Error fetching customer data:", error);
                });
        }
    }, [email, navigate]);

    const handleUpdate = (e) => {
        e.preventDefault();

        API.put(`/customer/update/${email}`, {
            email,
            firstName,
            lastName,
            phoneNumber,
        })
            .then(() => {
                alert("Profile updated successfully!");
            })
            .catch((error) => {
                console.error("Error updating profile:", error);
                alert("Failed to update profile.");
            });
    };

    const handleDelete = () => {
        if (window.confirm("Are you sure you want to delete this profile?")) {
            API.delete(`/customer/delete/${email}`)
                .then(() => {
                    alert("Profile deleted successfully!");
                    navigate("/");
                })
                .catch((error) => {
                    if (error.response && error.response.status === 409) {
                        alert("Cannot delete profile: The user has active bookings.");
                    }else{
                        console.error("Error deleting profile:", error);
                        alert("Failed to delete profile.");
                    }
                });
        }
    };

    return (
        <div>
            {isLoggedIn && (
                <>
                    <Header isLoggedIn={isLoggedIn} />
                    <br />
                    <div className="profile-container">
                        <h2>Profile</h2>
                        <form onSubmit={handleUpdate}>
                            <div className="form-group">
                                <label>Email:</label>
                                <input type="email" value={email} disabled readOnly />
                            </div>
                            <div className="form-group">
                                <label>First Name:</label>
                                <input
                                    type="text"
                                    value={firstName}
                                    onChange={(e) => setFirstName(e.target.value)}
                                />
                            </div>
                            <div className="form-group">
                                <label>Last Name:</label>
                                <input
                                    type="text"
                                    value={lastName}
                                    onChange={(e) => setLastName(e.target.value)}
                                />
                            </div>
                            <div className="form-group">
                                <label>Phone Number:</label>
                                <input
                                    type="text"
                                    value={phoneNumber}
                                    onChange={(e) => setPhoneNumber(e.target.value)}
                                />
                            </div>
                            <button className='update-profile-button' type="submit">Update Profile</button>
                        </form>
                        <br />
                        <button className='delete-profile-button' type="button" onClick={handleDelete}>Delete Profile</button>
                    </div>
                </>
            )}
        </div>
    );
}

export default Profile;