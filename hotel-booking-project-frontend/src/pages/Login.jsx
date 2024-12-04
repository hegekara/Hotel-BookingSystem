import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api";
import "../styles/Auth.css"

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
      if (localStorage.getItem("jwtToken")) {
        navigate("/dashboard");
      }
    }, [navigate]);
  
    const handleLogin = async (e) => {
      e.preventDefault();
  
      const data = new URLSearchParams();
      data.append("email", email);
      data.append("password", password);
  
      try {
        const response = await API.post("/customer/login", data, {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
        });
  
        console.log("Response:", response.data);
  
        const token = response.data.token;
        if (token) {
          localStorage.setItem("jwtToken", token);
          navigate("/dashboard");
        }
      } catch (error) {
        console.error("Error:", error);
        setError("Login failed. Please check your email and password.");
      }
    };

  return (
    <div className="auth-container">
      <h2 className="auth-title">Login</h2>
      <form className="auth-form" onSubmit={handleLogin}>
        <div className="form-group">
          <label className="form-label">Email:</label>
          <input
            className="form-input"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label className="form-label">Password:</label>
          <input
            className="form-input"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button className="form-button" type="submit">
          Login
        </button>
      </form>
      {error && <p className="error-message">{error}</p>}
    </div>
  );
} 

export default Login;