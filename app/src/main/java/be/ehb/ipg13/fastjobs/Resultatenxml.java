package be.ehb.ipg13.fastjobs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by Nadir on 14/05/2015.
 */
public class Resultatenxml {
    private static ArrayList<String> lijstTrefwoorden = new ArrayList<String>();
    private static String diploma;
    private static String typejob;
    private static String soortJob;
    private static String gemeente;
    private static String afstand;
    private static String trefwoord;

    private static String xml1;
    static final int READ_BLOCK_SIZE = 100;

    public static String getGegevens(Context context) {
        //get voorkeuren
        SharedPreferences voorkeuren = context.getSharedPreferences("voorkeuren", Context.MODE_PRIVATE);
        diploma = voorkeuren.getString("diploma", "diploma niet gevonden");
        typejob = voorkeuren.getString("typejob", "Typejob niet gevonden!");
        soortJob = voorkeuren.getString("soortjob", "Soortjob niet gevonden!");
        gemeente = voorkeuren.getString("gemeente", "Gemeente niet gevonden!");
        afstand = voorkeuren.getString("afstand", "Afstand niet gevonden!");
        trefwoord = voorkeuren.getString("trefwoorden", "trefwoorden niet gevonden!");

        //add begin
        xml1 = "<ZoekCriteria xmlns=\"http://vdab.be/eliseservices/model/zoek/v1\">";

        //add trefwoorden
        if (!trefwoord.equals("LEEG") && !trefwoord.equals("trefwoorden niet gevonden!")) {
            xml1 += "<ZoekVeld name=\"trefwoord\" value=\"" + trefwoord.toString() +"\"/> ";
        }
        //add diploma
        if (!diploma.equals("LEEG") && !diploma.equals("Diploma niet gevonden!")) {
            if (diploma.equals("Bacheler")) {
                xml1 += "<ZoekVeld name=\"srch_diploma\" value=\"B\"/> ";
            }
            if (diploma.equals("Master")) {
                xml1 += "<ZoekVeld name=\"srch_diploma\" value=\"M\"/> ";
            }
        }

        //add typejob
        if (!typejob.equals("LEEG") && !typejob.equals("Typejob niet gevonden!")) {
            if (typejob.equals("Voltijds")) {
                xml1 += "<ZoekVeld name=\"tijdregeling\" value=\"V\"/> ";
            }
            if (typejob.equals("Deeltijds")) {
                xml1 += "<ZoekVeld name=\"tijdregeling\" value=\"D\"/> ";
            }
        }

        //add soortjob
        if (!soortJob.equals("LEEG") && !soortJob.equals("Soortjob niet gevonden!")) {
            if (soortJob.equals("Interim job")) {
                xml1 += "<ZoekVeld name=\"srch_soort\" value=\"I\"/> ";
                xml1 += "<ZoekVeld name=\"srch_soort\" value=\"U\"/> ";
            }
            if (soortJob.equals("Studentenjob")) {
                xml1 += "<ZoekVeld name=\"srch_soort\" value=\"S\"/> ";
            }
            if (soortJob.equals("Vaste job")) {
                xml1 += "<ZoekVeld name=\"srch_soort\" value=\"V\"/> ";
            }
        }
        //add gemeente
        if (!gemeente.equals("LEEG") && !gemeente.equals("Gemeente niet gevonden!")) {
            xml1 += "<ZoekVeld name=\"postcodeGemeenteText\" value=\"" + gemeente + "\"/> ";
        }

        //add afstand in km
        if (!afstand.equals("LEEG") && !afstand.equals("Afstand niet gevonden!")) {
            xml1 += "<ZoekVeld name=\"afstand\" value=\"" + afstand + "\"/> ";
        }
        //add end
        xml1 += "<ReturnVeld name=\"vacatureid\" /> <ReturnVeld name=\"functienaam\" /><ReturnVeld name=\"gemeente\" /> </ZoekCriteria>";
        return xml1;
    }
}
