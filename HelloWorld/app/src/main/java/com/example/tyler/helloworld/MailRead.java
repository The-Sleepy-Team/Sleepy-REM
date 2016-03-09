package com.example.tyler.helloworld;

/**
 * Created by jiyeon on 2/26/16.
 */
import java.util.*;
import javax.mail.*;
import javax.activation.*;

public class MailRead {
    private String _sent_date;
    private String _subject;
    private String _content;

    public MailRead() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", "sleepymrwindow@gmail.com", "123abc123ABC");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message msg = inbox.getMessage(inbox.getMessageCount());
            Address[] in = msg.getFrom();
            for (Address address : in) {
                System.out.println("FROM:" + address.toString());
            }

            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mc);

            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);

            //debugging purposes
            //System.out.println("SENT DATE:" + msg.getSentDate());
            //System.out.println("SUBJECT:" + msg.getSubject());
            //System.out.println("CONTENT:" + bp.getContent());

            _sent_date = msg.getSentDate().toString();
            _subject = msg.getSubject();
            _content = msg.getContent().toString();

        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

    //getters
    public String getContent() {return _content;}
    public String getSubject() {return _subject;}
    public String getSentDate() {return _sent_date;}

}
