import React, { useState, useEffect } from "react";
import { useLocation, useNavigate, Link } from "react-router-dom";
import Room from "../components/Room";
import Header from "../components/Header";
import API from '../api';

function AdminListRoom() {
    const navigate = useNavigate();
    const [role, setRole] = useState(localStorage.getItem("role") || " ");
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [rooms, setRooms] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");
        const role = localStorage.getItem("role").toLowerCase();
        setIsLoggedIn(!!token);
        setRole(role)

        if (!token) {
            navigate("/");
        } else {
            fetchRooms();
        }
    }, [navigate]);

    const fetchRooms = async () => {
        try {
            const response = await API.get("/room/list");

            setRooms(response.data);
        } catch (error) {
            console.error("Odalar getirilemedi:", error);
        }
    };
    console.log(isLoggedIn && (role === "admin" || role === "manager" || role === "personel"));

    return (
        <div>
            {isLoggedIn && (role === "admin" || role === "manager" || role === "personel") && (
                <>
                    <Header isLoggedIn={isLoggedIn} role={role} />
                    <div style={{ padding: "4rem", paddingTop: "4rem" }}>
                        <div style={{ display: "flex", "justifyContent": "space-between" }}>
                            <h2>Rooms</h2>
                            <Link to="/create-room" className="home-button">Create Room</Link>
                        </div>
                        <Room
                            rooms={rooms}
                            checkInDate={""}
                            checkOutDate={""}
                        />
                    </div>
                </>
            )}
        </div>
    );
}

export default AdminListRoom;