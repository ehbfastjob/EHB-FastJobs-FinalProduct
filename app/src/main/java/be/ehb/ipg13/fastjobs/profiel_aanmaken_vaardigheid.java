package be.ehb.ipg13.fastjobs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class profiel_aanmaken_vaardigheid extends ActionBarActivity {

    Button toevoegen_taal;
    Button toevoegen_pc;
    ArrayList<EditText> lijst_taal;
    ArrayList<Spinner> lijst_taal_niveau;

    ArrayList<EditText> lijst_pc;
    ArrayList<Spinner> lijst_pc_niveau;

    ArrayList<TextView> lijst_label_taal;
    ArrayList<TextView> lijst_label_pc;

    ArrayList<ImageView> lijst_img1;
    ArrayList<ImageView> lijst_img2;

    RelativeLayout RelLayout;
    int teller = 2;
    Button volgende;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel_aanmaken_vaardigheid);
        this.getSupportActionBar().hide();

        final ImageView img = (ImageView) findViewById(R.id.imgView4);
        img.setImageResource(R.drawable.header4);

        lijst_taal = new ArrayList<EditText>();
        lijst_taal_niveau = new ArrayList<Spinner>();
        lijst_pc = new ArrayList<EditText>();
        lijst_pc_niveau = new ArrayList<Spinner>();

        lijst_label_taal = new ArrayList<TextView>();
        lijst_label_pc = new ArrayList<TextView>();

        lijst_img1 = new ArrayList<ImageView>();
        lijst_img2 = new ArrayList<ImageView>();

        RelLayout = (RelativeLayout) findViewById(R.id.rell);
        volgende = (Button) findViewById(R.id.btn_volgende_4);
        RelLayout.setBackgroundColor(Color.TRANSPARENT);
        skip = (TextView) findViewById(R.id.lbl_skip_4);

        toevoegen_taal = new Button(this);
        toevoegen_taal.setText("Taalvaardigheid toevoegen +");
        toevoegen_taal.setTextColor(Color.parseColor("#ff2a8171"));
        toevoegen_taal.setTypeface(null, Typeface.BOLD);
        toevoegen_taal.setId(1);

        toevoegen_pc = new Button(this);
        toevoegen_pc.setText("Computervaardigheid Toevoegen +");
        toevoegen_pc.setTextColor(Color.parseColor("#ff2a8171"));
        toevoegen_pc.setTypeface(null, Typeface.BOLD);
        toevoegen_pc.setId(2);

        RelativeLayout.LayoutParams toevoegen_par_taal = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        toevoegen_par_taal.addRule(RelativeLayout.ALIGN_TOP);
        toevoegen_par_taal.addRule(RelativeLayout.CENTER_HORIZONTAL);

        RelLayout.addView(toevoegen_taal, toevoegen_par_taal);

        RelativeLayout.LayoutParams toevoegen_par_pc = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        toevoegen_par_pc.addRule(RelativeLayout.BELOW, toevoegen_taal.getId());
        toevoegen_par_pc.addRule(RelativeLayout.CENTER_HORIZONTAL);

        RelLayout.addView(toevoegen_pc, toevoegen_par_pc);

        toevoegen_taal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!lijst_taal.isEmpty()) {
                    if (lijst_taal.get(lijst_taal.size() - 1).getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Je moet iets aanduiden!", Toast.LENGTH_LONG).show();
                    } else if (lijst_taal_niveau.get(lijst_taal_niveau.size() - 1).getSelectedItem().equals("")) {
                        Toast.makeText(getApplicationContext(), "Je moet iets aanduiden!", Toast.LENGTH_LONG).show();
                    } else {
                        addd();
                    }
                } else {
                    addd();
                }
            }
        });

        toevoegen_pc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!lijst_pc.isEmpty()) {
                    if (lijst_pc.get(lijst_pc.size() - 1).getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Je moet iets aanduiden!", Toast.LENGTH_LONG).show();
                    } else if (lijst_pc_niveau.get(lijst_pc_niveau.size() - 1).getSelectedItem().equals("")) {
                        Toast.makeText(getApplicationContext(), "Je moet iets aanduiden!", Toast.LENGTH_LONG).show();
                    } else {
                        addd1();
                    }
                } else {
                    addd1();
                }
            }
        });

        volgende.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread threadVaardigheden = new Thread() {
                    public void run() {
                        try {
                            if (!lijst_taal.isEmpty()) {
                                if (lijst_taal.get(lijst_taal.size() - 1).getText().toString().isEmpty()) {
                                    lijst_taal.remove(lijst_taal.size() - 1);
                                    lijst_taal_niveau.remove(lijst_taal_niveau.size() - 2);
                                }
                            }
                            if (!lijst_pc.isEmpty()) {
                                if (lijst_pc.get(lijst_pc.size() - 1).getText().toString().isEmpty()) {
                                    lijst_pc.remove(lijst_pc.size() - 1);
                                    lijst_pc_niveau.remove(lijst_pc_niveau.size() - 2);
                                }
                            }
                            if (lijst_pc.size() > 0 || lijst_taal.size() > 0) {
                                SharedPreferences opslaanErvaring = getSharedPreferences("vaardigheden", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = opslaanErvaring.edit();

                                if (lijst_taal.size() > 0) {
                                    for (int i = 0; i < (lijst_taal.size()); i++) {
                                        editor.putString("taal " + i, lijst_taal.get(i).getText().toString());
                                    }
                                    for (int i = 0; i < (lijst_taal_niveau.size()); i++) {
                                        editor.putString("taal niveau " + i, lijst_taal_niveau.get(i).getSelectedItem().toString());
                                    }
                                    editor.putInt("taal size", lijst_taal.size());
                                } else {
                                    System.out.println("LIJST TALEN IS LEEG!!!");
                                }
                                if (lijst_taal.size() > 0) {
                                    for (int i = 0; i < (lijst_pc.size()); i++) {
                                        editor.putString("computerkennis " + i, lijst_pc.get(i).getText().toString());
                                    }
                                    for (int i = 0; i < (lijst_pc_niveau.size()); i++) {
                                        editor.putString("computerkennis niveau " + i, lijst_pc_niveau.get(i).getSelectedItem().toString());
                                    }
                                    editor.putInt("computerkennis size", lijst_pc.size());
                                } else {
                                    System.out.println("LIJST COMPUTERKENNIS IS LEEG!!!");
                                }
                                editor.apply();
                            } else {
                                System.out.println("LIJST OPLEIDINGEN IS LEEG!!!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                threadVaardigheden.start();
                Intent i = new Intent("be.ehb.ipg13.fastjobs.jobvoorkeur");
                startActivity(i);
            }
        });
    }

    public void addd() {
        String[] niveaus = {"", "Basis", "Matig", "Goed"};
        ListAdapter adapterNiveaus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, niveaus);

        final EditText var1 = new EditText(this);
        final Spinner var2 = new Spinner(this);

        final TextView lab1 = new TextView(this);
        final TextView lab2 = new TextView(this);

        final ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.prul_zwart);

        lab1.setText("Taal");
        lab2.setText("Niveau");

        var1.setBackgroundColor(Color.parseColor("#ff00a2e8"));
        var2.setBackgroundColor(Color.parseColor("#ff00a2e8"));

        var2.setAdapter((android.widget.SpinnerAdapter) adapterNiveaus);

        var1.setBackgroundColor(Color.LTGRAY);
        var2.setBackgroundColor(Color.LTGRAY);

        lab1.setTextColor(Color.parseColor("#ffffffff"));
        lab2.setTextColor(Color.parseColor("#ffffffff"));

        Resources res = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, res.getDisplayMetrics());
        var1.setMinimumWidth(px);
        var2.setMinimumWidth(px);

        int px1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, res.getDisplayMetrics());
        img.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int jj = lijst_img1.indexOf(img);
                        int ii = jj * 2;

                        RelLayout.removeView(lijst_taal.get(jj));
                        RelLayout.removeView(lijst_taal_niveau.get(jj));
                        RelLayout.removeView(lijst_label_taal.get(ii));
                        RelLayout.removeView(lijst_label_taal.get(ii + 1));
                        RelLayout.removeView(lijst_img1.get(jj));

                        lijst_img1.remove(jj);
                        lijst_taal.remove(jj);
                        lijst_taal_niveau.remove(jj);
                        lijst_label_taal.remove(ii + 1);
                        lijst_label_taal.remove(ii);

                        if ((lijst_label_taal.size()) == ii) {
                            RelativeLayout.LayoutParams toevoegen_par = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                             if (ii != 0) {
                                toevoegen_par.addRule(RelativeLayout.BELOW, lijst_taal_niveau.get(jj - 1).getId());
                                toevoegen_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                toevoegen_par.setMargins(0, 0, 0, 25);
                            } else {
                                toevoegen_par.addRule(RelativeLayout.ALIGN_TOP);
                                toevoegen_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                toevoegen_par.setMargins(0, 0, 0, 25);
                            }
                            RelLayout.removeView(toevoegen_taal);
                            RelLayout.addView(toevoegen_taal, toevoegen_par);
                        } else {
                            RelativeLayout.LayoutParams lab_par = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                             if (ii != 0) {
                                lab_par.addRule(RelativeLayout.BELOW, lijst_taal_niveau.get(jj - 1).getId());
                                lab_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                lab_par.setMargins(0, 150, 0, 25);
                                int ber = (int) (ii * 2.5);
                                 RelLayout.updateViewLayout(lijst_label_taal.get(jj * 2), lab_par);
                            } else {
                                lab_par.addRule(RelativeLayout.ALIGN_TOP);
                                lab_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                lab_par.setMargins(0, 0, 0, 25);
                                RelLayout.updateViewLayout(lijst_label_taal.get(jj * 2), lab_par);
                            }
                        }
                    }
                }
        );
     //   toevoegen_taal.setId(1);
     //   toevoegen_pc.setId(2);
        lab1.setId(++teller);
        var1.setId(++teller);
        lab2.setId(++teller);
        var2.setId(++teller);

        RelativeLayout.LayoutParams lab1_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams var1_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams lab2_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams var2_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams toevoegen_par_taal = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams toevoegen_par_pc = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams img_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        if (lijst_label_taal.size() < 1) {
            lab1_par.addRule(RelativeLayout.ALIGN_TOP);
            lab1_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
            lab1_par.setMargins(0, 0, 0, 5);
        } else {
            lab1_par.addRule(RelativeLayout.BELOW, lijst_taal_niveau.get(lijst_taal_niveau.size() - 1).getId());
            lab1_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
            lab1_par.setMargins(0, 150, 0, 5);
        }
        var1_par.addRule(RelativeLayout.BELOW, lab1.getId());
        var1_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
        var1_par.setMargins(0, 0, 0, 5);

        lab2_par.addRule(RelativeLayout.BELOW, var1.getId());
        lab2_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lab2_par.setMargins(0, 0, 0, 5);

        var2_par.addRule(RelativeLayout.BELOW, lab2.getId());
        var2_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
        var2_par.setMargins(0, 0, 0, 5);

        toevoegen_par_taal.addRule(RelativeLayout.BELOW, var2.getId());
        toevoegen_par_taal.addRule(RelativeLayout.CENTER_HORIZONTAL);
        toevoegen_par_taal.setMargins(0, 0, 0, 25);
        if (lijst_pc.size() < 1) {
            toevoegen_par_pc.addRule(RelativeLayout.BELOW, toevoegen_taal.getId());
            toevoegen_par_pc.addRule(RelativeLayout.CENTER_HORIZONTAL);
            toevoegen_par_pc.setMargins(0, 0, 0, 25);
        }
        img_par.addRule(RelativeLayout.ABOVE, var2.getId());
        img_par.addRule(RelativeLayout.RIGHT_OF, lab2.getId());
        img_par.setMargins(225, 0, 0, 0);
        img_par.width = px1;
        img_par.height = px1;

        lijst_taal.add(var1);
        lijst_taal_niveau.add(var2);
        lijst_img1.add(img);

        lijst_label_taal.add(lab1);
        lijst_label_taal.add(lab2);

        RelLayout.addView(lab1, lab1_par);
        RelLayout.addView(var1, var1_par);
        RelLayout.addView(lab2, lab2_par);
        RelLayout.addView(var2, var2_par);
        RelLayout.addView(lijst_img1.get(lijst_img1.size() - 1), img_par);
        if (lijst_pc.size() < 1) {
            RelLayout.removeView(toevoegen_pc);
        }
        RelLayout.removeView(toevoegen_taal);
        RelLayout.addView(toevoegen_taal, toevoegen_par_taal);
        if (lijst_pc.size() < 1) {
            RelLayout.addView(toevoegen_pc, toevoegen_par_pc);
        }
    }


    public void addd1() {
        String[] niveaus = {"", "Basis", "Matig", "Goed"};
        ListAdapter adapterNiveaus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, niveaus);

        final EditText var1 = new EditText(this);
        final Spinner var2 = new Spinner(this);

        final TextView lab1 = new TextView(this);
        final TextView lab2 = new TextView(this);

        final ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.prul_zwart);

        lab1.setText("Computerkennis");
        lab2.setText("Niveau");

        var1.setBackgroundColor(Color.parseColor("#ff00a2e8"));
        var2.setBackgroundColor(Color.parseColor("#ff00a2e8"));

        var2.setAdapter((android.widget.SpinnerAdapter) adapterNiveaus);

        var1.setBackgroundColor(Color.LTGRAY);
        var2.setBackgroundColor(Color.LTGRAY);

        lab1.setTextColor(Color.parseColor("#ffffffff"));
        lab2.setTextColor(Color.parseColor("#ffffffff"));

        Resources res = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, res.getDisplayMetrics());
        var1.setMinimumWidth(px);
        var2.setMinimumWidth(px);

        int px1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, res.getDisplayMetrics());
        img.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int jj = lijst_img2.indexOf(img);
                        int ii = jj * 2;

                        RelLayout.removeView(lijst_pc.get(jj));
                        RelLayout.removeView(lijst_pc_niveau.get(jj));
                        RelLayout.removeView(lijst_label_pc.get(ii));
                        RelLayout.removeView(lijst_label_pc.get(ii + 1));
                        RelLayout.removeView(lijst_img2.get(jj));

                        lijst_img2.remove(jj);
                        lijst_pc.remove(jj);
                        lijst_pc_niveau.remove(jj);
                        lijst_label_pc.remove(ii + 1);
                        lijst_label_pc.remove(ii);

                        if ((lijst_label_pc.size()) == ii) {
                            RelativeLayout.LayoutParams toevoegen_par = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                            if (ii != 0) {
                                toevoegen_par.addRule(RelativeLayout.BELOW, lijst_pc_niveau.get(jj - 1).getId());
                                toevoegen_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                toevoegen_par.setMargins(0, 0, 0, 25);
                            } else {
                                toevoegen_par.addRule(RelativeLayout.BELOW, toevoegen_taal.getId());
                                toevoegen_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                toevoegen_par.setMargins(0, 0, 0, 25);
                            }
                            RelLayout.removeView(toevoegen_pc);
                            RelLayout.addView(toevoegen_pc, toevoegen_par);
                        } else {
                            RelativeLayout.LayoutParams lab_par = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                            int kk = lijst_taal_niveau.size() + lijst_taal.size() + lijst_label_taal.size() + lijst_img2.size() + 0;
                            if (ii != 0) {
                                 lab_par.addRule(RelativeLayout.BELOW, lijst_pc_niveau.get(jj - 1).getId());
                                lab_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                lab_par.setMargins(0, 150, 0, 25);
                                Toast.makeText(getApplicationContext(), "EERSTE!!!!!", Toast.LENGTH_LONG).show();
                                int ber = (int) (ii * 2.5 + kk + jj - 1);
                                RelLayout.updateViewLayout(lijst_label_pc.get(jj * 2), lab_par);
                            } else {
                                lab_par.addRule(RelativeLayout.BELOW, toevoegen_taal.getId());
                                lab_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                lab_par.setMargins(0, 0, 0, 25);

                                int ber = ii + kk + jj + 1;
                                 RelLayout.updateViewLayout(lijst_label_pc.get(jj * 2), lab_par);
                            }
                        }
                    }
                }
        );
       // toevoegen_taal.setId(1);
      //  toevoegen_pc.setId(2);
        lab1.setId(++teller);
        var1.setId(++teller);
        lab2.setId(++teller);
        var2.setId(++teller);

        RelativeLayout.LayoutParams lab1_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams var1_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams lab2_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams var2_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams toevoegen_par_taal = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams toevoegen_par_pc = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams img_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        if (lijst_label_pc.size() < 1) {
            lab1_par.addRule(RelativeLayout.BELOW, toevoegen_taal.getId());
            lab1_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
            lab1_par.setMargins(0, 0, 0, 5);
        } else {
            lab1_par.addRule(RelativeLayout.BELOW, lijst_pc_niveau.get(lijst_pc_niveau.size() - 1).getId());
            lab1_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
            lab1_par.setMargins(0, 150, 0, 5);
        }
        var1_par.addRule(RelativeLayout.BELOW, lab1.getId());
        var1_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
        var1_par.setMargins(0, 0, 0, 5);

        lab2_par.addRule(RelativeLayout.BELOW, var1.getId());
        lab2_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lab2_par.setMargins(0, 0, 0, 5);

        var2_par.addRule(RelativeLayout.BELOW, lab2.getId());
        var2_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
        var2_par.setMargins(0, 0, 0, 5);

        toevoegen_par_pc.addRule(RelativeLayout.BELOW, var2.getId());
        toevoegen_par_pc.addRule(RelativeLayout.CENTER_HORIZONTAL);
        toevoegen_par_pc.setMargins(0, 0, 0, 25);
