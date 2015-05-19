package be.ehb.ipg13.fastjobs;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Nadir on 19/04/2015.
 */
public class ProfielXML {
    private static ArrayList<String> trefwoordenLijst = new ArrayList<String>();
    private static ArrayList<Profiel_model> trefwoordenLijst2 = new ArrayList<Profiel_model>();
    private static String xml1;
    private static String xml2;
    private static ArrayList<String> xmlFiles1 = new ArrayList<String>();
    private static ArrayList<String> xmlFiles2 = new ArrayList<String>();
    static final int READ_BLOCK_SIZE = 100;
    private static String tijdsregeling;
    private static String soortJob;
    private static String gemeente;
    private static String afstand;

    private static void getGegevens1(Context context) {
        //HUIDIGE DATUM OPHALEN
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String geformatteerdeDatum = sdf.format(cal.getTime());

        xmlFiles1 = new ArrayList<String>();
        xmlFiles2 = new ArrayList<String>();

        ProfielSQL pp = new ProfielSQL(context);
        pp.open();
        trefwoordenLijst2 = pp.getAll();
        pp.close();
        for (int ij = 0; ij < trefwoordenLijst2.size(); ij++) {
            //BEGIN TOEVOEGEN
            String xml11 = "<ZoekCriteria xmlns=\"http://vdab.be/eliseservices/model/zoek/v1\">";
            String xml22 = "<ZoekCriteria xmlns=\"http://vdab.be/eliseservices/model/zoek/v1\">";

            //TREFWOORDEN TOEVOEGEN
            for (int i = 0; i < trefwoordenLijst2.get(ij).getTrefwoorden().size(); i++) {
                xml11 += "<ZoekVeld name=\"trefwoord\" value=\"" + trefwoordenLijst2.get(ij).getTrefwoorden().get(i).toString() + "\"/> ";
                xml22 += "<ZoekVeld name=\"trefwoord\" value=\"" + trefwoordenLijst2.get(ij).getTrefwoorden().get(i).toString() + "\"/> ";
            }

            //TIJDSREGELING TOEVOEGEN
            if (!trefwoordenLijst2.get(ij).getTijdsregeling().equals("LEEG")) {
                if (trefwoordenLijst2.get(ij).getTijdsregeling().equals("Voltijds")) {
                    xml11 += "<ZoekVeld name=\"tijdregeling\" value=\"V\"/> ";
                    xml22 += "<ZoekVeld name=\"tijdregeling\" value=\"V\"/> ";
                }
                if (trefwoordenLijst2.get(ij).getTijdsregeling().equals("Deeltijds")) {
                    xml11 += "<ZoekVeld name=\"tijdregeling\" value=\"D\"/> ";
                    xml22 += "<ZoekVeld name=\"tijdregeling\" value=\"D\"/> ";
                }
            }

            //SOORTJOB TOEVOEGEN
            if (!trefwoordenLijst2.get(ij).getSoortjob().equals("LEEG")) {
                if (trefwoordenLijst2.get(ij).getSoortjob().equals("Interim job")) {
                    xml11 += "<ZoekVeld name=\"srch_soort\" value=\"I\"/> ";
                    xml11 += "<ZoekVeld name=\"srch_soort\" value=\"U\"/> ";
                    xml22 += "<ZoekVeld name=\"srch_soort\" value=\"I\"/> ";
                    xml22 += "<ZoekVeld name=\"srch_soort\" value=\"U\"/> ";
                }
                if (trefwoordenLijst2.get(ij).getSoortjob().equals("Studentenjob")) {
                    xml11 += "<ZoekVeld name=\"srch_soort\" value=\"S\"/> ";
                    xml22 += "<ZoekVeld name=\"srch_soort\" value=\"S\"/> ";
                }
                if (trefwoordenLijst2.get(ij).getSoortjob().equals("Vaste job")) {
                    xml11 += "<ZoekVeld name=\"srch_soort\" value=\"V\"/> ";
                    xml22 += "<ZoekVeld name=\"srch_soort\" value=\"V\"/> ";
                }
            }
            //GEMEENTE TOEVOEGEN
            if (!trefwoordenLijst2.get(ij).getGemeente().equals("LEEG")) {
                xml11 += "<ZoekVeld name=\"postcodeGemeenteText\" value=\"" + trefwoordenLijst2.get(ij).getGemeente() + "\"/> ";
                xml22 += "<ZoekVeld name=\"postcodeGemeenteText\" value=\"" + trefwoordenLijst2.get(ij).getGemeente() + "\"/> ";
            }

            //KM TOEVOEGEN
            if (!trefwoordenLijst2.get(ij).getAantalKM().equals("LEEG")) {
                xml11 += "<ZoekVeld name=\"afstand\" value=\"" + trefwoordenLijst2.get(ij).getAantalKM() + "\"/> ";
                xml22 += "<ZoekVeld name=\"afstand\" value=\"" + trefwoordenLijst2.get(ij).getAantalKM() + "\"/> ";
            }

            //DATUM TOEVOEGEN
            xml22 += "<ZoekVeldGroep name=\"vacatureDatum\"> <ZoekVeld name=\"from\" value=\"" + geformatteerdeDatum.toString() + "\"/></ZoekVeldGroep>";

            //EINDE TOEVOEGEN
            xml11 += "<ReturnVeld name=\"vacatureid\" /> <ReturnVeld name=\"functienaam\" /><ReturnVeld name=\"gemeente\" /> </ZoekCriteria>";
            xml22 += "<ReturnVeld name=\"vacatureid\" /> <ReturnVeld name=\"functienaam\" /><ReturnVeld name=\"gemeente\" /> </ZoekCriteria>";

            xmlFiles1.add(xml11);
            xmlFiles2.add(xml22);
        }/*
        for (int jk = 0; jk < xmlFiles1.size();jk++){
            System.out.println("XML FILE1  IS " + jk + ":= " + xmlFiles1.get(jk));
        }
        for (int jk = 0; jk < xmlFiles2.size();jk++){
            System.out.println("XML FILE2  IS " + jk + ":= " + xmlFiles2.get(jk));
        }*/
        //DATUM OPSLAAN
        SharedPreferences opslaanDatum = context.getSharedPreferences("datum", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = opslaanDatum.edit();
        editor.putString("datum", geformatteerdeDatum.toString());
        editor.apply();
    }

    public static boolean gegevensWegSchrijven(Context context) {
        getGegevens1(context);
        FileOutputStream fileout = null;
        for (int jj = 0; jj < xmlFiles1.size(); jj++) {
            try {
                fileout = context.openFileOutput("profielXML" + jj + ".txt", Context.MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(xmlFiles1.get(jj).toString());
                outputWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            try {
                fileout = context.openFileOutput("notProfielXML" + jj + ".txt", Context.MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(xmlFiles2.get(jj).toString());
                outputWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static String gegevensOphalen(Context context, boolean keuze,String profielKeuze) {

        ProfielSQL profielSQL = new ProfielSQL(context);
        profielSQL.open();
        ArrayList<Profiel_model> gegevensOphalenProfiel = profielSQL.getAll();
        profielSQL.close();
        int index = -1;
        for (int k = 0; k < gegevensOphalenProfiel.size();k++){
            if (gegevensOphalenProfiel.get(k).getNaam().equals(profielKeuze)){
                index = k;
            }
        }
        //index *=2;
        if(index == -1){
            System.out.println("PROFIEL XML = de index is -1");
        }
        //HUIDIGE DATUM OPHALEN
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String huidigeDatum = sdf.format(cal.getTime());

        //JOBVOORKEUREN OPHALEN
        SharedPreferences datumOphalen = context.getSharedPreferences("datum", Context.MODE_PRIVATE);
        String datum = datumOphalen.getString("datum", "Datum niet gevonden!");

        if (!huidigeDatum.equals(datum)) {
            //System.out.println("++++++++++++++++---->>> IK ZIT IN GEGEVENSOPHALEN");
            gegevensWegSchrijven(context);
        }
        char[] inputBuffer = new char[READ_BLOCK_SIZE];
        String s = "";
        int charRead;
        try {
            FileInputStream fileIn;
            if (keuze) {
                fileIn = context.openFileInput("profielXML"+index+".txt");
            } else {
                fileIn = context.openFileInput("notProfielXML" + index + ".txt");
            }
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static void getGegevens222(Context context) {
        //HUIDIGE DATUM OPHALEN
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String geformatteerdeDatum = sdf.format(cal.getTime());

        //TREFWOORDEN OPHALEN
        SharedPreferences trefwoorden = context.getSharedPreferences("trefwoorden", Context.MODE_PRIVATE);
        int count = trefwoorden.getInt("count", -1);
        for (int i = 0; i < count; i++) {
            trefwoordenLijst.add(trefwoorden.getString("trefwoord " + i, "trefwoord " + i + " niet gevonden!"));
        }
        //JOBVOORKEUREN OPHALEN
        SharedPreferences jobVoorkeuren = context.getSharedPreferences("jobVoorkeuren", Context.MODE_PRIVATE);
        tijdsregeling = jobVoorkeuren.getString("tijdsregeling", "Tijdsregeling niet gevonden!");
        soortJob = jobVoorkeuren.getString("soortjob", "Soortjob niet gevonden!");
        gemeente = jobVoorkeuren.getString("gemeente", "Gemeente niet gevonden!");
        afstand = jobVoorkeuren.getString("km", "Afstand niet gevonden!");

        //BEGIN TOEVOEGEN
        xml1 = "<ZoekCriteria xmlns=\"http://vdab.be/eliseservices/model/zoek/v1\">";
        xml2 = "<ZoekCriteria xmlns=\"http://vdab.be/eliseservices/model/zoek/v1\">";

        //TREFWOORDEN TOEVOEGEN
        for (int i = 0; i < trefwoordenLijst.size(); i++) {
            xml1 += "<ZoekVeld name=\"trefwoord\" value=\"" + trefwoordenLijst.get(i).toString() + "\"/> ";
            xml2 += "<ZoekVeld name=\"trefwoord\" value=\"" + trefwoordenLijst.get(i).toString() + "\"/> ";
        }

        //TIJDSREGELING TOEVOEGEN
        if (!tijdsregeling.equals("LEEG") && !tijdsregeling.equals("Tijdsregeling niet gevonden!")) {
            if (tijdsregeling.equals("Voltijds")) {
                xml1 += "<ZoekVeld name=\"tijdregeling\" value=\"V\"/> ";
                xml2 += "<ZoekVeld name=\"tijdregeling\" value=\"V\"/> ";
            }
            if (tijdsregeling.equals("Deeltijds")) {
                xml1 += "<ZoekVeld name=\"tijdregeling\" value=\"D\"/> ";
                xml2 += "<ZoekVeld name=\"tijdregeling\" value=\"D\"/> ";
            }
        }

        //SOORTJOB TOEVOEGEN
        if (!soortJob.equals("LEEG") && !soortJob.equals("Soortjob niet gevonden!")) {
            if (soortJob.equals("Interim job")) {
                xml1 += "<ZoekVeld name=\"srch_soort\" value=\"I\"/> ";
                xml1 += "<ZoekVeld name=\"srch_soort\" value=\"U\"/> ";
                xml2 += "<ZoekVeld name=\"srch_soort\" value=\"I\"/> ";
                xml2 += "<ZoekVeld name=\"srch_soort\" value=\"U\"/> ";
            }
            if (soortJob.equals("Studentenjob")) {
                xml1 += "<ZoekVeld name=\"srch_soort\" value=\"S\"/> ";
                xml2 += "<ZoekVeld name=\"srch_soort\" value=\"S\"/> ";
            }
            if (soortJob.equals("Vaste job")) {
                xml1 += "<ZoekVeld name=\"srch_soort\" value=\"V\"/> ";
                xml2 += "<ZoekVeld name=\"srch_soort\" value=\"V\"/> ";
            }
        }
        //GEMEENTE TOEVOEGEN
        if (!gemeente.equals("LEEG") && !gemeente.equals("Gemeente niet gevonden!")) {
            xml1 += "<ZoekVeld name=\"postcodeGemeenteText\" value=\"" + gemeente + "\"/> ";
            xml2 += "<ZoekVeld name=\"postcodeGemeenteText\" value=\"" + gemeente + "\"/> ";
        }

        //KM TOEVOEGEN
        if (!afstand.equals("LEEG") && !afstand.equals("Afstand niet gevonden!")) {
            xml1 += "<ZoekVeld name=\"afstand\" value=\"" + afstand + "\"/> ";
            xml2 += "<ZoekVeld name=\"afstand\" value=\"" + afstand + "\"/> ";
        }

        //DATUM TOEVOEGEN
        xml2 += "<ZoekVeldGroep name=\"vacatureDatum\"> <ZoekVeld name=\"from\" value=\"" + geformatteerdeDatum.toString() + "\"/></ZoekVeldGroep>";

        //EINDE TOEVOEGEN
        xml1 += "<ReturnVeld name=\"vacatureid\" /> <ReturnVeld name=\"functienaam\" /><ReturnVeld name=\"gemeente\" /> </ZoekCriteria>";
        xml2 += "<ReturnVeld name=\"vacatureid\" /> <ReturnVeld name=\"functienaam\" /><ReturnVeld name=\"gemeente\" /> </ZoekCriteria>";

        //DATUM OPSLAAN
        SharedPreferences opslaanDatum = context.getSharedPreferences("datum", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = opslaanDatum.edit();
        editor.putString("datum", geformatteerdeDatum.toString());
        editor.apply();
    }

    public static boolean gegevensWegSchrijven222(Context context) {
        getGegevens1(context);
        FileOutputStream fileout = null;
        try {
            fileout = context.openFileOutput("profielXML1.txt", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(xml1.toString());
            outputWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            fileout = context.openFileOutput("profielXML2.txt", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(xml2.toString());
            outputWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String gegevensOphalen222(Context context, boolean keuze) {
        //HUIDIGE DATUM OPHALEN
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String huidigeDatum = sdf.format(cal.getTime());

        //JOBVOORKEUREN OPHALEN
        SharedPreferences datumOphalen = context.getSharedPreferences("datum", Context.MODE_PRIVATE);
        String datum = datumOphalen.getString("datum", "Datum niet gevonden!");

        if (!huidigeDatum.equals(datum)) {
            //System.out.println("++++++++++++++++---->>> IK ZIT IN GEGEVENSOPHALEN");
            gegevensWegSchrijven(context);
        }
        char[] inputBuffer = new char[READ_BLOCK_SIZE];
        String s = "";
        int charRead;
        try {
            FileInputStream fileIn;
            if (keuze) {
                fileIn = context.openFileInput("profielXML1.txt");
            } else {
                fileIn = context.openFileInput("profielXML2.txt");
            }
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
