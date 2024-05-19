import React, { useEffect, useState } from 'react';
import Header from '../components/Header';
import Sidebar from '../components/Sidebar';
import MainContent from '../components/MainContent';
import ArtistCard from '../components/ArtistCard';
import AlbumCard from '../components/AlbumCard';
import '../Css/Home.css';

function Home() {
  const [artists, setArtists] = useState([]);
  const [albums, setAlbums] = useState([]);

  useEffect(() => {
    fetch('/api/artists')
      .then(response => response.json())
      .then(data => setArtists(data.items || []));

    fetch('/api/albums')
      .then(response => response.json())
      .then(data => setAlbums(data.items || []));
  }, []);

  return (
    <div className="home">
      <Header />
      <MainContent>
        <Sidebar />
        <div className="content">
          <h2>Popular Artists</h2>
          <div className="artists">
            {artists.map((artist, index) => (
              <ArtistCard key={index} artist={{ name: artist.name, image: artist.images[0]?.url }} />
            ))}
          </div>
          <h2>Popular Albums</h2>
          <div className="albums">
            {albums.map((album, index) => (
              <AlbumCard key={index} album={{ title: album.name, image: album.images[0]?.url }} />
            ))}
          </div>
        </div>
      </MainContent>
    </div>
  );
}

export default Home;
