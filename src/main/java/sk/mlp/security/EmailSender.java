/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.security;


import sk.mlp.database.DatabaseServices;
import sk.mlp.logger.FileLogger;
import sk.mlp.ui.model.User;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Trieda EmailSender slúži na posielanie automaticky generovaných emailov používateľovi.
 * @author Matej Pazdič
 */
public class EmailSender {
    
    private String server = null;
    private String userName = null;
    private String password = null;
    private final String system = System.getProperty("os.name");

    /**
     * Konštruktor triedy EmailSender.
     * @param smtpHost - adresa servera odchádzjúch správ
     * @param userName - prihlasovacie meno na emailový server
     * @param password - heslo na emailový server
     */
    public EmailSender(String smtpHost, String userName, String password){
        this.server = smtpHost;
        this.userName = userName;
        this.password = password;
    }
    
    /**
     * Metóda sendUerAuthEmail slúži na generovanie a zaslanie potvrdzovacieho emailu, ktorý slúži na overenie zadaného emailu používateĺa, novo registrovanému používateľovi.
     * @param email - emailová adresa používateľa, ktorému bude zalaný email
     * @param userToken - jedinečný 32 znakový identifikátor registrácie používateľa
     * @param firstName - krstné meno používateľa
     * @param lastName - priezvisko používateľa
     */
    public void sendUserAuthEmail(String email, String userToken, String firstName, String lastName){
        String name = "NONE";
        String surname = "NONE";
        if(firstName != null){
            name = firstName;
        } if(lastName != null){
            surname = lastName;
        }
        
        Properties props = new Properties();
        props.put("mail.smtp.host", server);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("skuska.api.3@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Confirmation email from GPSWebApp server!!!");
            //message.setText(userToken);
            message.setSubject("Confirmation email from GPSWebApp server!!!");
            if(system.startsWith("Windows")){
                message.setContent("<html><head><meta charset=\"Windows-1250\"></head><body><h1>Hello " + name + " " + surname + ", please confirm your email by clicking on link ...</h1><a href=http://localhost:8080/GPSWebApp/TryToAcceptUser.jsp?token=" + userToken + "&email=" + email + ">LINK</a></body></html>","text/html");
            }else{
                message.setContent("<html><head><meta charset=\"Windows-1250\"></head><body><h1>Hello " + name + " " + surname + ", please confirm your email by clicking on link ...</h1><a href=http://gps.kpi.fei.tuke.sk/TryToAcceptUser.jsp?token=" + userToken + "&email=" + email + ">LINK</a></body></html>","text/html");
            }

            Transport.send(message);

            FileLogger.getInstance().createNewLog("Successfuly sent email to user " + email + ".");

        } catch (MessagingException e) {
            FileLogger.getInstance().createNewLog("ERROR: cannot sent email to user " + email + ".");
        }
        
    }
    
    /**
     * Metóda sendUserPasswordRecoveryEmail je určená na generovbanie a zaslanie používateľovi email s obnovením jeho zabudnutého hesla.
     * @param email - používateľský email
     */
    public void sendUserPasswordRecoveryEmail(String email){
        try {
            DatabaseServices databaseServices = new DatabaseServices();
            User user = databaseServices.findUserByEmail(email);
            String name = "NONE";
            String surname = "NONE";
            if (user.getUserFirstName()!=null) {
                name = user.getUserFirstName();
            }
            if (user.getUserLastName() != null) {
                surname = user.getUserLastName();
            }
            
            Properties props = new Properties();
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            });
            
            try {
                
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("skuska.api.3@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email));
                message.setSubject("Your password to GPSWebApp server!!!");
                //message.setText(userToken);
                message.setSubject("Your password to GPSWebApp server!!!");
                message.setContent("<html><head><meta charset=\"Windows-1250\"></head><body><h1>Hello " + name + " " + surname + ",</h1><br>your paassword to access GPSWebApp server is <b>" + user.getUserPass() + "</b>. <br>Please take note that you can change it in your settings. Have a pleasant day.</body></html>", "text/html");

                Transport.send(message);
                
                FileLogger.getInstance().createNewLog("Successfuly sent password recovery email to user " + email + ".");
                
            } catch (MessagingException e) {
                FileLogger.getInstance().createNewLog("ERROR: Cannot sent password recovery email to user " + email + ".");
            }
        } catch (Exception ex) {
            FileLogger.getInstance().createNewLog("ERROR: Cannot sent password recovery email to user " + email + ".");
        }
    }
    
    /**
     * Metóda getNewUserToken je pomocná metóda určená na generovanie jedinečného 32 znakového identifikátora registrácie používateľa, ktorý je určený na overenie správnosti zadaného emalu používateľom.
     * @return Návratová adresa je 32 znakový jedinečný identifikátor.
     */
    public String getNewUserToken(){
        return RandomStringUtils.randomAlphanumeric(32);
    }

}
