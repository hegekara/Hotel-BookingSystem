import React from 'react';
import '../styles/Personel.css';
import API from "../api";

function Personel({ personels }) {
  const handleFire = (id) => {
    if (window.confirm('Are you sure you want to fire this employee?')) {
      API.delete(`/personel/delete/${id}`)
        .then((response) => {
          alert(response.data);
          window.location.reload();
        })
        .catch((error) => {
          console.error('Failed to fire personnel:', error);
          alert('An error occurred while trying to fire the employee.');
        });
    }
  };

  return (
    <div className="personel-list">
      <h2 className="personel-title">Personnel List</h2>
      <table className="personel-table">
        <thead>
          <tr>
            <th className="personel-header">Name</th>
            <th className="personel-header">Email</th>
            <th className="personel-header">Role</th>
            <th className="personel-header">Actions</th>
          </tr>
        </thead>
        <tbody>
          {personels.map((personel) => (
            <tr key={personel.email} className="personel-row">
              <td className="personel-data">{personel.firstName} {personel.lastName}</td>
              <td className="personel-data">{personel.email}</td>
              <td className="personel-data">{personel.role}</td>
              <td className="personel-actions">
                <button
                  className="fire-button"
                  onClick={() => handleFire(personel.id)}
                >
                  Fire
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Personel;