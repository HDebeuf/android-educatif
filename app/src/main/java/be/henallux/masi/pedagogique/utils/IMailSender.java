package be.henallux.masi.pedagogique.utils;

import android.content.Context;

/**
 * Created by hendrikdebeuf2 on 7/01/18.
 */

public interface IMailSender {

    void sendMailFile(String senderName, String subject, String body, String fileName);
    void sendMail(String senderName, String subject, String body);
}
