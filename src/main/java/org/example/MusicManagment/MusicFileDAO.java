package org.example.MusicManagment;

import org.example.DataBase.DatabaseConnector;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicFileDAO {

    private Connection connection;
    String path="Library"+File.separator;
    public MusicFileDAO() {
        try {
            connection = DatabaseConnector.connect();
        } catch (SQLException e) {
            System.out.println("Veritabanı bağlantısı oluşturulurken hata oluştu: " + e.getMessage());
        }
    }

    public void addMusicFile(MusicFile musicFile) {
        String sql = "INSERT INTO music_files (title, artist, album, duration, path) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, musicFile.getTitle());
            statement.setString(2, musicFile.getArtist());
            statement.setString(3, musicFile.getAlbum());
            statement.setInt(4, musicFile.getDuration());
            statement.setString(5, path+musicFile.getTitle()+musicFile.getArtist()+musicFile.getAlbum()+".mp3");
            statement.executeUpdate();
            System.out.println("Müzik dosyası eklendi: " + musicFile.getTitle());
        } catch (SQLException e) {
            System.out.println("Müzik dosyası eklenirken hata oluştu: " + e.getMessage());
        }
    }

    public List<MusicFile> getAllMusicFiles() {
        List<MusicFile> musicFiles = new ArrayList<>();
        String sql = "SELECT title, artist, album, duration, path FROM music_files";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String artist = resultSet.getString("artist");
                String album = resultSet.getString("album");
                int duration = resultSet.getInt("duration");
                String file=resultSet.getString("path");
                MusicFile musicFile = new MusicFile(title, artist, album, duration, file);
                musicFiles.add(musicFile);
            }
        } catch (SQLException e) {
            System.out.println("Müzik dosyaları getirilirken hata oluştu: " + e.getMessage());
        }
        return musicFiles;
    }

    public void close() {
        DatabaseConnector.disconnect(connection);
    }
}