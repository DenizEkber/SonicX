import React from 'react';
import '../Css/Header.css';

function Header() {
    return (
      <header className="header">
        <h1>Spotify Clone</h1>
        <div className="auth-buttons">
          <button className="login-button">Log In</button>
          <button className="register-button">Sign Up</button>
        </div>
      </header>
    );
  }

export default Header;
