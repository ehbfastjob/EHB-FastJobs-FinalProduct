package be.ehb.ipg13.fastjobs;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class profielen_bekijken extends ActionBarActivity {
    TextView naam, tijdsregeling, soortjob, gemeente, afstand, tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profielen_bekijken);

        naam = (TextView) findViewById(R.id.txt_naam);
        tijdsregeling = (TextView) findViewById(R.id.txt_tijdsregeling);
        soortjob = (TextView) findViewById(R.id.txt_soortjob);

        gemeente = (TextView) findViewById(R.id.txt_gemeente);
        afstand = (TextView) findViewById(R.id.txt_afstand);
        tags = (TextView) findViewById(R.id.txt_tags);

        String deProfielNaam = getIntent().getExtras().getString("deProfielNaam");
        ProfielSQL profielSQL = new ProfielSQL(this);
        profielSQL.open();
        ArrayList<Profiel_model> gegevensOphalenProfiel = profielSQL.getAll();
        profielSQL.close();
        Profiel_model deProfiel = null;
        for (int k = 0; k < gegevensOphalenProfiel.size(); k++) {
            if (gegevensOphalenProfiel.get(k).getNaam().equals(deProfielNaam)) {
                deProfiel = gegevensOphalenProfiel.get(k);
            }
        }
        naam.setText(deProfiel.getNaam());
        tijdsregeling.setText(deProfiel.getTijdsregeling());
        soortjob.setText(deProfiel.getSoortjob());

        gemeente.setText(deProfiel.getGemeente());
        afstand.setText(deProfiel.getAantalKM());
        ArrayList<String> profielTags = deProfiel.getTrefwoorden();
        String deTags = "";
        for (int j = 0; j < profielTags.size(); j++) {
           /* if (j == 0) {
                deTags = profielTags.get(j) + ", ";
            }*/
            if (j == (profielTags.size()-1)){
                deTags += profielTags.get(j);
            }else{
                deTags += profielTags.get(j) + ", ";
            }
        }
        tags.setText(deTags);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profielen_bekijken, menu);
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
