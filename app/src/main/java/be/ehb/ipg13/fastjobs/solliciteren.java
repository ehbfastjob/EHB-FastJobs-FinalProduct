package be.ehb.ipg13.fastjobs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class solliciteren extends Activity {

    public static String url ;
    public static String id;
    TextView functie;
    TextView description;
    TextView organizaion;
    TextView prof;
    TextView vacatureinfo;
    TextView lablVacature, typeLbl, ervaringlbl, opleidinglbl, ervaringinfo, opleidinginfo, descriptionlbl, profielLbl, contactTel,
            contactMail, contactWeb;
    Button bsolliciteren;


    private static final String TAG_URL= "url";
    private static final String TAG_FUNCTIENAAM = "functieNaam";
    private static final String TAG_functieOmschrijving = "functieOmschrijving";
    private static final String TAG_ORGANISATIE = "organisatie";
    private static final String TAG_STRAAT = "straat";
    private static final String TAG_POSTCODE = "postCode";
    private static final String TAG_GEMEENTE = "gemeente";
    private static final String TAG_LAND = "land";
    private static final String TAG_NAAM = "naam";
    private static final String TAG_PROFIEL = "profiel";
    private static final String TAG_VEREISTEN = "vereisten";
    private static final String TAG_TALEN = "talen";
    private static ArrayList<String> TAG_TAAL ;
    private static final String TAG_TAAL1 = "nederlands";
    private static final String TAG_TAAL2 = "engels";
    private static  final  String TAG_TIJDSREGELING ="tijdregeling";
    private static  final String TAG_SOORTJOB ="soortJob";
    private static final String TAG_STUDIES ="studies";
    private  static  final String TAG_WERKERVARING="werkervaring";
    private static final String TAG_AANBODENVOORDELEN ="aanbodEnVoordelen";
    private static  final String TAG_SOLLICITREN = "solliciteren";
    private static  final String TAG_EMAIL = "emailAdresVoorSollicitatieViaEmail";
    private static  final  String TAG_TEL ="telefoon";
    private static  final String TAG_WEB ="webformulier";
    private static  final  String STRAAT ="straat";
    private static  final String POSTCODE ="postcode";
    private static  final  String GEMEENTE ="gemeente";
    private static  final String LAND ="land";
    private String straat = null;
    private String postcode = null;
    private String gemeente = null;
    private String land = null;
    private String naam = null;

    //private String functieNaam = null;
    //private String functieOmschrijving = null;
    //private String profiel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solliciteren);

        bsolliciteren = (Button) findViewById(R.id.btnsolliciteren);

        //System.out.println(getIntent().getExtras().get("TAG_ID"));

        Intent i = getIntent();

        url = i.getStringExtra(TAG_URL);


        bsolliciteren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vacature = ((TextView) findViewById(R.id.functienaam)).getText().toString();

                String bedrijf = ((TextView) findViewById(R.id.bedrijfinfo)).getText().toString();
                String telefoon = ((TextView) findViewById(R.id.contacTel)).getText().toString();
                String email = ((TextView) findViewById(R.id.contactEmail)).getText().toString();
                String web = ((TextView) findViewById(R.id.contactWeb)).getText().toString();

                Intent takeUserToSolliciteren2 = new Intent(getApplicationContext(), solliciteren2.class);
                takeUserToSolliciteren2.putExtra(TAG_FUNCTIENAAM, vacature);
                takeUserToSolliciteren2.putExtra(TAG_ORGANISATIE, bedrijf);
                takeUserToSolliciteren2.putExtra(TAG_TEL, telefoon);
                takeUserToSolliciteren2.putExtra(TAG_EMAIL, email);
                takeUserToSolliciteren2.putExtra(TAG_WEB, web);
                takeUserToSolliciteren2.putExtra(STRAAT,straat);
                takeUserToSolliciteren2.putExtra(POSTCODE,postcode);
                takeUserToSolliciteren2.putExtra(GEMEENTE,gemeente);
                takeUserToSolliciteren2.putExtra(LAND,land);
                startActivity(takeUserToSolliciteren2);



            }
        });

        new JsonParse().execute();



    }

    private class JsonParse extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            contactTel = (TextView) findViewById(R.id.contacTel);
            contactMail = (TextView) findViewById(R.id.contactEmail);
            contactWeb = (TextView) findViewById(R.id.contactWeb);
            functie = (TextView) findViewById(R.id.functienaam);
            description = (TextView) findViewById(R.id.description);
            prof = (TextView) findViewById(R.id.profile);
            organizaion = (TextView) findViewById(R.id.bedrijfinfo);
            vacatureinfo = (TextView) findViewById(R.id.vacinfo);
            lablVacature = (TextView) findViewById(R.id.vacature);
            typeLbl = (TextView) findViewById(R.id.typeJob);
            typeLbl.setPaintFlags(typeLbl.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            ervaringlbl = (TextView) findViewById(R.id.jobErvaring);
            ervaringlbl.setPaintFlags(ervaringlbl.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            ervaringinfo = (TextView) findViewById(R.id.jobErvaringView);
            opleidinglbl = (TextView) findViewById(R.id.jobOpleiding);
            opleidinglbl.setPaintFlags(opleidinglbl.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            opleidinginfo = (TextView) findViewById(R.id.opleidingView);
            descriptionlbl = (TextView) findViewById(R.id.descriptionlbl);
            descriptionlbl.setPaintFlags(descriptionlbl.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            profielLbl = (TextView) findViewById(R.id.profielTitle);
            profielLbl.setPaintFlags(profielLbl.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            pd = new ProgressDialog(solliciteren.this);
            pd.setMessage("Gegevens worden opgeladen...");

            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {

            ServiceHandler sh = new ServiceHandler();
            String jsonsr = sh.makeServiceCall(url, ServiceHandler.GET, null);
            return jsonsr;
        }

        @Override
        protected void onPostExecute(String jsonsr) {
            pd.dismiss();
            //if (jsonsr != null)
            try {

                JSONObject json = new JSONObject(jsonsr);
                final String functieNaam = json.getString(TAG_FUNCTIENAAM);
                String functieOmschrijving = json.getString(TAG_functieOmschrijving);

                // organisatie Json object
                JSONObject organisatie = json.getJSONObject(TAG_ORGANISATIE);
                straat = organisatie.getString(TAG_STRAAT);
                postcode = organisatie.getString(TAG_POSTCODE);
                gemeente = organisatie.getString(TAG_GEMEENTE);
                land = organisatie.getString(TAG_LAND);
                naam = organisatie.getString(TAG_NAAM);
                //profiel Json object

                JSONObject profile = json.getJSONObject(TAG_PROFIEL);
                String vereisten = profile.getString(TAG_VEREISTEN);
                JSONArray talen = profile.getJSONArray(TAG_TALEN);
                String taal;

                if(talen != null)
                {

                    for(int i =0; i < talen.length(); i++)
                    {

                        taal = talen.get(i).toString();
                    }
                    //taal = builder.toString();
                }
                else {
                    taal = "Geen ";
                }
                JSONArray studies = profile.getJSONArray(TAG_STUDIES);
                String studie="";

                if(studies != null)
                {

                    for(int i = 0; i< studies.length(); i++)
                    {
                        //sb.append(studies.getInt(i));
                        studie = studies.get(i).toString();
                    }
                    // studie = sb.toString();
                }
                else
                {
                    studie = "Geen";
                }
                String werkervaring = profile.getString(TAG_WERKERVARING);
                JSONObject aanbod = json.getJSONObject(TAG_AANBODENVOORDELEN);
                String tijdsregeling = aanbod.getString(TAG_TIJDSREGELING);
                String soortjob = aanbod.getString(TAG_SOORTJOB);



                JSONObject solliciteren = json.getJSONObject(TAG_SOLLICITREN);
                final String email = solliciteren.getString(TAG_EMAIL);
                final String tel = solliciteren.getString(TAG_TEL);
                final String web = solliciteren.getString(TAG_WEB);

                if(tel != null )
                {
                    contactTel.setText(tel);
                }
                if(email != null)
                {
                    contactMail.setText(email);
                }
                if(web != null)
                {
                    contactWeb.setText(Html.fromHtml(web));
                }

                functie.setText(functieNaam);
                organizaion.setText("Bij :"+naam +"\n" + straat +"\n" +postcode+" "+gemeente +"\n" +land);
                description.setText(Html.fromHtml(functieOmschrijving));
                vacatureinfo.setText( soortjob +", "+ tijdsregeling );
                ervaringinfo.setText(werkervaring);
                opleidinginfo.setText(studie);
                prof.setText(Html.fromHtml(vereisten));
                bedrijf b = new bedrijf();
                b.setNaam(naam);
                b.setStraat(straat);



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }
/*
    public void solliciteren(View button )
    {
       String jsonsr = new JsonParse().doInBackground();

        System.out.println(jsonsr);
        try {
            JSONObject json = new JSONObject(jsonsr);
            String functieNaam = json.getString(TAG_FUNCTIENAAM);

            // organisatie Json object
            JSONObject organisatie = json.getJSONObject(TAG_ORGANISATIE);
            String straat = organisatie.getString(TAG_STRAAT);
            String postcode = organisatie.getString(TAG_POSTCODE);
            String gementee = organisatie.getString(TAG_GEMEENTE);
            String land = organisatie.getString(TAG_LAND);
            String naam = organisatie.getString(TAG_NAAM);


            JSONObject solliciteren = json.getJSONObject(TAG_SOLLICITREN);
            String email = solliciteren.getString(TAG_EMAIL);
            String tel = solliciteren.getString(TAG_TEL);
            String web = solliciteren.getString(TAG_WEB);

            Intent takeUserToSolliciteren2 = new Intent(this.getApplicationContext(), solliciteren2.class);

            takeUserToSolliciteren2.putExtra(TAG_FUNCTIENAAM,functieNaam);
            takeUserToSolliciteren2.putExtra(TAG_NAAM, naam);
            takeUserToSolliciteren2.putExtra(TAG_GEMEENTE,gementee);
            takeUserToSolliciteren2.putExtra(TAG_STRAAT, straat);
            takeUserToSolliciteren2.putExtra(TAG_POSTCODE, postcode);
            takeUserToSolliciteren2.putExtra(TAG_LAND, land);
            takeUserToSolliciteren2.putExtra(TAG_EMAIL, email);
            takeUserToSolliciteren2.putExtra(TAG_TEL, tel);
            takeUserToSolliciteren2.putExtra(TAG_WEB, web);

            startActivity(takeUserToSolliciteren2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
*/

}


