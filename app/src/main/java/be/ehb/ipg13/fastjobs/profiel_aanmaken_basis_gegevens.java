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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class profiel_aanmaken_basis_gegevens extends ActionBarActivity {

    Button volgende;
    EditText voornaam, achternaam, email, rijbewijs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel_aanmaken_basis_gegevens);

        this.getSupportActionBar().hide();
        /*
        getSharedPreferences("ervaringen", Context.MODE_PRIVATE).edit().clear();
        getSharedPreferences("opleidingen", Context.MODE_PRIVATE).edit().clear();
        getSharedPreferences("jobVoorkeuren", Context.MODE_PRIVATE).edit().clear();
        getSharedPreferences("trefwoorden", Context.MODE_PRIVATE).edit().clear();
        getSharedPreferences("vaardigheden", Context.MODE_PRIVATE).edit().clear();
        getSharedPreferences("basisgegevens", Context.MODE_PRIVATE).edit().clear();
*/
        SharedPreferences opslaanErvaring = getSharedPreferences("ervaringen", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = opslaanErvaring.edit();
        editor.clear();
        editor.apply();

        SharedPreferences opslaanOpleiding = getSharedPreferences("opleidingen", Context.MODE_PRIVATE);
        editor = opslaanOpleiding.edit();
        editor.clear();
        editor.apply();

        SharedPreferences opslaanJobVoorkeuren = getSharedPreferences("jobVoorkeuren", Context.MODE_PRIVATE);
        editor = opslaanJobVoorkeuren.edit();
        editor.clear();
        editor.apply();

        SharedPreferences opslaanTrefwoorden = getSharedPreferences("trefwoorden", Context.MODE_PRIVATE);
        editor = opslaanTrefwoorden.edit();
        editor.clear();
        editor.apply();

        SharedPreferences opslaanVaardigheden = getSharedPreferences("vaardigheden", Context.MODE_PRIVATE);
        editor = opslaanVaardigheden.edit();
        editor.clear();
        editor.apply();

        SharedPreferences opslaanBasisgegevens = getSharedPreferences("basisgegevens", Context.MODE_PRIVATE);
        editor = opslaanBasisgegevens.edit();
        editor.clear();
        editor.apply();

        final ImageView img = (ImageView) findViewById(R.id.imgView1);
        img.setImageResource(R.drawable.header1);

        volgende = (Button) findViewById(R.id.btn_volgende_1);
        voornaam = (EditText) findViewById(R.id.txt_voornaam);
        achternaam = (EditText) findViewById(R.id.txt_achternaam);
        email = (EditText) findViewById(R.id.txt_email);
        rijbewijs = (EditText) findViewById(R.id.txt_rijbewijs);

        voornaam.setBackgroundColor(Color.LTGRAY);
        achternaam.setBackgroundColor(Color.LTGRAY);
        email.setBackgroundColor(Color.LTGRAY);
        rijbewijs.setBackgroundColor(Color.LTGRAY);

        volgende.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!voornaam.getText().toString().equals("") || !achternaam.getText().toString().equals("") || !email.getText().toString().equals("") || !rijbewijs.getText().toString().equals("")) {
                    SharedPreferences opslaanBasisgegevens = getSharedPreferences("basisgegevens", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = opslaanBasisgegevens.edit();
                    editor.putString("voornaam ", voornaam.toString());
                    editor.putString("achternaam ", achternaam.toString());
                    editor.putString("email ", email.toString());
                    editor.putString("rijbewijs ", rijbewijs.toString());
                    editor.apply();

                    SharedPreferences opslaanEersteKeer = getSharedPreferences("EersteKeerPref", Context.MODE_PRIVATE);
                    editor = opslaanEersteKeer.edit();
                    editor.putString("EersteKeer", "JA");
                    editor.apply();
                    //Toast.makeText(getApplicationContext(), "SAVED!", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), opslaanErvaring.getString("bedrijf 1","!!").toString(), Toast.LENGTH_LONG).show();
                    /*
                    Intent i = new Intent("be.ehb.ipg13.fastjobs.opleidingen");
                    startActivity(i);
                    */
                    Intent i = new Intent("be.ehb.ipg13.fastjobs.jobvoorkeur");
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Je moet alles invullen!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profiel_aanmaken_basis_gegevens, menu);
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
