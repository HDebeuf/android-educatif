package be.henallux.masi.pedagogique.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

/**
 * Created by hendrikdebeuf2 on 7/01/18.
 */

public class MailSender implements IMailSender {

    private Context context;
    private String userName;
    private String password;
    private String senderName;

    public MailSender(Context context) {
        this.context = context;
    }

    public void sendMail(String senderName, String subject, String body, String fileName){
        BackgroundMail.newBuilder(context)
                .withUsername("applicationpedagogique@gmail.com")
                .withPassword("application pedagogique")
                .withSenderName(senderName)
                .withMailTo("applicationpedagogique@gmail.com")
                .withType(BackgroundMail.TYPE_HTML)
                .withSubject(subject)
                .withBody(body)
                .withAttachments(Environment.getExternalStorageDirectory().getPath() + "/" + fileName)
                .withProcessVisibility(false)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context,"Vos résultats ont été envoyés à votre professeur",Toast.LENGTH_LONG);
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(context,"Une erreur est survenue",Toast.LENGTH_LONG);
                    }
                })
                .send();
    }

}
