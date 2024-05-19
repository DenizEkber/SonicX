import React from 'react';
import "../Css/Card.css";

function ArtistCard({ artist }) {
  return (
    <div className="artist-card">
      <img src={artist.image} alt={artist.name} />
      <h3>{artist.name}</h3>
    </div>
  );
}

export default ArtistCard;
