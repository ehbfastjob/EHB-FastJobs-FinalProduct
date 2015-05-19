package be.ehb.ipg13.fastjobs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class wijzig_jobvoorkeuren extends ActionBarActivity {
    Button volgende;
    EditText gemeente, km, profielNaam;
    Spinner tijdsregeling, soortjob;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wijzig_jobvoorkeuren);

        this.getSupportActionBar().hide();

        final String deProfielNaam = getIntent().getExtras().getString("deProfielNaam").toString();
        String idd = null;
        ProfielSQL profielSQL = new ProfielSQL(this);
        profielSQL.open();
        ArrayList<Profiel_model> gegevensOphalenProfiel = profielSQL.getAll();
        profielSQL.close();
        Profiel_model deProfiel = null;
        for (int k = 0; k < gegevensOphalenProfiel.size(); k++) {
            if (gegevensOphalenProfiel.get(k).getNaam().equals(deProfielNaam)) {
                deProfiel = gegevensOphalenProfiel.get(k);
                idd = gegevensOphalenProfiel.get(k).getProfielID();
            }
        }
        final String idd2 = idd;
        final Profiel_model profielFinal = deProfiel;
        //final ImageView img = (ImageView) findViewById(R.id.imgView5);
        //img.setImageResource(R.drawable.header5);

        volgende = (Button) findViewById(R.id.btn_volgende_5);
        tijdsregeling = (Spinner) findViewById(R.id.spnr_tijdsregeling);
        soortjob = (Spinner) findViewById(R.id.spnr_soortJob);
        gemeente = (EditText) findViewById(R.id.txt_gemeente);
        km = (EditText) findViewById(R.id.txt_km);
        profielNaam = (EditText) findViewById(R.id.txt_profielNaam);
        skip = (TextView) findViewById(R.id.lbl_skip_5);

        String[] tijdsregelingen = {"", "Voltijds", "Deeltijds"};
        ListAdapter adapterTijdsregeling = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tijdsregelingen);

        final String[] soortenJobs = {"", "Vaste job", "Interim job", "Studentenjob"};
        ListAdapter adapterSoortJob = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, soortenJobs);

        tijdsregeling.setAdapter((android.widget.SpinnerAdapter) adapterTijdsregeling);
        soortjob.setAdapter((android.widget.SpinnerAdapter) adapterSoortJob);

        profielNaam.setBackgroundColor(Color.parseColor("#ff2a8171"));
        gemeente.setBackgroundColor(Color.parseColor("#ff2a8171"));
        km.setBackgroundColor(Color.parseColor("#ff2a8171"));

        // GEGEVENS OP DE JUISTE PLAATS TOEVOEGEN
        profielNaam.setText(deProfiel.getNaam());
        if (deProfiel.getTijdsregeling().equals("Voltijds")) {
            tijdsregeling.setSelection(1);
        } else if (deProfiel.getTijdsregeling().equals("Deeltijds")) {
            tijdsregeling.setSelection(2);
        } else {
            tijdsregeling.setSelection(0);
        }
        if (deProfiel.getSoortjob().equals("Vaste job")) {
            soortjob.setSelection(1);
        } else if (deProfiel.getSoortjob().equals("Interim job")) {
            soortjob.setSelection(2);
        } else if (deProfiel.getSoortjob().equals("Studentenjob")) {
            soortjob.setSelection(3);
        } else {
            soortjob.setSelection(0);
        }
        gemeente.setText(deProfiel.getGemeente());
        km.setText(deProfiel.getAantalKM());

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DDD", Toast.LENGTH_SHORT).show();
            }
        });

        volgende.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(wijzig_jobvoorkeuren.this, wijzig_jobvoorkeuren_trefwoorden.class);
  //              System.out.println("VOLGENDE:1: DE ID IS =-> " + profielFinal.getProfielID());
                System.out.println("VOLGENDE:2: DE ID IS =-> " + idd2.toString());
                i.putExtra("id", profielFinal.getProfielID());
                i.putExtra("profielNaam", profielNaam.getText().toString());
                i.putExtra("tijdsregeling", tijdsregeling.getSelectedItem().toString());
                i.putExtra("soortjob", soortjob.getSelectedItem().toString());
                i.putExtra("gemeente", gemeente.getText().toString());
                i.putExtra("aantalKM", km.getText().toString());
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wijzig_jobvoorkeuren, menu);
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
