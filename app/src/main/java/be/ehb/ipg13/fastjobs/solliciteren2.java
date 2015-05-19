package be.ehb.ipg13.fastjobs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class solliciteren2 extends ActionBarActivity {
    private static  final String TAG_EMAIL = "emailAdresVoorSollicitatieViaEmail";
    private static  final  String TAG_TEL ="telefoon";
    private static  final String TAG_WEB ="webformulier";
    private static final String TAG_FUNCTIENAAM = "functieNaam";
    private static final String TAG_STRAAT = "straat";
    private static final String TAG_POSTCODE = "postCode";
    private static final String TAG_GEMEENTE = "gemeente";
    private static final String TAG_LAND = "land";
    private static final String TAG_NAAM = "naam";
    private static final String TAG_ORGANISATIE ="organisatie";
    private static  final  String STRAAT ="straat";
    private static  final String POSTCODE ="postcode";
    private static  final  String GEMEENTE ="gemeente";
    private static  final String LAND ="land";
    TextView bedrijfsnaam;
    TextView adress;
    TextView functie, contact;
    Button bellen, emailen, surfen,kaart;
    private String straat,postcode,gemeente,land;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solliciteren2);

        bedrijfsnaam = (TextView)findViewById(R.id.bedrijfsNaam);
        adress = (TextView) findViewById(R.id.bedrijfsNaam);
        functie = (TextView) findViewById(R.id.vactureNaam);
        contact = (TextView) findViewById(R.id.solliciteren);
        bellen = (Button) findViewById(R.id.bellen);
        emailen = (Button) findViewById(R.id.mailen);
        surfen = (Button) findViewById(R.id.web);
        kaart =(Button) findViewById(R.id.kaart);
        Intent intent = getIntent();
        //String url = intent.getStringExtra(TAG_EMAIL);
        //String tel = intent.getStringExtra(TAG_TEL);
        //String web = intent.getStringExtra(TAG_WEB);

        String functienaam = intent.getStringExtra(TAG_FUNCTIENAAM);
        String bedrijf = intent.getStringExtra(TAG_ORGANISATIE);
        final String telefoon = intent.getStringExtra(TAG_TEL);
        final String email = intent.getStringExtra(TAG_EMAIL);
        final String web = intent.getStringExtra(TAG_WEB);
        straat = intent.getStringExtra(STRAAT);
        postcode = intent.getStringExtra(POSTCODE);
        gemeente = intent.getStringExtra(GEMEENTE);
        land =intent.getStringExtra(LAND);

        if(telefoon == null | telefoon =="")
        {

            ((Button)findViewById(R.id.bellen)).setEnabled(false);
        }
        else if(email == null | email =="")
        {
            ((Button) findViewById(R.id.mailen)).setEnabled(false);
        }
        else if(web == null | web =="")
        {
            ((Button) findViewById(R.id.web)).setEnabled(false);
        }


        functie.setText(functienaam);
        // adress.setText(straat +"\n" + postcode +"\n" + gemeente +"\n" + land  );
        bedrijfsnaam.setText(bedrijf);
        contact.setText("Telefoon: "+ telefoon +"\n" +"Email :" + email +"\n" +"Web site :"+ Html.fromHtml(web));

        bellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View b) {
                if (telefoon != null) {
                    Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                    phoneIntent.setData(Uri.parse(telefoon));
                    try {
                        startActivity(phoneIntent);
                        finish();
                        Log.i("Finished making a call.", "");
                    } catch (ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "Call failed, please try later.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        emailen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n) {

                if(email != null)
                {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto"));
                    emailIntent.setType("text/plain");

                    emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sollicitatie");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Beste Mevrouw x\n" +
                            "Ik ben al jaren vaste klant bij H&M en ben erg vertrouwd met jullie kledinglijn. Toen ik op de\n" +
                            "VDAB-site las dat u op zoek bent naar een nieuwe medewerker, was ik dan ook heel\n" +
                            "enthousiast! Het lijkt me super om zo�n sterk merk te vertegenwoordigen. Daarom besloot ik\n" +
                            "onmiddellijk te solliciteren.\n" +
                            "In mijn job als verkoper bij Brantano leerde ik de kneepjes van het vak. Ik heb ervaring met\n" +
                            "verschillende klanten, kassawerk, rekken aanvullen, drukke periodes� Verder ben ik\n" +
                            "klantvriendelijk, commercieel ingesteld en flexibel, wat me tot een uitstekende kandidaat\n" +
                            "maakt. Uiteraard ben ik ook altijd bereid om me bij te scholen mocht dit nodig zijn.\n" +
                            "In een persoonlijk gesprek wil ik u graag verder overtuigen van mijn capaciteiten.\n" +
                            "Met vriendelijke groeten ");

                    try {
                        startActivity(Intent.createChooser(emailIntent,"send mail..."));
                        finish();
                        Log.i("Finished sending email.","");
                    } catch (ActivityNotFoundException ex)
                    {
                        Toast.makeText(getApplication(),"There is no email client installed.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        surfen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(web != null)
                {
                    Uri uri = Uri.parse(web);
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
            }
        });

        kaart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kaart != null)
                {
                    Uri uri = Uri.parse(web);
                    Intent intent = new Intent(getBaseContext(),google_MapsActivity.class);
                    intent.putExtra(STRAAT,straat);
                    intent.putExtra(POSTCODE,postcode);
                    intent.putExtra(GEMEENTE,gemeente);
                    intent.putExtra(LAND,land);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_solliciteren2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
