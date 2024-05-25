package org.example.UserManagment;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private final String host = "smtp.gmail.com";
    private final String port = "465";
    private final String username = "your_email"; // Gmail adresiniz
    private final String password = "your_password"; // Uygulama şifresi kullanmalısınız

    public void sendVerificationEmail(String recipientEmail, String verificationCode) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Try true for TLS or remove for SSL on port 465
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port); // Change to 465 if using SSL
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Consider adding additional properties like mail.smtp.ssl.enable for port 465

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password); // Use the generated App Password
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("E-posta Doğrulama Kodu");
            message.setText("Merhaba,\n\nE-posta doğrulama kodunuz: " + verificationCode);

            Transport.send(message);

            System.out.println("Doğrulama kodu e-posta olarak gönderildi ");
        } catch (MessagingException e) {
            System.out.println("E-posta gönderilirken hata oluştu: " + e.getMessage());
            // Consider adding e.printStackTrace() for detailed error information
        }
    }

}
