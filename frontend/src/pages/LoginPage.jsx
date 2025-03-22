import React, { useState } from 'react';
import { Link } from 'react-router-dom';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    // Simple validation
    if (!username || !password) {
      setError('Username and Password are required');
      return;
    }

    // Perform login logic here (e.g., authentication, API call)
    console.log('Login:', { username, password });

    // Clear the form
    setUsername('');
    setPassword('');
    setError('');
  };

  return (
    <section className="section">
      <div className="container">
        <h1 className="title">Login</h1>
        <form onSubmit={handleSubmit}>
          <div className="field">
            <label className="label">Username</label>
            <div className="control">
              <input
                className="input"
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>
          </div>

          <div className="field">
            <label className="label">Password</label>
            <div className="control">
              <input
                className="input"
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
          </div>

          {error && <div className="notification is-danger">{error}</div>}

          <div className="field">
            <div className="control">
              <button type="submit" className="button is-primary">
                Login
              </button>
            </div>
          </div>
        </form>

        <div className="has-text-centered mt-4">
          <p>
            Don't have an account? <Link to="/register">Register here</Link>
          </p>
        </div>
      </div>
    </section>
  );
}

export default LoginPage;
