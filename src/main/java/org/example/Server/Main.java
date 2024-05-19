package org.example.Server;
import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        SpotifyService spotifyService = new SpotifyService();

        //port(8080);
        get("/api/artists", (req, res) -> {
            res.type("application/json");
            return spotifyService.getTopArtists();
        });
        //port(8000);
        get("/api/albums", (req, res) -> {
            res.type("application/json");
            return spotifyService.getTopAlbums();
        });
    }
}




/*import org.example.UserManagment.UserService;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        // Sunucuya port 8080'de dinlemesini söyle
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/login", new LoginHandler());
        server.setExecutor(null); // Varsayılan executor kullan
        server.start();
        System.out.println("Sunucu 8080 portunda dinliyor...");
    }

    static class LoginHandler implements HttpHandler {
        private UserService userService = UserService.getInstance();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // İstek gövdesini al
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String[] params = body.split("&");
                String email = null;
                String password = null;
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if ("email".equals(keyValue[0])) {
                        email = keyValue[1];
                    } else if ("password".equals(keyValue[0])) {
                        password = keyValue[1];
                    }
                }

                // Kullanıcıyı doğrula
                boolean isAuthenticated = userService.login(email, password);
                String response;
                if (isAuthenticated) {
                    response = "Login successful";
                } else {
                    response = "Invalid email or password";
                }

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }
        }
    }
}*/