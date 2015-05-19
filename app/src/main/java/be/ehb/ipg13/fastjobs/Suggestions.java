package be.ehb.ipg13.fastjobs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Suggestions extends Fragment {
    View rootview;
    ArrayList<ModelTest> lijstje;
    ArrayList<String> lijstTrefwoorden = new ArrayList<String>();
    Context thisContext;
    Spinner spnrProfielen;
    ArrayAdapter<ModelTest> adapter;
    boolean eersteKeer = true;
    ListView listView;
    TextView lblInternet;
    ProgressBar pr;
    private static String TAG_URL = "url";
    private  static  String TAG_NAME ="naam";
    private boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_suggestions, container, false);

                ProfielSQL profielSQL = new ProfielSQL(getActivity());
                profielSQL.open();
                ArrayList<Profiel_model> profielen = profielSQL.getAll();
                profielSQL.close();

                final String profielNamen[] = new String[profielen.size()];
                for (int v = 0; v < profielen.size(); v++) {
                    profielNamen[v] = profielen.get(v).getNaam();
                }

                spnrProfielen = (Spinner) rootview.findViewById(R.id.spnrProfielen);
                final ListAdapter adapterProfielNamen = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, profielNamen);
                spnrProfielen.setAdapter((android.widget.SpinnerAdapter) adapterProfielNamen);


                spnrProfielen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!eersteKeer) {
                            ProfielXML.gegevensWegSchrijven(getActivity());
                            String res2 = ProfielXML.gegevensOphalen(getActivity(), true, spnrProfielen.getSelectedItem().toString());
                            System.out.println("");
                            System.out.println("");
                            System.out.println(" ================ DE RES IS =====--->> " + res2);
                            System.out.println("");
                            System.out.println("");
                            APIHelper apiHelper2 = new APIHelper(res2, 0, 25);
                            try {
                                lijstje = XMLParser.deParser((String) apiHelper2.execute().get());
                                if (lijstje != null) {
                                    System.out.println("IK ZIT IN DE SPINNER ONCLICK LISTENER!!!! DE SIZE IS == " + lijstje.size());
                                } else {
                                    System.out.println("IK ZIT IN DE SPINNER ONCLICK LISTENER!!! LIJSTJE IS NULL");
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            //if (lijstje != null && lijstje.size())
                            listViewVullen();
                            //adapter.notifyDataSetChanged();
                        } else {
                            eersteKeer = false;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        System.out.println("IK ZIT IN DE SPINNER ONNOTHING SELECTED LISTENER!!!!");
                    }
                });
                if (profielen != null) {
                    if (profielen.size() != 0) {
                        ProfielXML.gegevensWegSchrijven(getActivity());
                        String res = ProfielXML.gegevensOphalen(getActivity(), true, profielen.get(0).getNaam());
                        String abc = "";
                        //Hier zal je de XML sturen naar de APIHelper en de resultaat in de XMLParser Steken
                        //De XML Parser zal een ArrayList Terug Sturen
                        APIHelper apiHelper = new APIHelper(res, 0, 25);
                        try {
                            lijstje = XMLParser.deParser((String) apiHelper.execute().get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        listViewVullen();
                    }
                }
        return rootview;
    }

    private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void listViewVullen() {
        System.out.println("RESULTATEN WORDEN HIER GETOOND: ");
        if (lijstje != null) {
            System.out.println(" -> ");
            for (int h = 0; h < lijstje.size(); h++) {
                System.out.println(" ===========-------------->>>>  DE LIJSTJE IS ===========-------------->>>> " + lijstje.get(h).getTitel());
            }
            System.out.println(" -> ");
            adapter = new myListAdapter();
            listView = (ListView) rootview.findViewById(R.id.myListView);
            listView.setAdapter(adapter);
        }
    }

    public class myListAdapter extends ArrayAdapter<ModelTest> {
        public myListAdapter() {
            super(getActivity(), R.layout.item_view, lijstje);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            view = getActivity().getLayoutInflater().inflate(R.layout.item_view, parent, false);
            if (lijstje != null) {
                if (lijstje.size() > 0) {
                    final ModelTest modelTest = lijstje.get(position);

                    TextView vacTitel = (TextView) view.findViewById(R.id.lbl_titel);
                    vacTitel.setText(modelTest.getTitel());
/*
                    TextView vacId = (TextView) view.findViewById(R.id.lbl_bij_tekst);
                    vacId.setText(modelTest.getId());
*/
                    TextView vacGemeente = (TextView) view.findViewById(R.id.lbl_in_tekst);
                    vacGemeente.setText(modelTest.getGemeente());
                    //boolean flag = false;

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
                                SaveJobSQL entry = new SaveJobSQL(getActivity().getApplicationContext());
                                entry.open();
                                entry.createEntry(entryID, entryTitle, entryGemeente);
                                entry.close();
                                System.out.println("HET IS IN DE TRY!!!!");
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
                            System.out.println("DE IDddddddddddddddd IS ==> " + modelTest.getId());

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
                } else {
                    System.out.println("=> DE LIJST SIZE IS 0!!!");
                }
            } else {
                System.out.println("=> DE LIJST IS NULLLLL!!!");
            }
            return view;
        }
    }
}