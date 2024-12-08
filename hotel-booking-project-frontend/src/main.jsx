import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./App";
import "./index.css"
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home"
import NoPage from "./pages/NoPage";
import RoomFilter from "./pages/RoomFilter";
import ListRoom from "./pages/ListRoom";
import RoomDetail from "./pages/RoomDetail";
import ListBooking from "./pages/ListBooking";
import Profile from "./pages/Profile";
import PasswordSettings from "./pages/PasswordSettings";
import PersonelLogin from "./admin-pages/PersonelLogin";
import AdminPanel from "./admin-pages/AdminPanel";
import AdminListRoom from "./admin-pages/AdminListRoom";

ReactDOM.createRoot(document.getElementById("root")).render(
  <>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} /> 
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} />
        <Route path="/room-filter" element={<RoomFilter />} />
        <Route path="/list-room" element={<ListRoom />} />
        <Route path="/rooms" element={<RoomDetail />} />
        <Route path="/list-booking" element={<ListBooking />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/password-settings" element={<PasswordSettings />} />

        <Route path="/personel-login" element={<PersonelLogin />} />
        <Route path="/admin-panel" element={<AdminPanel />} />

        <Route path="/*" element={<NoPage/>}/>
      </Routes>
    </BrowserRouter>
  </>
);