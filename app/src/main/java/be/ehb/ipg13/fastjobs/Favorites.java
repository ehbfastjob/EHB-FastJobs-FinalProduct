package be.ehb.ipg13.fastjobs;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Favorites extends Fragment {
    View rootview;

    ArrayList<ModelTest> lijstje;
    ModelTest vactureDisplay;
    String gemeente;
    String titel;
    String vacID;
    private static String TAG_URL = "url";
    private  static  String TAG_NAME ="naam";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_favorites, container, false);

        try {
            SaveJobSQL displayDB = new SaveJobSQL(getActivity().getApplicationContext());
            displayDB.open();
            //displayDB.createEntry("1", "de titel", "de gemeente");
            //displayDB.createEntry("2", "de titel2", "de gemeente2");
            ModelTest m = new ModelTest();
            m.setGemeente("de gemeente");
            m.setId("123");
            m.setTitel("de titel");
            lijstje= displayDB.getFavorites();
            //lijstje = new ArrayList<ModelTest>();
            //lijstje.add(m);
            displayDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        listViewVullen();
        return rootview;

    }

    private void listViewVullen() {

        if (lijstje != null) {
            ArrayAdapter<ModelTest> adapter = new myListAdapter();
            ListView listView = (ListView) rootview.findViewById(R.id.myListViewFav);
            listView.setAdapter(adapter);
        }
    }

    public class myListAdapter extends ArrayAdapter<ModelTest> {
        public myListAdapter() {
            super(getActivity(), R.layout.item_view, lijstje);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            System.out.println("CONVERTVIEW == " + convertView);
            System.out.println("VIEW1 == " + view);
            view = getActivity().getLayoutInflater().inflate(R.layout.item_view, parent, false);
            System.out.println("VIEW2 == " + view);
            final ModelTest modelTest = lijstje.get(position);

            TextView vacTitel = (TextView) view.findViewById(R.id.lbl_titel);
            vacTitel.setText(modelTest.getTitel());
/*
            TextView vacId = (TextView) view.findViewById(R.id.lbl_bij_tekst);
            vacId.setText(modelTest.getId());
*/
            TextView vacGemeente = (TextView) view.findViewById(R.id.lbl_in_tekst);
            vacGemeente.setText(modelTest.getGemeente());
/*
            ImageButton fav = (ImageButton) view.findViewById(R.id.btn_fav);
            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Verwijder functie aan de knop toevoegen.

                }
            });*/
            ImageButton fav = (ImageButton) view.findViewById(R.id.btn_fav);
            fav.setImageResource(R.drawable.garbagebinfs);
            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Verwijder functie aan de knop toevoegen.


                    String entryID = modelTest.getId();



                    boolean check = true;
                    try {
                        SaveJobSQL entry = new SaveJobSQL(getActivity().getApplicationContext());
                        entry.open();
                        entry.deleteFavorite(entryID);

                        System.out.println("-----------------------------------------------------------HET IS IN DE TRY!!!!");
                        lijstje= entry.getFavorites();
                        entry.close();
                        listViewVullen();

                    } catch (Exception e) {
                        check = false;
                        String error = e.toString();
                        Dialog d = new Dialog(getActivity().getApplicationContext());
                        d.setTitle(" error");
                        TextView tv = new TextView(getActivity().getApplicationContext());
                        tv.setText(error);
                        d.setContentView(tv);
                        d.show();
                        System.out.println("HET IS IN DE CATCH!!!!");
                    }
                    Intent apply = new Intent(getContext(),solliciteren.class);
                    String url = "http://vdabservices-cbt.vdab.be/vacaturedetail/1.0.0/"+modelTest.getId();
                    apply.putExtra(TAG_URL, url);
                    apply.putExtra(TAG_NAME, modelTest.getTitel());
                    startActivity(apply);

                }
            });
            if (position % 2 == 0) {
                view.setBackgroundColor(Color.LTGRAY);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent apply = new Intent(getContext(),solliciteren.class);
                    String url = "http://vdabservices-cbt.vdab.be/vacaturedetail/1.0.0/"+modelTest.getId();
                    apply.putExtra(TAG_URL, url);
                    apply.putExtra(TAG_NAME, modelTest.getTitel());
                    //System.out.println("position:  " + position +"Vacature id :"+ modelTest.getId());
                    // startActivity(apply);
                    // String url = "http://vdabservices-cbt.vdab.be/vacaturedetail/1.0.0/"+ modelTest.getId();
                    // System.out.println(url);
                    startActivity(apply);

                }
            });

            return view;
        }
    }
}