/*
        toevoegen_par_pc.addRule(RelativeLayout.BELOW, toevoegen_taal.getId());
        toevoegen_par_pc.addRule(RelativeLayout.CENTER_HORIZONTAL);
        toevoegen_par_pc.setMargins(0, 0, 0, 25);
*/

        img_par.addRule(RelativeLayout.ABOVE, var2.getId());
        img_par.addRule(RelativeLayout.RIGHT_OF, lab2.getId());
        img_par.setMargins(225, 0, 0, 0);
        img_par.width = px1;
        img_par.height = px1;

        lijst_pc.add(var1);
        lijst_pc_niveau.add(var2);
        lijst_img2.add(img);

        lijst_label_pc.add(lab1);
        lijst_label_pc.add(lab2);

        RelLayout.addView(lab1, lab1_par);
        RelLayout.addView(var1, var1_par);
        RelLayout.addView(lab2, lab2_par);
        RelLayout.addView(var2, var2_par);
        RelLayout.addView(lijst_img2.get(lijst_img2.size() - 1), img_par);
        RelLayout.removeView(toevoegen_pc);
        //RelLayout.removeView(toevoegen_taal);
        //RelLayout.addView(toevoegen_taal, toevoegen_par_taal);
        RelLayout.addView(toevoegen_pc, toevoegen_par_pc);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profiel_aanmaken_vaardigheid, menu);
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
