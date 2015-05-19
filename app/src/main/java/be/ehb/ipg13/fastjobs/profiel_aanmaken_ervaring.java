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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class profiel_aanmaken_ervaring extends ActionBarActivity {
    Button toevoegen;
    ArrayList<EditText> lijst;
    ArrayList<TextView> lijst_label;
    ArrayList<ImageView> lijst_img;
    RelativeLayout RelLayout;
    int teller = 1;
    Button volgende;
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel_aanmaken_ervaring);
        this.getSupportActionBar().hide();

        final ImageView img = (ImageView) findViewById(R.id.imgView3);
        img.setImageResource(R.drawable.header3);

        lijst_img = new ArrayList<ImageView>();
        lijst = new ArrayList<EditText>();
        lijst_label = new ArrayList<TextView>();

        RelLayout = (RelativeLayout) findViewById(R.id.rell);
        volgende = (Button) findViewById(R.id.btn_volgende_3);
        RelLayout.setBackgroundColor(Color.TRANSPARENT);
        skip = (TextView) findViewById(R.id.lbl_skip_3);

        toevoegen = new Button(this);
        toevoegen.setText("Toevoegen +");
        toevoegen.setTextColor(Color.parseColor("#ff2a8171"));
        toevoegen.setTypeface(null, Typeface.BOLD);

        RelativeLayout.LayoutParams toevoegen_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        toevoegen_par.addRule(RelativeLayout.ALIGN_TOP);
        toevoegen_par.addRule(RelativeLayout.CENTER_HORIZONTAL);

        RelLayout.addView(toevoegen, toevoegen_par);

        toevoegen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!lijst.isEmpty()) {
                    if (lijst.get(lijst.size() - 2).getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Je moet iets aanduiden!", Toast.LENGTH_LONG).show();
                    } else if (lijst.get(lijst.size() - 1).getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Je moet iets aanduiden!", Toast.LENGTH_LONG).show();
                    } else {
                        addd();
                    }
                } else {
                    addd();
                }
            }
        });
