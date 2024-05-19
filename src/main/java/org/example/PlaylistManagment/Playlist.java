package org.example.PlaylistManagment;

import org.example.MusicManagment.MusicFile;

import java.util.List;

public class Playlist {
    private String name;
    private PlaylistDAO playlistDAO;

    public Playlist(String name) {
        this.name = name;
        this.playlistDAO = new PlaylistDAO();
    }

    public String getName() {
        return name;
    }

    public void addMusicFileToPlaylist(MusicFile musicFile) {
        playlistDAO.addMusicFileToPlaylist(this.name, musicFile);
    }

    public void removeMusicFileFromPlaylist(String musicTitle) {
        playlistDAO.removeMusicFileFromPlaylist(this.name, musicTitle);
    }

    public List<MusicFile> getAllMusicFiles() {
        return playlistDAO.getMusicFilesInPlaylist(this.name);
    }

    public void close() {
        playlistDAO.close();
    }

}
