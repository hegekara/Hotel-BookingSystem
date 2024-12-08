import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import API from "../api";
import "../styles/AddPersonel.css";
import Header from "../components/Header";

const AddPersonel = () => {
  const [isLoggedIn, setLoggedIn] = useState(false);
  const [localRole, setLocalRole] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [password2, setPassword2] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [role, setRole] = useState("");
  const [roles, setRoles] = useState([]);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    API.get("/personel/roles")
      .then((response) => {
        setRoles(response.data);
      })
      .catch((err) => {
        console.error("Failed to fetch roles:", err);
        setError("Failed to load roles.");
      });

    const token = localStorage.getItem("jwtToken");
    const role = localStorage.getItem("role");
    setLoggedIn(!!token);
    setLocalRole(role);
  }, []);

  const handleRegister = async (e) => {
    e.preventDefault();

    if (password !== password2) {
      setError("Passwords do not match. Please try again.");
      return;
    }

    try {
      const payload = { firstName, lastName, email, phoneNumber, password, role };
      await API.post("/personel/register", payload);
      setSuccess(true);
      navigate("/list-personel");
    } catch (err) {
      console.error("Registration failed:", err);
      setError("Registration failed. Please try again.");
    }
  };

  return (
    <>
      {isLoggedIn && localRole === "admin" && (
        <div>
            <Header isLoggedIn={isLoggedIn} /><br />
            <div className="add-personel-page">
            <br />
            <div className="add-personel-container">
                <h2 className="form-title">Add Personnel</h2>
                <form className="personnel-form" onSubmit={handleRegister}>
                <input
                    type="text"
                    className="form-input"
                    placeholder="First Name"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    required
                />
                <input
                    type="text"
                    className="form-input"
                    placeholder="Last Name"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    required
                />
                <input
                    type="email"
                    className="form-input"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    type="text"
                    className="form-input"
                    placeholder="Phone Number"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                    required
                />
                <select
                    className="form-select"
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    required
                >
                    <option value="" disabled>
                    Select Role
                    </option>
                    {roles.map((r) => (
                    <option key={r} value={r}>
                        {r}
                    </option>
                    ))}
                </select>
                <input
                    type="password"
                    className="form-input"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <input
                    type="password"
                    className="form-input"
                    placeholder="Confirm Password"
                    value={password2}
                    onChange={(e) => setPassword2(e.target.value)}
                    required
                />
                <button type="submit" className="form-button">
                    Add Personnel
                </button>
                </form>
                {success && (
                <p className="form-success-message">Personnel added successfully! Redirecting...</p>
                )}
                {error && <p className="form-error-message">{error}</p>}
                <Link to="/list-personel" className="form-back-link">
                Back to Personnel List
                </Link>
            </div>
            </div>

        </div>
        
      )}
    </>
  );
};

export default AddPersonel;
