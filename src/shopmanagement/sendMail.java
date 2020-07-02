package shopmanagement;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Fahim
 */
public class sendMail {

    public static void Mail(String from, String to, String pass, String subject, String messageString) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

//        SMTPAuthenticator auth = new SMTPAuthenticator();
//        Session session = Session.getInstance(properties, auth);
//        session.setDebug(true);
//
//        MimeMessage msg = new MimeMessage(session);
//        try {
//            msg.setSubject(subject);
//            msg.setFrom(new InternetAddress(from));
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            Transport transport = session.getTransport("smtps");
//            transport.connect("smtp.gmail.com", Integer.valueOf("465"), "Fahim", pass);
//            transport.sendMessage(msg, msg.getAllRecipients());
//            transport.close();
//
//        } catch (AddressException e) {
//            e.printStackTrace();
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, pass);
                    }

                });

        try {
            System.out.println("1");
            MimeMessage message = new MimeMessage(session);
            System.out.println("mimeMsg ok");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(messageString);
            System.out.println("sending");
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", from, pass);

            Transport.send(message);
            transport.close();
            System.out.println("done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
