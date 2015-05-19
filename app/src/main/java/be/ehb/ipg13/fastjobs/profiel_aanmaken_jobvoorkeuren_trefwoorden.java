package be.ehb.ipg13.fastjobs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class profiel_aanmaken_jobvoorkeuren_trefwoorden extends ActionBarActivity {

    Button opslaan, toevoegen;
    ArrayList<String> lijstTrefwoorden;
    EditText txt_trefwoord;
    GridLayout layout1, layout2;

    ArrayList<TextView> lijst, lijst_2;
    TextView ab, ab2;
    int m = 1, k = 0, k2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel_aanmaken_jobvoorkeuren_trefwoorden);
        this.getSupportActionBar().hide();

        lijstTrefwoorden = new ArrayList<String>();
        final ImageView img = (ImageView) findViewById(R.id.imgView6);
        img.setImageResource(R.drawable.balls1);
        lijst = new ArrayList<TextView>();
        lijst_2 = new ArrayList<TextView>();

        toevoegen = (Button) findViewById(R.id.btn_add);
        layout1 = (GridLayout) findViewById(R.id.layout_1);
        layout2 = (GridLayout) findViewById(R.id.layout_2);
        opslaan = (Button) findViewById(R.id.btn_opslaan);
        txt_trefwoord = (EditText) findViewById(R.id.txt_trefwoord);

        txt_trefwoord.setBackgroundColor(Color.parseColor("#ff2a8171"));

        toevoegenAanLayout("Exel");
        toevoegenAanLayout("Microsoft");
        toevoegenAanLayout("Softskills");
        toevoegenAanLayout("Team player");
        toevoegenAanLayout("vmware");
        toevoegenAanLayout("SQL");


        toevoegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aa(null);
                m++;
            }
        });
        opslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread threadTrefwoorden = new Thread() {
                    public void run() {
                        ProfielSQL entry = new ProfielSQL(getApplicationContext());
                        entry.open();
                        entry.createEntry(getIntent().getExtras().getString("profielNaam").toString(),
                                getIntent().getExtras().getString("tijdsregeling").toString(),
                                getIntent().getExtras().getString("soortjob").toString(),
                                getIntent().getExtras().getString("gemeente").toString(),
                                getIntent().getExtras().getString("aantalKM").toString(),
                                lijstTrefwoorden);
                        entry.close();

                        try {/*
                            SharedPreferences opslaanTrefwoorden = getSharedPreferences("trefwoorden", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = opslaanTrefwoorden.edit();
                            editor.putInt("count", lijstTrefwoorden.size());
                            for (int i = 0; i < (lijstTrefwoorden.size()); i++) {
                                editor.putString("trefwoord " + i, lijstTrefwoorden.get(i).toString());
                                System.out.println("DE LIJST VAN DE TREFWOORDEN: " + i + " : " + lijstTrefwoorden.get(i).toString());
                            }
                            editor.apply();

                            SharedPreferences opslaanIDs = getSharedPreferences("ids", Context.MODE_PRIVATE);
                            SharedPreferences.Editor ed = opslaanIDs.edit();
                            ed.putInt("count", 0);
                            ed.apply();
*/
                            SharedPreferences pref = getSharedPreferences("EersteKeerPref", MODE_PRIVATE);
                            String tag = tag = pref.getString("EersteKeer", "");
                            if(tag.equals("JA")) {
                                Intent intent = new Intent(getApplicationContext(), NotService.class);
                                PendingIntent sender = PendingIntent.getService(getApplicationContext(), 0, intent, 0);

                                AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                                am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 10000, 60000, sender);

                                SharedPreferences opslaanEersteKeer = getSharedPreferences("EersteKeerPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = opslaanEersteKeer.edit();
                                editor.putString("EersteKeer", "NEE");
                                editor.apply();
                            }
                            ProfielXML.gegevensWegSchrijven(profiel_aanmaken_jobvoorkeuren_trefwoorden.this);
                            String res = ProfielXML.gegevensOphalen(profiel_aanmaken_jobvoorkeuren_trefwoorden.this, true, getIntent().getExtras().getString("profielNaam").toString());
                            System.out.println("RES : = - = : " + res);
                            Intent i = new Intent("be.ehb.ipg13.fastjobs.homescreen");
                            startActivity(i);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (!(lijstTrefwoorden.size() == 0)) {
                    threadTrefwoorden.start();
                } else {
                    Toast.makeText(profiel_aanmaken_jobvoorkeuren_trefwoorden.this, "Je moet minimum 1 trefwoord toevoegen!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    public void toevoegenAanLayout(final String tag) {
        ab2 = new TextView(this);
        ab2.setText("#" + tag.toString() + " ");
        ab2.setId(m);
        int aaaa = ab2.getText().toString().length();

        lijst_2.add(ab2);
        ab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean zitErIn = true;
                Toast.makeText(getApplicationContext(), "KLIK", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < lijst.size(); i++) {
                    if (lijst.get(i).getText().toString().equals("#" + tag.toString() + " ")) {
                        zitErIn = false;
                    }
                }
                for (int i = 0; i < lijst_2.size(); i++) {
                    if (lijst_2.get(i).getText().toString().equals("#" + tag.toString() + " ")) {
                        TextView view = (TextView) v;
                        view.setTextColor(Color.WHITE);
                        lijst_2.get(i).setTypeface(null, Typeface.BOLD);
                    }
                }
                if (zitErIn) {
                    aa(tag.toString());
                }
            }
        });
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();

        if (aaaa > 40) {
            param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 3);
        } else if (aaaa > 36) {
            param.columnSpec = GridLayout.spec(9);
        } else if (aaaa > 32) {
            param.columnSpec = GridLayout.spec(8);
        } else if (aaaa > 28) {
            param.columnSpec = GridLayout.spec(7);
        } else if (aaaa > 24) {
            if (k2 + 13 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 13);
            k2 += 13;
        } else if (aaaa > 22) {
            if (k2 + 12 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 12);
            k2 += 12;
        } else if (aaaa > 20) {
            if (k2 + 11 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 11);
            k2 += 11;
        } else if (aaaa > 18) {
            if (k2 + 10 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 10);
            k2 += 10;
        } else if (aaaa > 16) {
            if (k2 + 9 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 9);
            k2 += 9;
        } else if (aaaa > 14) {
            if (k2 + 8 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 8);
            k2 += 8;
        } else if (aaaa > 12) {
            if (k2 + 7 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 7);
            k2 += 7;
        } else if (aaaa > 10) {
            if (k2 + 6 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 6);
            k2 += 6;
        } else if (aaaa > 8) {
            if (k2 + 5 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 5);
            k2 += 5;
        } else if (aaaa > 6) {
            if (k2 + 4 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 4);
            k2 += 4;
        } else if (aaaa > 4) {
            if (k2 + 3 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 3);
            k2 += 3;
        } else if (aaaa > 2) {
            if (k2 + 2 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k2, 2);
            k2 += 2;
        } else {
            if (k2 > 19) {
                k2 = 0;
            }
            param.columnSpec = GridLayout.spec(k, 1);
            k2 += 1;
        }
        ab2.setTextSize(18);
        layout1.addView(ab2, param);

    }

    public void aa(String toev) {
        ab = new TextView(this);
        if (toev == null) {
            ab.setText("#" + txt_trefwoord.getText() + " ");
            lijstTrefwoorden.add(txt_trefwoord.getText().toString());
        } else {
            ab.setText("#" + toev.toString() + " ");
            lijstTrefwoorden.add(toev.toString());
        }
        ab.setId(m);
        int aaaa = ab.getText().toString().length();
        //ab.setWidth(25*aaaa);
        ab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "KLIK", Toast.LENGTH_SHORT).show();
                layout2.removeView(v);
                TextView view = (TextView) v;
                String ddd = view.getText().toString();
                ddd = ddd.substring(1);
                ddd = ddd.trim();
                for (int i = 0; i < lijstTrefwoorden.size(); i++) {
                    if (lijstTrefwoorden.get(i).toString().equals(ddd.toString())) {
                        lijstTrefwoorden.remove(i);
                    }
                }
                lijst.remove(view);
                for (int i = 0; i < lijst_2.size(); i++) {
                    if (lijst_2.get(i).getText().toString().equals(view.getText().toString())) {
                        lijst_2.get(i).setTextColor(Color.BLACK);
                        lijst_2.get(i).setTypeface(null, Typeface.NORMAL);
                    }
                }
            }
        });
        lijst.add(ab);
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();

        if (aaaa > 40) {
            k = 0;
            param.columnSpec = GridLayout.spec(k, 20);
            k += 20;
        } else if (aaaa > 38) {
            if (k + 19 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 19);
            k += 19;
        } else if (aaaa > 36) {
            if (k + 18 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 18);
            k += 18;
        } else if (aaaa > 34) {
            if (k + 17 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 17);
            k += 17;
        } else if (aaaa > 32) {
            if (k + 16 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 16);
            k += 16;
        } else if (aaaa > 30) {
            if (k + 15 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 15);
            k += 15;
        } else if (aaaa > 28) {
            if (k + 14 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 14);
            k += 14;
        } else if (aaaa > 24) {
            if (k + 13 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 13);
            k += 13;
        } else if (aaaa > 22) {
            if (k + 12 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 12);
            k += 12;
        } else if (aaaa > 20) {
            if (k + 11 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 11);
            k += 11;
        } else if (aaaa > 18) {
            if (k + 10 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 10);
            k += 10;
        } else if (aaaa > 16) {
            if (k + 9 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 9);
            k += 9;
        } else if (aaaa > 14) {
            if (k + 8 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 8);
            k += 8;
        } else if (aaaa > 12) {
            if (k + 7 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 7);
            k += 7;
        } else if (aaaa > 10) {
            if (k + 6 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 6);
            k += 6;
        } else if (aaaa > 8) {
            if (k + 5 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 5);
            k += 5;
        } else if (aaaa > 6) {
            if (k + 4 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 4);
            k += 4;
        } else if (aaaa > 4) {
            if (k + 3 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 3);
            k += 3;
        } else if (aaaa > 2) {
            if (k + 2 > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 2);
            k += 2;
        } else {
            if (k > 19) {
                k = 0;
            }
            param.columnSpec = GridLayout.spec(k, 1);
            k += 1;
        }
        ab.setTextSize(17);
        layout2.addView(ab, param);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profiel_aanmaken_jobvoorkeuren_trefwoorden, menu);
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
