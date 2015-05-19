package be.ehb.ipg13.fastjobs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class google_MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private String straat,postcode,gemeente,land, address;
    private static  final  String STRAAT ="straat";
    private static  final String POSTCODE ="postcode";
    private static  final  String GEMEENTE ="gemeente";
    private static  final String LAND ="land";
    private Positions positions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google__maps);

        Intent intent = getIntent();

        straat = intent.getStringExtra(STRAAT);
        postcode = intent.getStringExtra(POSTCODE);
        gemeente = intent.getStringExtra(GEMEENTE);
        land =intent.getStringExtra(LAND);
        address = new StringBuilder().append(straat + " ").append(postcode + " ").append(gemeente + " ").append(land).toString();

    }



    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap(double lat,double longi)} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                new GetLatitudeandLongitude().execute();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    public void setUpMap(double lat, double longi) {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(lat ,longi)).title("Marker"));
        //mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        //mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

        mMap.addMarker(new MarkerOptions().position(new LatLng(lat ,longi))).setVisible(true);

        // Move the camera instantly to location with a zoom of 15.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat ,longi), 15));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

    }

    public static Positions getGeoPointFromAddress(String locationAddress) {
        Positions positions = new Positions();
        try {
            String locationAddres = locationAddress.replaceAll(" ", "%20");
            String str = "http://maps.googleapis.com/maps/api/geocode/json?address="
                    + locationAddres + "&sensor=true";

            URL url = new URL(str);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = "", full = "";
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                full += output;
            }

            String lat, lon;
            JSONObject   json = new JSONObject(full);
            JSONObject geoMetryObject = new JSONObject();
            JSONObject locations = new JSONObject();
            JSONArray jarr = json.getJSONArray("results");
            int i;
            for (i = 0; i < jarr.length(); i++) {
                json = jarr.getJSONObject(i);
                geoMetryObject = json.getJSONObject("geometry");
                locations = geoMetryObject.getJSONObject("location");
                lat = locations.getString("lat");
                lon = locations.getString("lng");
                Log.e("LATITUDE","Latitude : " + lat);
                Log.e("LONGITUDE", "Longitude : " + lon);
                positions.setLatitude(lat);
                positions.setLongitude(lon);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return positions;
    }

    private class GetLatitudeandLongitude extends AsyncTask<String, String, Positions>{

        @Override
        protected Positions doInBackground(String... params) {
            return getGeoPointFromAddress(address);
        }

        @Override
        protected void onPostExecute(Positions s) {
            setUpMap(Double.parseDouble(s.getLatitude()),Double.parseDouble(s.getLongitude()));
        }
    }
}
