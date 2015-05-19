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


public class profiel_aanmaken_jobvoorkeuren extends ActionBarActivity {

    Button volgende;
    EditText gemeente, km, profielNaam;
    Spinner tijdsregeling, soortjob;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel_aanmaken_jobvoorkeuren);
        this.getSupportActionBar().hide();

        final ImageView img = (ImageView) findViewById(R.id.imgView5);
        img.setImageResource(R.drawable.balls2);

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

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DDD", Toast.LENGTH_SHORT).show();
            }
        });

        volgende.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profielNaam.getText().equals("")) {
                    Toast.makeText(profiel_aanmaken_jobvoorkeuren.this, "Je moet een profielNaam ingeven!", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent("be.ehb.ipg13.fastjobs.trefwoorden");
                    i.putExtra("profielNaam", profielNaam.getText().toString());
                    if (!tijdsregeling.getSelectedItem().equals("")) {
                        i.putExtra("tijdsregeling", tijdsregeling.getSelectedItem().toString());
                    } else {
                        i.putExtra("tijdsregeling", "LEEG");
                    }

                    if (!soortjob.getSelectedItem().equals("")) {
                        i.putExtra("soortjob", soortjob.getSelectedItem().toString());
                    } else {
                        i.putExtra("soortjob", "LEEG");
                    }

                    if (!gemeente.getText().toString().equals("")) {
                        if (!km.getText().toString().equals("")) {
                            i.putExtra("gemeente", gemeente.getText().toString());
                            i.putExtra("aantalKM", km.getText().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Geef km!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        i.putExtra("gemeente", "LEEG");
                        i.putExtra("aantalKM", "LEEG");
                    }
                    startActivity(i);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profiel_aanmaken_jobvoorkeuren, menu);
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
