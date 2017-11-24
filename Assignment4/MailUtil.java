/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailapp;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Abhijit
 */
public class MailUtil {
    
    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private Properties props;
    private Session session;
    private String sender;
    private String receiver;
    private String password;
    
    public MailUtil()
    {
        props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
    }
    
    public void authenticateSender(String username,String password)
    {
        session = Session.getDefaultInstance(props,new Authenticator(){
                             protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                             }});
        this.sender=username;
        this.password=password;
    }
    
    public void sendMail(String receiver,String subject,String text) throws Exception
    {
        this.receiver=receiver;
        Message msg = new MimeMessage(session);
        
        msg.setFrom(new InternetAddress(sender));
        msg.setRecipients(Message.RecipientType.TO, 
                      InternetAddress.parse(receiver,false));
        msg.setSubject(subject);
        msg.setText(text);
        msg.setSentDate(new Date());
        Transport.send(msg);
        System.out.println("Message sent.");
    }
    
}
