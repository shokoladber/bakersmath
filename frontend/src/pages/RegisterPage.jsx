import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function RegisterPage() {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: ''
  });

  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    setSuccessMessage('');

    if (formData.password !== formData.confirmPassword) {
      setErrors({ confirmPassword: "Passwords do not match" });
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username: formData.username,
          email: formData.email,
          password: formData.password
        }),
      });

      const data = await response.json();

      if (!response.ok) {
        setErrors(data); // Display validation errors from backend
      } else {
        setSuccessMessage('Registration successful! Redirecting to login...');
        setTimeout(() => navigate('/login'), 2000);
      }
    } catch (error) {
      setErrors({ server: 'An error occurred. Please try again.' });
    }
  };

  return (
    <section className="section">
      <div className="container">
        <h1 className="title">Register</h1>
        <form onSubmit={handleSubmit}>
          <div className="field">
            <label className="label">Username</label>
            <div className="control">
              <input
                className="input"
                type="text"
                name="username"
                placeholder="Enter username"
                value={formData.username}
                onChange={handleChange}
                required
              />
            </div>
            {errors.username && <p className="help is-danger">{errors.username}</p>}
          </div>

          <div className="field">
            <label className="label">Email</label>
            <div className="control">
              <input
                className="input"
                type="email"
                name="email"
                placeholder="Enter email"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>
            {errors.email && <p className="help is-danger">{errors.email}</p>}
          </div>

          <div className="field">
            <label className="label">Password</label>
            <div className="control">
              <input
                className="input"
                type="password"
                name="password"
                placeholder="Enter password"
                value={formData.password}
                onChange={handleChange}
                required
              />
            </div>
          </div>

          <div className="field">
            <label className="label">Confirm Password</label>
            <div className="control">
              <input
                className="input"
                type="password"
                name="confirmPassword"
                placeholder="Confirm password"
                value={formData.confirmPassword}
                onChange={handleChange}
                required
              />
            </div>
            {errors.confirmPassword && <p className="help is-danger">{errors.confirmPassword}</p>}
          </div>

          {errors.server && <p className="notification is-danger">{errors.server}</p>}
          {successMessage && <p className="notification is-success">{successMessage}</p>}

          <div className="field">
            <div className="control">
              <button type="submit" className="button is-primary">
                Register
              </button>
            </div>
          </div>
        </form>

        <p className="mt-3">
          Already have an account? <a href="/login">Login here</a>
        </p>
      </div>
    </section>
  );
}

export default RegisterPage;