/*
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DDD",Toast.LENGTH_SHORT).show();
            }
        });
        */
        volgende.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread threadErvaring = new Thread() {
                    public void run() {
                        try {
                            if (!lijst.isEmpty()) {
                                if (lijst.get(lijst.size() - 1).getText().toString().isEmpty()) {
                                    lijst.remove(lijst.size() - 1);
                                    lijst.remove(lijst.size() - 2);
                                }
                            }
                            if (lijst.size() > 0) {
                                SharedPreferences opslaanErvaring = getSharedPreferences("ervaringen", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = opslaanErvaring.edit();
                                int j = 0;
                                for (int i = 0; i < (lijst.size()); i += 2) {
                                    j++;
                                    editor.putString("bedrijf " + j, lijst.get(i).getText().toString());
                                    editor.putString("functie " + j, lijst.get(i + 1).getText().toString());
                                }
                                editor.putInt("size", j);
                                editor.apply();
                            } else {
                                System.out.println("LIJST ERVARINGEN IS LEEG!!!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                threadErvaring.start();
                Intent i = new Intent("be.ehb.ipg13.fastjobs.vaardigheden");
                startActivity(i);
            }
        });
    }

    public void addd() {
        final EditText var1 = new EditText(this);
        final EditText var2 = new EditText(this);

        final TextView lab1 = new TextView(this);
        final TextView lab2 = new TextView(this);

        lab1.setText("Bedrijf");
        lab2.setText("Functie");

        //var1.setBackgroundColor(Color.parseColor("#ff00a2e8"));
        //var2.setBackgroundColor(Color.parseColor("#ff00a2e8"));

        var1.setBackgroundColor(Color.LTGRAY);
        var2.setBackgroundColor(Color.LTGRAY);

        lab1.setTextColor(Color.parseColor("#ffffffff"));
        lab2.setTextColor(Color.parseColor("#ffffffff"));

        final ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.prul_zwart);

        Resources res = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, res.getDisplayMetrics());
        var1.setMinimumWidth(px);
        var2.setMinimumWidth(px);

        int px1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, res.getDisplayMetrics());
        img.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int jj = lijst_img.indexOf(img);
                        int ii = jj * 2;
                        //Toast.makeText(getApplicationContext(), " = = " + ii, Toast.LENGTH_SHORT).show();

                        RelLayout.removeView(lijst.get(ii));
                        RelLayout.removeView(lijst.get(ii + 1));
                        RelLayout.removeView(lijst_label.get(ii));
                        RelLayout.removeView(lijst_label.get(ii + 1));
                        RelLayout.removeView(lijst_img.get(jj));

                        lijst_img.remove(jj);
                        lijst.remove(ii + 1);
                        lijst.remove(ii);
                        lijst_label.remove(ii + 1);
                        lijst_label.remove(ii);

                        if ((lijst.size()) == ii) {
                            RelativeLayout.LayoutParams toevoegen_par = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                            if (ii != 0) {
                                toevoegen_par.addRule(RelativeLayout.BELOW, lijst.get(ii - 1).getId());
                                toevoegen_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                toevoegen_par.setMargins(0, 0, 0, 25);
                            } else {
                                toevoegen_par.addRule(RelativeLayout.ALIGN_TOP);
                                toevoegen_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                toevoegen_par.setMargins(0, 0, 0, 25);
                            }
                            RelLayout.removeView(toevoegen);
                            RelLayout.addView(toevoegen, toevoegen_par);
                        } else {
                            RelativeLayout.LayoutParams lab_par = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                            if (ii != 0) {
                                lab_par.addRule(RelativeLayout.BELOW, lijst.get(ii - 1).getId());
                                lab_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                lab_par.setMargins(0, 150, 0, 25);
                                int ber = (int) (ii * 2.5);
                                RelLayout.getChildAt(ber).setLayoutParams(lab_par);
                            } else {
                                lab_par.addRule(RelativeLayout.ALIGN_TOP);
                                lab_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                lab_par.setMargins(0, 0, 0, 25);
                                RelLayout.getChildAt(ii).setLayoutParams(lab_par);
                            }
                            View vie = RelLayout.getChildAt(ii);
                            if (vie instanceof ImageView) {
                                Toast.makeText(getApplicationContext(), "image", Toast.LENGTH_SHORT).show();
                            } else if (vie instanceof Spinner) {
                                Toast.makeText(getApplicationContext(), "edit", Toast.LENGTH_SHORT).show();
                            } else if (vie instanceof TextView) {
                                Toast.makeText(getApplicationContext(), "text", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "haha", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
       // toevoegen.setId(1);
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

        RelativeLayout.LayoutParams toevoegen_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams img_par = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        if (lijst_label.size() < 1) {
            lab1_par.addRule(RelativeLayout.ALIGN_TOP);
            lab1_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
            lab1_par.setMargins(0, 0, 0, 5);
        } else {
            lab1_par.addRule(RelativeLayout.BELOW, lijst.get(lijst.size() - 1).getId());
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

        toevoegen_par.addRule(RelativeLayout.BELOW, var2.getId());
        toevoegen_par.addRule(RelativeLayout.CENTER_HORIZONTAL);
        toevoegen_par.setMargins(0, 0, 0, 25);

        img_par.addRule(RelativeLayout.ABOVE, var2.getId());
        img_par.addRule(RelativeLayout.RIGHT_OF, lab2.getId());
        img_par.setMargins(225, 0, 0, 0);
        img_par.width = px1;
        img_par.height = px1;

        lijst.add(var1);
        lijst.add(var2);
        lijst_img.add(img);
        lijst_label.add(lab1);
        lijst_label.add(lab2);
/*
        RelLayout.addView(lab1, lab1_par);
        RelLayout.addView(var1, var1_par);
        RelLayout.addView(lab2, lab2_par);
        RelLayout.addView(var2, var2_par);
        RelLayout.removeView(toevoegen);
        RelLayout.addView(toevoegen, toevoegen_par);
*/
        RelLayout.addView(lijst_label.get(lijst_label.size() - 2), lab1_par);
        RelLayout.addView(lijst.get(lijst.size() - 2), var1_par);
        RelLayout.addView(lijst_img.get(lijst_img.size() - 1), img_par);

        RelLayout.addView(lijst_label.get(lijst_label.size() - 1), lab2_par);
        RelLayout.addView(lijst.get(lijst.size() - 1), var2_par);
        RelLayout.removeView(toevoegen);
        RelLayout.addView(toevoegen, toevoegen_par);

        //RelLayout.setScrollY(10000);
        //RelLayout.setHorizontalGravity(RelativeLayout.ALIGN_PARENT_END);
        // RelLayout.setVerticalGravity(RelativeLayout.CENTER_VERTICAL);
        //RelLayout.setVerticalScrollbarPosition(500);
        // RelLayout.setVerticalScrollbarPosition(View.FOCUS_DOWN);
        // RelLayout.setVerticalGravity(View.FOCUS_DOWN);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profiel_aanmaken_ervaring, menu);
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
