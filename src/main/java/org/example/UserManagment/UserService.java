package org.example.UserManagment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserService {
    private static UserService instance;
    private User currentUser;
    private UserDAO userDAO;
    private EmailSender emailSender;

    private Map<String, User> pendingUsers;

    private Map<String, String> verificationCodes;

    private UserService() {
        userDAO = new UserDAO();
        emailSender = new EmailSender();
        verificationCodes = new HashMap<>();
        pendingUsers = new HashMap<>();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean login(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null ) {
            String hashedPassword = PasswordUtils.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                currentUser = user;
                System.out.println("Giriş başarılı. Hoş geldiniz, " + currentUser.getFirstName() + " " + currentUser.getLastName());
                return true;
            }
        }
        System.out.println("Hatalı e-posta veya şifre. Lütfen tekrar deneyin.");
        return false;
    }

    public void register(String firstName, String lastName, String email, String password) {
        if (userDAO.getUserByEmail(email) != null) {
            System.out.println("Bu e-posta adresi zaten kullanımda.");
            return ;
        }

        // E-posta doğrulama kodu oluştur
        String verificationCode = generateVerificationCode();

        verificationCodes.put(email, verificationCode);
        pendingUsers.put(email, new User(0, firstName, lastName, email, password));

        emailSender.sendVerificationEmail(email, verificationCode);
        System.out.println("Doğrulama kodu gönderildi. Lütfen e-posta adresinizi doğrulayın.");
    }

    public boolean verifyEmail(String email, String code) {
        String correctCode = verificationCodes.get(email);
        if (correctCode != null && correctCode.equals(code)) {
            // Kullanıcıyı veritabanına kaydet
            User newUser = pendingUsers.get(email);
            boolean success = userDAO.addUser(newUser);
            if (success) {
                System.out.println("E-posta doğrulaması başarılı. Hesap aktifleştirildi.");
                verificationCodes.remove(email); // Kod doğrulandığı için kaldır
                pendingUsers.remove(email); // Geçici kullanıcıyı kaldır
                return true;
            } else {
                System.out.println("Hesap oluşturulurken bir hata oluştu. Lütfen tekrar deneyin.");
                return false;
            }
        } else {
            System.out.println("Geçersiz doğrulama kodu. Lütfen doğru kodu girin.");
            return false;
        }
    }

    public void logout() {
        currentUser = null;
        System.out.println("Çıkış yapıldı. Lütfen tekrar giriş yapın.");
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // Rastgele 4 haneli doğrulama kodu
        return String.valueOf(code);
    }
}