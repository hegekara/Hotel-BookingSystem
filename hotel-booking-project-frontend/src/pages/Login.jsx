import React, { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import API from "../api";
import "../styles/Auth.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("jwtToken")) {
      navigate("/home");
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
      const email = response.data.object.email;
      const role = (response.data.object.role).toLowerCase();

      if (token) {
        localStorage.setItem("jwtToken", token);
        localStorage.setItem("email", email);
        localStorage.setItem("role", role);
        navigate("/home");
      }
    } catch (error) {
      console.error("Error:", error);
      setError("Login failed. Please check your email and password.");
    }
  };

  return (
    <div className="homepage-img">
      <img src="../src/static/images/homepage-img.jpeg" alt="Hotel Image" />
      <div className="content">
        <form className="auth-form" onSubmit={handleLogin}>
          <h2 className="auth-title">Login</h2>
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
          <div className="link-container">
            <Link className="form-link" to="/register">Go to Register Page</Link>
          </div>
        </form>
        {error && <p className="error-message">{error}</p>}
      </div>
    </div>
  );
};

export default Login;