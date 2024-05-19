package org.example.Server;


import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class SpotifyService {

    private static final String CLIENT_ID = "dce6368c43554a689faa8591974eb880";
    private static final String CLIENT_SECRET = "34bfcfc42e3142e09047b6f83bb0832b";
    private String accessToken;

    public SpotifyService() {
        this.accessToken = fetchAccessToken();
    }

    private String fetchAccessToken() {
        try {
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            conn.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String requestBody = "grant_type=client_credentials";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Parse access token from response using JSON parsing
            JSONObject jsonResponse = new JSONObject(response.toString());
            String accessToken = jsonResponse.getString("access_token");
            return accessToken;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTopArtists() {
        return fetchData("https://api.spotify.com/v1/me/top/artists");
    }

    public String getTopAlbums() {
        return fetchData("https://api.spotify.com/v1/me/top/albums");
    }

    private String fetchData(String endpoint) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
