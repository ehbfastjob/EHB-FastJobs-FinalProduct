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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Profile extends Fragment {
    View rootview;
    ListView listViewProfielen;
    Button toevoegen;
    ArrayList<Profiel_model> lijstje;
    ArrayAdapter<Profiel_model> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_profile, container, false);
        toevoegen = (Button) rootview.findViewById(R.id.btn_profiel_toevoegen);

        // PROFIELEN UIT DE DATABASE HALEN OM DIE DAARNA IN DE LISTVIEW TE STEKEN
        ProfielSQL profielOphalen = new ProfielSQL(getActivity().getApplicationContext());
        profielOphalen.open();
        lijstje = profielOphalen.getAll();
        profielOphalen.close();
        System.out.println("HET IS IN DE TRY!!!!");

        if (lijstje != null) {
            adapter = new myListAdapter();
            ListView listView = (ListView) rootview.findViewById(R.id.myListview1);
            listView.setAdapter(adapter);
        }
        toevoegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("be.ehb.ipg13.fastjobs.jobvoorkeur");
                startActivity(i);
            }
        });
        return rootview;
    }

    public class myListAdapter extends ArrayAdapter<Profiel_model> {
        public myListAdapter() {
            super(getActivity(), R.layout.item_view_profile, lijstje);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            view = getActivity().getLayoutInflater().inflate(R.layout.item_view_profile, parent, false);
            final Profiel_model profielModel = lijstje.get(position);

            TextView proNaam = (TextView) view.findViewById(R.id.lbl_naam_profiel);
            proNaam.setText(profielModel.getNaam());

            ImageButton btnWijzig = (ImageButton) view.findViewById(R.id.btn_wijzig);
            btnWijzig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent i = new Intent("be.ehb.ipg13.fastjobs.wijzig_jobvoorkeuren");
                    Intent i = new Intent(getActivity(), wijzig_jobvoorkeuren.class);
                    i.putExtra("deProfielNaam", profielModel.getNaam().toString());
                    startActivity(i);
                }
            });

            ImageButton btnVerwijder = (ImageButton) view.findViewById(R.id.btn_verwijder);
            btnVerwijder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfielSQL proSQL = new ProfielSQL(getActivity());
                    proSQL.open();
                    proSQL.delete(profielModel.getNaam().toString(),profielModel.getProfielID());
                    proSQL.close();
                    lijstje.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
            if (position % 2 == 1) {
                view.setBackgroundColor(Color.parseColor("#ff2a8171"));
                btnWijzig.setImageResource(R.drawable.icon_wijzigen_groen);
                btnWijzig.setBackgroundColor(Color.parseColor("#ff2a8171"));
                btnVerwijder.setImageResource(R.drawable.prullenbak_groen);
                btnVerwijder.setBackgroundColor(Color.parseColor("#ff2a8171"));
            }else{
                btnWijzig.setImageResource(R.drawable.icon_wijzigen);
                btnWijzig.setBackgroundColor(Color.WHITE);
                btnVerwijder.setImageResource(R.drawable.prullenbak_wit);
                btnVerwijder.setBackgroundColor(Color.WHITE);
                //btnVerwijder.setMinimumHeight(40);
                //btnVerwijder.setMaxWidth(40);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("DE POSITITE IS ==> " + position);
                    Intent i = new Intent("be.ehb.ipg13.fastjobs.proBekijken");
                    i.putExtra("deProfielNaam", profielModel.getNaam().toString());
                    startActivity(i);
                    /*
                    Intent i = new Intent("be.ehb.ipg13.fastjobs.opleidingen");
                    startActivity(i);
                    */
                }
            });
            return view;
        }
    }
}