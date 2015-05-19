package be.ehb.ipg13.fastjobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Tips extends Fragment implements View.OnClickListener  {
    View rootview;
    Button voorButton, naButton , tijdensButton;
    String url;
    String TAG = "Jermo";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_tips, container, false);
            Log.i(TAG, "On create ");
            voorButton = (Button)rootview.findViewById(R.id.voorbutton);
            tijdensButton = (Button)rootview.findViewById(R.id.tijdensbutton);
            naButton = (Button)rootview.findViewById(R.id.nabutton);
            Log.i(TAG, "na initailisatie ");
            voorButton.setOnClickListener( this);
            tijdensButton.setOnClickListener(this);
            naButton.setOnClickListener(this);
            return rootview;

        }



        @Override
        public void onClick(View view) {
            Log.i(TAG, "ID OF ON CLICK :  " + view.getId());
            switch (view.getId()) {

                case R.id.voorbutton:
                    Log.i(TAG, "voorbutton ");
                    Intent intentVoor = new Intent("be.ehb.ipg13.fastjobs.tipdetail");
                    url = "http://www.vdab.be//werkinzicht/gesprekvoor.shtml";
                    intentVoor.putExtra("URL", url);
                    startActivity(intentVoor);
                    break;
                case R.id.tijdensbutton:
                    Log.i(TAG, "tijdensbutton ");
                    Intent intenttijdens = new Intent("be.ehb.ipg13.fastjobs.tipdetail");
                    url = "http://www.vdab.be//werkinzicht/gesprektijdens.shtml";
                    intenttijdens.putExtra("URL", url);
                    startActivity(intenttijdens);
                    break;
                case R.id.nabutton:
                    Log.i(TAG, "nabutton ");
                    Intent intentNa = new Intent("be.ehb.ipg13.fastjobs.tipdetail");
                    url = "http://www.vdab.be//werkinzicht/gesprekna.shtml";
                    intentNa.putExtra("URL", url);
                    startActivity(intentNa);
                    break;
            }
        }
    }