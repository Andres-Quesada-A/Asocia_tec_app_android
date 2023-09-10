package com.techsphere.asociaplan.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ConfirmationEmail {
    public void sendEmail(String receiverEmail, String subject, String messageContent, String imageFilePath, String pdfFilePath) {
        try {
            String host = "smtp.gmail.com";
            String stringSenderEmail = "asociaplan@gmail.com";
            String stringPasswordSenderEmail = "unhypuehnrxjbsfe";

            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            mimeMessage.setSubject(subject);
//            mimeMessage.setText(messageContent);

            // Adjuntar imagen al mensaje
            MimeBodyPart imageBodyPart = new MimeBodyPart();
            DataSource imageSource = new FileDataSource(imageFilePath);
            imageBodyPart.setDataHandler(new DataHandler(imageSource));
            imageBodyPart.setFileName(imageSource.getName());

            // Adjuntar PDF al mensaje
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            DataSource pdfSource = new FileDataSource(pdfFilePath);
            pdfBodyPart.setDataHandler(new DataHandler(pdfSource));
            pdfBodyPart.setFileName(pdfSource.getName());

            // Crear el Multipart y agregar las partes
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(imageBodyPart);
            multipart.addBodyPart(pdfBodyPart);

            // Establecer el contenido del mensaje
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageContent);
            multipart.addBodyPart(messageBodyPart);

            mimeMessage.setContent(multipart);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
