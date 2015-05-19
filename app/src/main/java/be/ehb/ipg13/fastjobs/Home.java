package be.ehb.ipg13.fastjobs;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Home extends Fragment {
    View rootview;
    ImageView imageLogo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_home, container, false);
        imageLogo = (ImageView)rootview.findViewById(R.id.imageViewLogo);
        imageLogo.setImageResource(R.drawable.fastjobslogo);
        return rootview;

    }

}