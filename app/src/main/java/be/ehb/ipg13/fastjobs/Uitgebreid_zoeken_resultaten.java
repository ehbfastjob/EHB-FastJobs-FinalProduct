package be.ehb.ipg13.fastjobs;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Uitgebreid_zoeken_resultaten extends ActionBarActivity {

    private static String TAG_URL ="url";
    ArrayList<ModelTest> lijstje;
    ArrayList<String> trefwoordLijst = new ArrayList<String>();
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uitgebreid_zoeken_resultaten);
        String res = Resultatenxml.getGegevens(getApplicationContext());
        System.out.println("RESSSSSSSS = " + res);
        String abc = "";

        APIHelper apiHelper = new APIHelper(res, 0, 25);
        try {
            lijstje = XMLParser.deParser((String) apiHelper.execute().get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        lijstVullen();

    }

    private void lijstVullen() {
        //System.out.println("RESULTATEN WORDEN HIER GETOOND: ");
        if (lijstje != null) {
            System.out.println(lijstje);
            ArrayAdapter<ModelTest> adapter = new myListAdapter();
            ListView listView = (ListView) findViewById(R.id.listView);
            //listView.setAdapter(adapter);
            listView.setAdapter(adapter);
        }

    }

    public class myListAdapter extends ArrayAdapter<ModelTest> {
        public myListAdapter() {
            super(getApplicationContext(), R.layout.item_view, lijstje);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            view = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            final ModelTest modelTest = lijstje.get(position);

            TextView vacTitel = (TextView) view.findViewById(R.id.lbl_titel);
            vacTitel.setText(modelTest.getTitel());
/*
            TextView vacId = (TextView) view.findViewById(R.id.lbl_bij_tekst);
            vacId.setText(modelTest.getId());
*/
            TextView vacGemeente = (TextView) view.findViewById(R.id.lbl_in_tekst);
            vacGemeente.setText(modelTest.getGemeente());

            final ImageButton fav = (ImageButton) view.findViewById(R.id.btn_fav);
            fav.setImageResource(R.drawable.ic_staroff);
            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(flag == false) {
                        fav.setImageResource(R.drawable.ic_sar);
                        flag = true;
                    }
                    else
                    {
                        fav.setImageResource(R.drawable.ic_staroff);
                        flag = false;
                    }



                    System.out.println("DE KNOP WERKT!!!!");
                    //int entryID = Integer.parseInt(modelTest.getId());
                    String entryID = modelTest.getId();

                    String entryTitle = modelTest.getTitel();
                    String entryGemeente;
                    if (modelTest.getGemeente() != null) {
                        entryGemeente = modelTest.getGemeente();
                    } else {
                        entryGemeente = "Geen Gemeente";
                    }
                    System.out.println("entyID == " + entryID);
                    System.out.println("etryTitle == " + entryTitle);
                    System.out.println("entryGemeente == " + entryGemeente);

                    boolean check = true;
                    try {
                        SaveJobSQL entry = new SaveJobSQL(getContext());
                        entry.open();
                        entry.createEntry(entryID, entryTitle, entryGemeente);
                        entry.close();
                        System.out.println("HET IS IN DE TRY!!!!");
                    } catch (Exception e) {
                        check = false;
                        String error = e.toString();
                        Dialog d = new Dialog(getContext());
                        d.setTitle(" error");
                        TextView tv = new TextView(getContext());
                        tv.setText(error);
                        d.setContentView(tv);
                        d.show();
                        System.out.println("HET IS IN DE CATCH!!!!");
                    }
                }
            });
            if (position % 2 == 1) {
                view.setBackgroundColor(Color.parseColor("#ff2a8171"));
                vacTitel.setTextColor(Color.parseColor("#ffffffff"));
                //vacId.setTextColor(Color.parseColor("#ffffffff"));
                vacGemeente.setTextColor(Color.parseColor("#ffffffff"));
            }else{
                vacTitel.setTextColor(Color.parseColor("#ff2a8171"));
                //vacId.setTextColor(Color.parseColor("#ff2a8171"));
                vacGemeente.setTextColor(Color.parseColor("#ff2a8171"));
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("DE POSITITE IS ==> " + position);
                    Intent detail = new Intent(getApplicationContext(),solliciteren.class);
                    String url = "http://vdabservices-cbt.vdab.be/vacaturedetail/1.0.0/"+modelTest.getId();
                    detail.putExtra(TAG_URL, url );
                    startActivity(detail);

                }
            });
            return view;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uitgebreid_zoeken_resultaten, menu);
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
