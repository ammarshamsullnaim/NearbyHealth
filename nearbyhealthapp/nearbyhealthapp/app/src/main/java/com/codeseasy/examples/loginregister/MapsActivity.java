package com.codeseasy.examples.loginregister;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;


import android.view.View;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.codeseasy.examples.loginregister.databinding.ActivityMapsBinding;

import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    MarkerOptions marker;
    Vector<MarkerOptions> markerOptions;

    LatLng alorsetar;
    LatLng currentUserLocation; // Declare the variable

    private String URL = "https://nearbyhealth.000webhostapp.com/all.php";
    RequestQueue requestQueue;
    Gson gson;
    Hospital[] hospitals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isUserLoggedIn()) {
            // Redirect to login page if not logged in
            redirectToLogin();
        }

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Move these lines to the top
        binding.showNearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNearbyMarkers();
            }
        });

        gson = new GsonBuilder().create();

        //This way to get data manual from local file
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markerOptions = new Vector<>();

        //Klinik Perlis
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.49, 100.20))
                .title("Klinik Desa Abi")
                .snippet("01000, Kangar, Perlis")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.48, 100.29))
                .title("Klinik Desa Guar Nangka")
                .snippet("02500, Kangar, Perlis")
        );

        //Klinik Kedah
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(5.82, 100.52))
                .title("Klinik Desa Batu 3")
                .snippet("08300, Kuala Muda, Kedah")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.165, 100.403))
                .title("Klinik Desa Hutan Kampung")
                .snippet("05250, Kota Setar, Kedah")
        );

        //Klinik Pulau Pinang
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(5.269, 100.421))
                .title("Klinik Desa Batu Kawan")
                .snippet("14110, Seberang Perai Selatan, Pulau Pinang")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(5.463, 100.2795))
                .title("Klinik Kesihatan Tanjung Bungah")
                .snippet("11200 Tanjung Bungah, Pulau Pinang")
        );

        //Klinik Perak
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(4.614, 101.057))
                .title("Klinik Kesihatan Jelapang")
                .snippet("30020 Ipoh,Perak")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(4.666, 101.158))
                .title("Klinik Kesihatan Tanjung Rambutan")
                .snippet("31250 Tanjung Rambutan,Perak")
        );

        //Klinik Selangor
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(3.069, 101.775))
                .title("Klinik Kesihatan Batu 9")
                .snippet("43200 Cheras, Selangor")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(2.979, 101.826))
                .title("Klinik Kesihatan Kajang")
                .snippet("43000 Kajang, Selangor")
        );

        //Klinik W.P K.L
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(3.174, 101.708))
                .title("Klinik Kesihatan Kuala Lumpur")
                .snippet("53200 Kuala Lumpur")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(3.106, 101.748))
                .title("Klinik Kesihatan Cheras Baru")
                .snippet("56100 Kuala Lumpur")
        );

        //Klinik W.P Putrajaya
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(2.943, 101.676))
                .title("Klinik Kesihatan Putrajaya")
                .snippet("62250 Putrajaya, Wilayah Persekutuan Putrajaya")
        );

        //Klinik Negeri Sembilan
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(2.725, 101.935))
                .title("Klinik Kesihatan Seremban")
                .snippet("70300 Seremban, Negeri Sembilan")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(2.822, 101.801))
                .title("Klinik Kesihatan Nilai")
                .snippet("71800 Seremban, Negeri Sembilan")
        );

        //Klinik Melaka
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(2.435, 102.089))
                .title("Klinik Kesihatan Masjid Tanah")
                .snippet("78300 Masjid Tanah, Melaka")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(2.435, 102.031))
                .title("Klinik Kesihatan Kuala Sungai Baru")
                .snippet("78200 Kuala Sungai Baru, Melaka")
        );

        //Klinik Johor
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(1.746, 103.732))
                .title("Klinik Kesihatan Ibu & Anak Johor Bahru")
                .snippet("80100 Johor Bahru")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(1.6798, 103.787))
                .title("Klinik Kesihatan Larkin")
                .snippet("80350 Johor Bahru, Johor")
        );

        //Klinik Pahang
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(3.925, 103.310))
                .title("Klinik Kesihatan Bandar Kuantan")
                .snippet("25200 Kuantan, Pahang")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(3.752, 103.107))
                .title("Klinik Kesihatan Gambang")
                .snippet("26300 Gambang, Pahang")
        );

        //Klinik Terengganu
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(5.318, 103.135))
                .title("Klinik Kesihatan Hiliran")
                .snippet("20300 Kuala Terengganu, Terengganu")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(4.225, 103.439))
                .title("Klinik Kesihatan Kuala Kemaman")
                .snippet("24000 Chukai, Terengganu")
        );

        //Klinik Kelantan
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.146, 102.245))
                .title("Klinik Kesihatan Kota Bharu")
                .snippet("15200 Kota Bharu, Kelantan")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(5.907, 102.373))
                .title("Klinik Kesihatan Pasire Puteh")
                .snippet("16800 Pasir Puteh, Kelantan")
        );

        //Klinik Sarawak
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(1.576, 110.431))
                .title("Klinik Kesihatan Kota Samarahan")
                .snippet("94300 Kota Samarahan, Sarawak")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(1.818, 110.332))
                .title("Klinik Kesihatan Santubong")
                .snippet("93050 Santubong, Sarawak")
        );

        //Klinik W.P Labuan
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(5.2896, 115.264))
                .title("Klinik Kesihatan Labuan")
                .snippet("87000 Labuan, Labuan")
        );

        //Klinik Sabah
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(5.309, 116.319))
                .title("Klinik Kesihatan Keningau")
                .snippet("89007 Kota Kinabalu, Sabah")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.003, 116.071))
                .title("Klinik Kesihatan Luyang")
                .snippet("88300 Kota Kinabalu, Sabah")
        );

        alorsetar = new LatLng(6.12, 100.3755);
        marker = new MarkerOptions().position(alorsetar).title("Alor Setar").snippet("Cawangan di buka 7am-9pm");

    }

    private boolean isUserLoggedIn() {
        // Check if the user email is stored in SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        String userEmail = sharedPref.getString("email", null);
        return userEmail != null;
    }

    private void redirectToLogin() {
        // Redirect to the login page
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish(); // Close the current activity
    }

    private void showNearbyMarkers() {
        if (isLocationPermissionGranted()) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (locationManager != null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        }
        // Call sendRequest() in onLocationChanged method
    }

    public void onLocationChanged(Location location) {
        // Remove location updates to avoid continuous updates
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }

        // Get the user's current location
        currentUserLocation = new LatLng(location.getLatitude(), location.getLongitude());

        // Send user location to the server
        updateUserLocation(currentUserLocation.latitude, currentUserLocation.longitude);

        // Clear existing markers
        mMap.clear();

        // Show only the locations under a 50km radius
        for (Hospital info : hospitals) {
            Double lat = Double.parseDouble(info.latitude);
            Double lng = Double.parseDouble(info.longitude);

            // Calculate the distance between the user and the hospital
            float[] results = new float[1];
            Location.distanceBetween(currentUserLocation.latitude, currentUserLocation.longitude,
                    lat, lng, results);

            if (results[0] <= 50000) {  // Within 50km radius
                String title = info.name;
                String snippet = info.address;
                String snippet1 = info.phoneNo;
                String snippet2 = info.district;
                String snippet3 = info.postcode;
                String snippet4 = info.state;

                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                        .title(title)
                        .snippet(snippet)
                        .snippet(snippet1)
                        .snippet(snippet2)
                        .snippet(snippet3)
                        .snippet(snippet4)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                mMap.addMarker(marker);
            }
        }

        // Center the camera on the user's location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUserLocation, 8));
    }
    private void updateUserLocation (double latitude, double longitude) {
        SharedPreferences sharedPref = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        String userEmail = sharedPref.getString("email", null);

        if (userEmail != null) {
            // Build the URL with user email, latitude, and longitude
            String updateLocationURL = "https://nearbyhealth.000webhostapp.com/update_location.php?email=" + Uri.encode(userEmail, "@") +
                    "&latitude=" + latitude + "&longitude=" + longitude;

            // Make a request to update user location
            StringRequest locationRequest = new StringRequest(Request.Method.GET, updateLocationURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle response if needed
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error if needed
                        }
                    });

            // Add the request to the Volley queue
            requestQueue.add(locationRequest);
        }
    }

    private void showCurrentUserLocation() {
        if (isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
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

    //This way to retrieve location from online database(web-server)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alorsetar, 8));
        enableMyLocation();
        showCurrentUserLocation();
        sendRequest(); // Call the method to fetch data from the database
    }
    private boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void enableMyLocation() {
        String perms[] = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_NETWORK_STATE"};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                Log.d("nearbyhealth", "permission granted");
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            Log.d("nearbyhealth", "permission denied");
            ActivityCompat.requestPermissions(this, perms, 200);
        }
    }

    public void sendRequest() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, onSuccess, onError);
        requestQueue.add(stringRequest);
    }

    public Response.Listener<String> onSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hospitals = gson.fromJson(response, Hospital[].class);

            // this will be displayed on logcat as debug
            Log.d("Hospital", "Number of Hospital Data Point : " + hospitals.length);

            // to handle if empty hospital or no internet access
            if (hospitals.length < 1) {
                Toast.makeText(getApplicationContext(), "Problem retrieving JSON data", Toast.LENGTH_LONG).show();
                return;
            }

            // Add nearby markers within 50km radius
            for (Hospital info : hospitals) {
                Double lat = Double.parseDouble(info.latitude);
                Double lng = Double.parseDouble(info.longitude);
                String title = info.name;
                String snippet = info.address;
                String snippet1 = info.phoneNo;
                String snippet2 = info.district;
                String snippet3 = info.postcode;
                String snippet4 = info.state;

                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                        .title(title)
                        .snippet(snippet)
                        .snippet(snippet1)
                        .snippet(snippet2)
                        .snippet(snippet3)
                        .snippet(snippet4)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                mMap.addMarker(marker);
            }
        }
    };

    public Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
        }
    };

    private void handleLoginResult(String result, String email) {
        if (result.equals("Login Success")) {
            // Save user email in SharedPreferences
            saveUserEmail(email);

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            // Continue with your logic or redirect to another activity if needed
        } else {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserEmail(String email) {
        // Save user email in SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", email);
        editor.apply();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}