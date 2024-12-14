import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Header from "../components/Header";
import API from "../api";
import "../styles/RoomCreate.css";

function EditRoom() {
    const navigate = useNavigate();
    const { roomNumber } = useParams();
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [role, setRole] = useState(localStorage.getItem("role") || "");

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
        const role = localStorage.getItem("role")?.toLowerCase();
        setIsLoggedIn(!!token);
        setRole(role);

        if (!token) {
            navigate("/");
            return;
        }

        if (roomNumber) {
            fetchRoomData();
        }

        fetchTypes();
    }, [navigate, roomNumber]);

    const fetchRoomData = async () => {
        try {
            const response = await API.get(`/room/${roomNumber}`);
            const room = response.data;
            setRoomType(room.roomType || "");
            setCapacity(room.capacity || "");
            setBedType(room.bedType || "");
            setPricePerNight(room.pricePerNight || "");
            setIsAvailable(room.isAvailable);
            setHasView(room.hasView);
        } catch (error) {
            console.error("Error fetching room data:", error);
            alert("Failed to fetch room data.");
        }
    };

    const fetchTypes = async () => {
        try {
            const roomTypeResponse = await API.get("/room/room-types");
            setRoomTypes(roomTypeResponse.data);

            const bedTypeResponse = await API.get("/room/bed-types");
            setBedTypes(bedTypeResponse.data);
        } catch (error) {
            console.error("Error fetching room/bed types:", error);
        }
    };

    const handleUpdateRoom = async (e) => {
        e.preventDefault();
        const room = {
            roomNumber,
            roomType,
            capacity: parseInt(capacity) || 0,
            bedType,
            pricePerNight: parseFloat(pricePerNight) || 0.0,
            isAvailable,
            hasView,
        };

        try {
            await API.put(`/room/${roomNumber}`, room);
            alert("Room updated successfully!");
            navigate("/admin-list-room");
        } catch (error) {
            console.error("Error updating room:", error);
            alert("Failed to update room. Please try again.");
        }
    };

    const handleDeleteRoom = async () => {
        if (window.confirm("Are you sure you want to delete this room?")) {
            try {
                await API.delete(`/room/${roomNumber}`);
                alert("Deletion successful!");
                navigate("/admin-list-room");
            } catch (error) {
                console.error("Error:", error);
                alert("Failed to delete room. Please try again.");
            }
        }
    };

    return (
        <div>
            {isLoggedIn && (role === "admin" || role === "manager" || role === "personel") && (
                <>
                    <Header isLoggedIn={isLoggedIn} role={role} />
                    <br /><br />
                    <div className="room-form-container">
                        <h2>Edit Room</h2>
                        <form className="room-form" onSubmit={handleUpdateRoom}>
                            <div className="form-group">
                                <label>Room Number:</label>
                                <input type="text" value={roomNumber} disabled />
                            </div>
                            <div className="form-group">
                                <label>Room Type:</label>
                                <select
                                    value={roomType}
                                    onChange={(e) => setRoomType(e.target.value)}
                                    disabled
                                >
                                    <option value="">Select Room Type</option>
                                    {roomTypes.map((type, index) => (
                                        <option key={index} value={type}>
                                            {type}
                                        </option>
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
                                        <option key={index} value={type}>
                                            {type}
                                        </option>
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
                                <label>Available:</label>
                                <input
                                    type="checkbox"
                                    checked={isAvailable}
                                    onChange={(e) => setIsAvailable(e.target.checked)}
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
                            <button type="submit">Update Room</button>
                            <button type="button" onClick={handleDeleteRoom}>Delete Room</button>
                        </form>
                    </div>
                </>
            )}
        </div>
    );
}

export default EditRoom;
