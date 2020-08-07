package com.example.posapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
//import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;
    public String first_name;
    public String criteria = "rating";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        Intent intent = getIntent();
//        first_name = intent.getStringExtra("first_name");
//        final Button button = (Button) findViewById(R.id.openTab);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent1 = new Intent(MapsActivity.this, Tab.class);
//                intent1.putExtra("username", first_name);
//                startActivity(intent1);
//                //myIntent.putExtra("google_id", googleId)
//                //myIntent.putExtra("google_first_name", googleFirstName)
////            myIntent.putExtra("google_last_name", googleLastName)
////            myIntent.putExtra("google_email", googleEmail)
////            myIntent.putExtra("google_profile_pic_url", googleProfilePicURL)
////            myIntent.putExtra("google_id_token", googleIdToken)
////                this.startActivity(myIntent)
//            }
//        });

        URL myUrl = null;
        try {
            myUrl = new URL("https://developers.zomato.com/api/v2.1/search?count=10&lat=21.493179&lon=83.986847&radius=1000&sort=rating");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ListView rlv = findViewById(R.id.restroListView);
        DownloadFilesTask dd = new DownloadFilesTask(this, rlv);
        dd.execute(myUrl);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sortBy);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)findViewById(checkedId);
                String rc = (String) rb.getText();
                if (rc.equals("Cost"))
                    criteria = "cost";
                else if (rc.equals("Rating"))
                    criteria = "rating";
                else if (rc.equals("Distance"))
                    criteria = "real_distance";
                else
                    Log.e("RadioButton", "Not recognized");

                LatLng pos = currentLocationmMarker.getPosition(); //
//                Log.e("CheckID",String.valueOf(checkedId));
                Log.e("Current place", pos.latitude + ":" + pos.longitude);
