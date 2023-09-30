package com.kasra.javaee.controller;

import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import org.apache.commons.lang.StringEscapeUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by kasra.haghpanah on 01/12/2016.
 */
@Named
@SessionScoped
@Controller(url = "mail")
public class MailController implements Serializable {

//    @Resource(name = "mail/gmail")
//    private Session session;

    // create-javamail-resource --mailhost="smtp.gmail.com" --mailuser="kasrakhpk1985@gmail.com" --fromaddress="kasrakhpk1985@gmail.com" --debug="true" --enabled="true" --description="A new JavaMail Session!" --property="mail.smtp.password=REZA8414443KASRAh:mail.smtp.auth=true:mail.smtp.port=587:mail.smtp.socketFactory.fallback=false:mail.smtp.socketFactory.port=587:mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory" "mail/gmail"


    @Resource(name = "properties/example")
    private Properties properties;
    //create-custom-resource --restype=java.util.properties --factoryclass org.glassfish.resources.custom.factory.PropertiesFactory --property prop1=test1:prop2=test2 properties/example


    @PostConstruct
    public void init() {
        System.out.println("Start MailController!");
    }

    @WebRequest(url = "send", method = Method.PUT)
    public void send(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws UnsupportedEncodingException {

        String message = StringEscapeUtils.unescapeJavaScript(request.getParameter("message"));
        String email = StringEscapeUtils.unescapeJavaScript(request.getParameter("email"));
        String subject = StringEscapeUtils.unescapeJavaScript(request.getParameter("subject"));
        String filename = StringEscapeUtils.unescapeJavaScript(request.getParameter("filename"));


        subject = MimeUtility.encodeText(subject, "utf-8", "B");
        filename = MimeUtility.encodeText(filename, "utf-8", "B");


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");


        String fromEmail = "kasrakhpk1985@gmail.com"; // "java.ee.tutoria.mail@gmail.com"
        String password = "REZA8414443KASRAh"; //

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);//password accordingly
                    }
                });


        Message msg = new MimeMessage(session);
        //msg.setFrom(new InternetAddress(session.getProperty("mail.from")));
        try {
            msg.setSubject(subject);
            //msg.setText(message);
            InternetAddress[] address = new InternetAddress[1];
            address[0] = new InternetAddress(email);
            msg.setRecipients(Message.RecipientType.TO, address);//InternetAddress.parse(email)
            msg.setSentDate(new Date());


            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();

            String html = "<h1 style=\"color: #ff6666\">" + message + "</h1>";
            BodyPart bodyHtml = new MimeBodyPart();
            bodyHtml.setContent(html, "text/html; charset=utf-8");
            multipart.addBodyPart(bodyHtml);

            String file = servlet.getContextRoot() + "/src/main/webapp/upload/kasra.jpg"; //"path of file to be attached";
            String fileName = filename + ".jpg";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart, "text/html; charset=utf-8");

            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        String value = properties.getProperty("prop1");
        servlet.print(response, "{\"data\":\"Send Email!\",\"property\":\"" + value + "\"}", "application/json", true);

    }


    @Schedule(second = "*/5" , minute = "*" , hour = "*")
    public void referesh() throws NamingException {
        Context context = new InitialContext();
        properties = (Properties)context.lookup("properties/example");
    }
}
