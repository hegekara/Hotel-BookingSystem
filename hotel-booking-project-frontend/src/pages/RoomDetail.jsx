import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "../styles/RoomDetail.css";
import SuiteImg from "../static/images/suit-room.png";
import StandartImg from "../static/images/standart-room.jpeg";
import DeluxeImg from "../static/images/deluxe-room.jpeg";
import Header from "../components/Header";

function RoomDetail() {
  const location = useLocation();
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [selectedImage, setSelectedImage] = useState(null);
  const [role] = useState(localStorage.getItem("role"));

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    setIsLoggedIn(!!token);

    if((role=="admin")|| (role=="manager")|| (role=="personel")){
      navigate("/admin-panel")
    }

    if (!token) {
      navigate("/");
    }
  }, [navigate]);

  const rooms = [
    {
      name: "Standard",
      image: StandartImg,
      description: "Rahat bir konaklama için tüm temel olanaklara sahip konforlu standart oda.",
    },
    {
      name: "Suite",
      image: SuiteImg,
      description: "Lüks süitimizde kral boy yatak, jakuzi ve nefes kesen manzaralar mevcuttur.",
    },
    {
      name: "Deluxe",
      image: DeluxeImg,
      description: "Birinci sınıf mobilyalar ve geniş iç mekanlara sahip 1+1 odamızda zarafetin tadını çıkarın.",
    },
  ];

  return (
    <div>
      {isLoggedIn ? (
        <>
          <Header isLoggedIn={isLoggedIn} />
          <div className="room-detail-container">
            <h2 className="room-detail-title">Our Rooms</h2>
            <div className="image-display-container">
              {selectedImage ? (
                <img src={selectedImage} alt="Selected Room" className="selected-image" />
              ) : (
                <p className="placeholder-text">Click on a room to view its image.</p>
              )}
            </div>
            <div className="room-list">
              {rooms.map((room, index) => (
                <div
                  key={index}
                  className="room-card"
                  onClick={() => setSelectedImage(room.image)}
                >
                  <h3 className="room-name">{room.name}</h3>
                  <p className="room-description">{room.description}</p>
                </div>
              ))}
            </div>
          </div>
        </>
      ) : (
        <p>Redirecting...</p>
      )}
    </div>
  );
}

export default RoomDetail;
