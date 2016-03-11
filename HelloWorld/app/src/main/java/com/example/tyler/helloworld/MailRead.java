package com.example.tyler.helloworld;

/**
 * Created by jiyeon on 2/26/16.
 */
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import javax.mail.*;
import javax.activation.*;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

public class MailRead extends AsyncTask<String, Void, String> {

    private Exception exception;

    @Override
    protected String doInBackground(String... emailSubject) {
        try {
            System.out.println(emailSubject);
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getInstance(props, null);
            final Store store = session.getStore();
            store.connect("imap.gmail.com", "sleepymrwindow@gmail.com", "123abc123ABC");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message msg = null;
            for (int i = inbox.getMessageCount(); i > 0; i--)
            {
                msg = inbox.getMessage(i);
                System.out.println(msg.getSubject());
                if (msg.getSubject().equals(emailSubject[0])) break;
            }

            /*
            Address[] in = msg.getFrom();
            for (Address address : in) {
                System.out.println("FROM:" + address.toString());
            }
            */


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

            return (bp.getContent().toString());
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String content) {
        // TODO: check this.exception
        // TODO: do something with the feed
        try {


            //_sent_date1 = msg.getSentDate();
            //_subject = msg.getSubject();
            //_content = bp.getContent().toString();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
/*{

    private String _content;
    private String _subject;

    public MailRead() {

        _content = "";
        _subject = "";

        try {
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {

                    try {
                        //Your code goes here
                        Properties props = new Properties();
                        props.setProperty("mail.store.protocol", "imaps");
                        Session session = Session.getInstance(props, null);
                        final Store store = session.getStore();
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

                        //_sent_date1 = msg.getSentDate();
                        _subject = msg.getSubject();
                        _content = bp.getContent().toString();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

    //getters

    //from email1 (latest email)
    //public Message getMsg() {return msg;}
    public String getCont() {return _content;}
    public String getSubject() {return _subject;}
    //public Date getSentDate1() {return _sent_date1;}

    /*
    //from email2 (second latest email)
    public String getContent2() {return _content2;}
    public String getSubject2() {return _subject2;}
    public String getSentDate2() {return _sent_date2;}
    */
//}
