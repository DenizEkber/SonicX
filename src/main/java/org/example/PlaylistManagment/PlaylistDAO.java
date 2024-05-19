package org.example.PlaylistManagment;

import org.example.DataBase.DatabaseConnector;
import org.example.MusicManagment.MusicFile;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    private Connection connection;
    String path="Library"+ File.separator;
    public PlaylistDAO() {
        try {
            connection = DatabaseConnector.connect();
        } catch (SQLException e) {
            System.out.println("Veritabanı bağlantısı oluşturulurken hata oluştu: " + e.getMessage());
        }
    }

    public void addMusicFileToPlaylist(String playlistName, MusicFile musicFile) {
        String sql = "INSERT INTO playlist_music (playlist_name, title, artist, album, duration, path) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playlistName);
            statement.setString(2, musicFile.getTitle());
            statement.setString(3, musicFile.getArtist());
            statement.setString(4, musicFile.getAlbum());
            statement.setInt(5, musicFile.getDuration());
            statement.setString(6, path+musicFile.getTitle()+musicFile.getArtist()+musicFile.getAlbum()+".mp3");
            statement.executeUpdate();
            System.out.println("Müzik dosyası çalma listesine eklendi: " + musicFile.getTitle());
        } catch (SQLException e) {
            System.out.println("Müzik dosyası eklenirken hata oluştu: " + e.getMessage());
        }
    }

    public void removeMusicFileFromPlaylist(String playlistName, String musicTitle) {
        String sql = "DELETE FROM playlist_music WHERE playlist_name = ? AND title = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playlistName);
            statement.setString(2, musicTitle);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Müzik dosyası çalma listesinden silindi: " + musicTitle);
            } else {
                System.out.println("Belirtilen müzik dosyası çalma listesinde bulunamadı: " + musicTitle);
            }
        } catch (SQLException e) {
            System.out.println("Müzik dosyası silinirken hata oluştu: " + e.getMessage());
        }
    }

    public List<MusicFile> getMusicFilesInPlaylist(String playlistName) {
        List<MusicFile> musicFiles = new ArrayList<>();
        String sql = "SELECT title, artist, album, duration FROM playlist_music WHERE playlist_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playlistName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String artist = resultSet.getString("artist");
                String album = resultSet.getString("album");
                int duration = resultSet.getInt("duration");
                String file=resultSet.getString(path+title+artist+album+".mp3");
                MusicFile musicFile = new MusicFile(title, artist, album, duration,file);
                musicFiles.add(musicFile);
            }
        } catch (SQLException e) {
            System.out.println("Çalma listesi yenilenirken hata oluştu: " + e.getMessage());
        }
        return musicFiles;
    }

    public void close() {
        DatabaseConnector.disconnect(connection);
    }
}