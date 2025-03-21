import React from 'react';
import { Link } from 'react-router-dom';

function Homepage() {
  return (
    <section className="hero is-fullheight">
      <div className="hero-body">
        <div className="container has-text-centered">
          <div className="box">
            <h1 className="title is-2">Welcome to Baker's Math</h1>
            <p className="subtitle is-4">The tool designed to simplify bakery calculations and optimize your production process.</p>
            <Link to="/login">
                <button className="button is-primary is-large mt-5">Get Started</button>
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Homepage;
