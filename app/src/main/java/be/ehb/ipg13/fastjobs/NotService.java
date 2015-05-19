package be.ehb.ipg13.fastjobs;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nadir on 19/04/2015.
 */
public class NotService extends IntentService {
    private static int teller = 0;
    private static final String TAG = "com.example.nadir.my_not";
    private ArrayList<String> IDs = new ArrayList<String>();
    private ArrayList<ModelTest> lijst = null;
    private String abc = "";

    public NotService() {
        super("NotService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences idsOphalen = getSharedPreferences("ids", Context.MODE_PRIVATE);
        int count = idsOphalen.getInt("count", -1);
        for (int i = 0; i < count; i++) {
            IDs.add(idsOphalen.getString("id " + i, "id " + i + " niet gevonden!"));
        }
        ProfielSQL profielSQL = new ProfielSQL(this);
        profielSQL.open();
        ArrayList<Profiel_model> profielen = profielSQL.getAll();
        profielSQL.close();

        for (int kk = 0; kk < profielen.size();kk++) {

            String xml = ProfielXML.gegevensOphalen(this, false,profielen.get(kk).getNaam());
            System.out.println("XML FILE 2 ==->>" + xml);
             System.out.println("DE PROFIELNAAM IS == " + profielen.get(kk).getNaam());
            //Hier zal je de XML sturen naar de APIHelper en de resultaat in de XMLParser Steken
            //De XML Parser zal een ArrayList Terug Sturen
            APIHelper apiHelper = new APIHelper(xml, 0, 200);
            try {
                lijst = XMLParser.deParser((String) apiHelper.execute().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (lijst != null) {
                if (lijst.size() > IDs.size()) {
                    int verschil = lijst.size() - IDs.size();
                    teller++;

                    Uri a1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);

                    Notification notification = new Notification.Builder(this)
                            .setContentTitle("Er zijn " + verschil + " jobs beschikbaar!")
                            .setContentText("De profiel is " + profielen.get(kk).getNaam())
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .build();
                    mgr.notify(teller, notification);

                    SharedPreferences opslaanIDs = getSharedPreferences("ids", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = opslaanIDs.edit();
                    editor.putInt("count", lijst.size());
                    for (int i = 0; i < lijst.size(); i++) {
                        editor.putString("id " + i, lijst.get(i).getId());
                    }
                    editor.apply();
                }
            }
        }
    }
}

