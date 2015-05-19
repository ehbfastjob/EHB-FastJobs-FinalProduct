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
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class Uitgebreid_zoeken extends ActionBarActivity {
    Button zoeken;
    EditText trefwoorden,gemeente, afstand;
    Spinner diploma,typejob, soortjob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uitgebreid_zoeken);
        this.getSupportActionBar().hide();

        trefwoorden = (EditText) findViewById(R.id.txt_trefwoorden);
        diploma = (Spinner) findViewById(R.id.spnr_diploma);
        zoeken = (Button) findViewById(R.id.btn_zoeken);
        typejob = (Spinner) findViewById(R.id.spnr_typeJob);
        soortjob = (Spinner) findViewById(R.id.spnr_soortJob);
        gemeente = (EditText) findViewById(R.id.txt_gemeente);
        afstand = (EditText) findViewById(R.id.txt_afstand);


        final String[] typeJob = {"", "Voltijds", "Deeltijds"};
        ListAdapter adapterTypejob = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeJob);

        String[] soortJob = {"", "Vaste job", "Interim job", "Studentenjob"};
        ListAdapter adapterSoortJob = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, soortJob);

        final String[] diplomas = {"", "Bachelor", "Master"};
        ListAdapter adapterDiploma = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, diplomas);

        typejob.setAdapter((android.widget.SpinnerAdapter) adapterTypejob);
        soortjob.setAdapter((android.widget.SpinnerAdapter) adapterSoortJob);
        diploma.setAdapter((android.widget.SpinnerAdapter) adapterDiploma);

        trefwoorden.setBackgroundColor(Color.parseColor("#ff2a8171"));
        gemeente.setBackgroundColor(Color.parseColor("#ff2a8171"));
        afstand.setBackgroundColor(Color.parseColor("#ff2a8171"));


        zoeken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences opslaanJobVoorkeuren = getSharedPreferences("voorkeuren", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = opslaanJobVoorkeuren.edit();

                if (!trefwoorden.getText().toString().equals("")) {
                    editor.putString("trefwoorden", trefwoorden.getText().toString());
                } else {
                    editor.putString("trefwoorden", "LEEG");
                }

                if (!diploma.getSelectedItem().equals("")) {
                    editor.putString("diploma", diploma.getSelectedItem().toString());
                } else {
                    editor.putString("diploma", "LEEG");
                }

                if (!typejob.getSelectedItem().equals("")) {
                    editor.putString("typejob", typejob.getSelectedItem().toString());
                } else {
                    editor.putString("typejob", "LEEG");
                }

                if (!soortjob.getSelectedItem().equals("")) {
                    editor.putString("soortjob", soortjob.getSelectedItem().toString());
                } else {
                    editor.putString("soortjob", "LEEG");
                }

                if (!gemeente.getText().toString().equals("")) {
                    if (!afstand.getText().toString().equals("")) {
                        editor.putString("gemeente", gemeente.getText().toString());
                        editor.putString("afstand", afstand.getText().toString());
                    }else{
                        Toast.makeText(getApplicationContext(), "Geef afstand!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    editor.putString("gemeente", "LEEG");
                    editor.putString("afstand", "LEEG");
                }
                editor.apply();

                Intent i = new Intent(getApplicationContext(),Uitgebreid_zoeken_resultaten.class);
                startActivity(i);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uitgebreid_zoeken, menu);
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
