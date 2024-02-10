package com.codeseasy.examples.loginregister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.codeseasy.examples.loginregister.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView homeButton;
    ImageView aboutButton;
    ImageView mapButton;
    ImageView logoutButton;
    TextView textViewEmail; // TextView to display the email
    Toolbar myToolbar;
    ActivityMainBinding binding;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("NearbyHealth");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.map:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.profile:
                        viewPager2.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.map).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);
                        break;
                }
                super.onPageSelected(position);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Experience effortless to find nearest Hospital with Nearby Health app! " +
                    "Download now for ultimate journey. " +
                    "Tap the link for a feel new experience. " +
                    "Download Nearby Health now! \" via https://github.com/ammarshamsullnaim/NearbyHealth.git/");
            startActivity(Intent.createChooser(shareIntent, null));
            return true;
        }

        else if (item.getItemId() == R.id.item_about) {
            Intent aboutIntent = new Intent(this, AboutPage.class);
            startActivity(aboutIntent);
        }

        else if (item.getItemId() == R.id.item_info) {
            Intent infoIntent = new Intent(this, Instruction.class);
            startActivity(infoIntent);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
    }
}