//                criteria = "real_distance";
                String lt = String.valueOf(pos.latitude);
                String ln = String.valueOf(pos.longitude);
                URL sortUrl = null;
                try {
                    sortUrl = new URL("https://developers.zomato.com/api/v2.1/search?count=10&lat="+lt+"&lon="+ln+"&radius=1000&sort="+criteria);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                DownloadFilesTask dd = new DownloadFilesTask(MapsActivity.this, rlv);
                dd.execute(sortUrl);
                //Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });

//        Button button = findViewById(R.id.openTab);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent1 = new Intent(MapsActivity.this, Tab.class);
//                intent1.putExtra("username", "Test");
//                startActivity(intent1);
//            }
//        });

//        RestroAdapter fd = dd.getList();
//        fd.setOnItemClickListener((parent, view, position, id) -> {
//            Intent intent1 = new Intent(MapsActivity.this, Tab.class);
//            intent1.putExtra("username", "Test");
//            startActivity(intent1);
//        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            bulidGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationmMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }

    public void onClick(View v)
    {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        EditText tf_location; String location,lt,ln;
        List<Address> addressList;

        switch(v.getId())
        {
            case R.id.B_search:
                tf_location =  findViewById(R.id.TF_location);
                location = tf_location.getText().toString();
//                List<Address> addressList;


                if(!location.equals(""))
                {
                    Geocoder geocoder = new Geocoder(this);
                    criteria = "rating";

                    try {
                        addressList = geocoder.getFromLocationName(location, 5);

                        if(addressList != null)
                        {
                            for(int i = 0;i<addressList.size();i++)
                            {
                                LatLng latLng = new LatLng(addressList.get(i).getLatitude() , addressList.get(i).getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                lt = String.valueOf(addressList.get(i).getLatitude());
                                ln = String.valueOf(addressList.get(i).getLongitude());
//                                double ln = addressList.get(i).getLongitude();
                                String ltn = lt+":"+ln;
                                Log.e("NewPos",ltn);
                                URL newLoc = null;
                                try {
                                    newLoc = new URL("https://developers.zomato.com/api/v2.1/search?count=10&lat="+lt+"&lon="+ln+"&radius=1000&sort="+criteria);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                ListView rlvv = findViewById(R.id.restroListView);
                                DownloadFilesTask dd = new DownloadFilesTask(this, rlvv);
                                dd.execute(newLoc);
//                                MarkerOptions markerOptions = new MarkerOptions();
//                                markerOptions.position(latLng);
                                markerOptions.title("Current Location");
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                currentLocationmMarker = mMap.addMarker(markerOptions);
//                                markerOptions.title(location);
                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

//            case R.id.B_hopistals:
//                mMap.clear();
//                String hospital = "hospital";
//                String url = getUrl(latitude, longitude, hospital);
//                dataTransfer[0] = mMap;
//                dataTransfer[1] = url;
//
//                getNearbyPlacesData.execute(dataTransfer);
//                Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();
//                break;
        }
    }


    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=AIzaSyC52ZF3lCF5oFKBW_P6kcyk32R11ZofNpY");
//        googlePlaceUrl.append("&key="+getString(R.string.google_maps_key));

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }


    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }

    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
        String name, cuisines, timings, ratings; Context propContext; ListView propListView;
        RestroAdapter feedAdapter;
        List<String> items;
        protected DownloadFilesTask(Context context, ListView listView) {
            name = "";
            cuisines = "";
            timings = "";
            ratings = "";
            propContext = context;
            propListView = listView;
            items = new ArrayList<String>();
            //feedAdapter = null;
        }
        RestroAdapter getList() {
            return feedAdapter;
        }



        protected Long doInBackground(URL... url1) {

            try {
                URL url = new URL(url1[0].toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Set Headers
                String token = "11ebc53bfeec11b6d1427f0fc9a758f8";
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("user-key", token);

                // Output is null here <--------
                //System.out.println(conn.getHeaderField("CustomHeader"));

                // Request not successful
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("Request Failed. HTTP Error Code: " + conn.getResponseCode());
                }

                // Read response
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer jsonString = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    jsonString.append(line);
                }
                br.close();
                conn.disconnect();

                JSONObject jsonObj = new JSONObject(String.valueOf(jsonString));

                // Getting JSON Array node
                JSONArray restroList = jsonObj.getJSONArray("restaurants");

                // looping through All Contacts
                for (int i = 0; i < restroList.length(); i++) {
                    JSONObject c = restroList.getJSONObject(i);
//                    String id = c.getString("id");
//                    String name = c.getString("name");
//                    String email = c.getString("email");
//                    String address = c.getString("address");
//                    String gender = c.getString("gender");

                    // Phone node is JSON Object
                    JSONObject restro = c.getJSONObject("restaurant");
                    JSONObject user_rating = restro.getJSONObject("user_rating");
                    name = restro.getString("name");
                    cuisines = restro.getString("cuisines");
                    timings = restro.getString("timings");
                    ratings = user_rating.getString("aggregate_rating");
                    items.add(name+":#:"+cuisines+":#:"+timings+":#:"+ratings);
                    //String home = phone.getString("home");
                    //String office = phone.getString("office");

                    Log.e("Restro Name", name+":"+cuisines+":"+timings);
//                    Log.e("Restro Name", name);
//                    Log.e("Restro Name", );
//                    Log.e("Restro Name", nm);
                }

                //Log.e("Download", String.valueOf(jsonString));
            } catch (Exception e) {
                e.printStackTrace();
            }


            return Long.parseLong("5");
        }

//        protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
//        }

        protected void onPostExecute(Long result) {
            Log.e("Data", "successful");
//            List<String> items = new ArrayList<String>();;
//            items.add(name);
//            items.add("bb");
//            feedAdapter = new RestroAdapter(propContext, R.layout.restro_record, items);
//            propListView.adapter = feedAdapter;
            feedAdapter = new RestroAdapter(propContext, R.layout.restro_record, items);
            propListView.setAdapter(feedAdapter);
//            showDialog("Downloaded " + result + " bytes");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
