package com.codeseasy.examples.loginregister;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment implements LocationListener {

    Button logoutButton;
    TextView textViewEmail2;
    TextView textViewLatitude2;
    TextView textViewLongitude2;
    TextView textViewfullName;
    TextView textViewContact;
    TextView textViewDevice;
    private LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private String URL = "http://localhost/nearbyhealth/alluser.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewEmail2 = view.findViewById(R.id.textViewEmail2);
        textViewLatitude2 = view.findViewById(R.id.textViewLatitude2);
        textViewLongitude2 = view.findViewById(R.id.textViewLongitude2);
        textViewfullName = view.findViewById(R.id.textViewfullName);
        textViewContact = view.findViewById(R.id.textViewContact);
        textViewDevice = view.findViewById(R.id.textViewDevice);

        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        // Check for location permission and request if not granted
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            // Start listening for location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

        // Retrieve user data from SharedPreferences
        String userEmail = getUserEmail();
        String userFullname = getUserFullname();
        String userDevice = getUserDevice();
        String userContact = getUserContact();

        if (userEmail != null) {
            Toast.makeText(getContext(), "Logged in as: " + userEmail, Toast.LENGTH_SHORT).show();
            // Display the email in the TextView
            textViewEmail2.setText("Email: " + userEmail);
        }

        else if (userFullname != null) {
            // Display the fullname in the TextView
            textViewfullName.setText("Fullname: " + userFullname);
        }

        else if (userDevice != null) {
            // Display the device in the TextView
            textViewDevice.setText("Device: " + userDevice);
        }

        else if (userContact != null) {
            // Display the contact in the TextView
            textViewContact.setText("Contact: " + userContact);
        }

        logoutButton = view.findViewById(R.id.button_logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout logic: Navigate to LoginActivity
                Intent intent = new Intent(getActivity(), Login.class); // Use getActivity() to get the context
                startActivity(intent);
                getActivity().finish(); // Close the current activity using getActivity() method
            }
        });
        return view;
    }

    private String getUserEmail() {
        SharedPreferences sharedPref = requireContext().getSharedPreferences("userSession", Context.MODE_PRIVATE);
        return sharedPref.getString("email", null);
    }

    private String getUserFullname() {
        SharedPreferences sharedPref = requireContext().getSharedPreferences("userSession", Context.MODE_PRIVATE);
        return sharedPref.getString("fullName", null);
    }

    private String getUserContact() {
        SharedPreferences sharedPref = requireContext().getSharedPreferences("userSession", Context.MODE_PRIVATE);
        return sharedPref.getString("contact", null);
    }

    private String getUserDevice() {
        SharedPreferences sharedPref = requireContext().getSharedPreferences("userSession", Context.MODE_PRIVATE);
        return sharedPref.getString("userAgent", null);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Display latitude and longitude in TextViews
        textViewLatitude2.setText("Latitude: " + latitude);
        textViewLongitude2.setText("Longitude: " + longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start listening for location updates
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                } else {
                    // Handle if permission is still not granted
                    Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the home button click here if necessary
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
