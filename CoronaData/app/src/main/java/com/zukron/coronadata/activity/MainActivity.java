package com.zukron.coronadata.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;
import com.zukron.coronadata.R;
import com.zukron.coronadata.fragment.AboutFragment;
import com.zukron.coronadata.fragment.GlobalHomeFragment;
import com.zukron.coronadata.fragment.CountryHomeFragment;
import com.zukron.coronadata.fragment.list.ListCountryFragment;
import com.zukron.coronadata.fragment.list.ListProvinceFragment;
import com.zukron.coronadata.fragment.ProvinceHomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze_24);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // set default fragment and which is load first
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // check fragment
        if (getIntent().getExtras() != null) {
            String intentFragment = getIntent().getExtras().getString("loadFragment");

            switch (intentFragment) {
                case "CountryHomeFragment":
                    String country = getIntent().getStringExtra("country");

                    CountryHomeFragment countryHomeFragment = CountryHomeFragment.newInstance(country);
                    fragmentTransaction.add(R.id.fl_main_content, countryHomeFragment).commit();
                    break;
                case "ProvinceHomeFragment":
                    String province = getIntent().getStringExtra("province");

                    ProvinceHomeFragment provinceHomeFragment = ProvinceHomeFragment.newInstance(province);
                    fragmentTransaction.add(R.id.fl_main_content, provinceHomeFragment).commit();
                    break;
            }
        } else {
            // default fragment
            CountryHomeFragment countryHomeFragment = CountryHomeFragment.newInstance("Indonesia");
            fragmentTransaction.add(R.id.fl_main_content, countryHomeFragment).commit();
        }
    }

    /**
     * avoid activity finish when drawer is open
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.nav_item_indonesia:
                CountryHomeFragment countryHomeFragment = CountryHomeFragment.newInstance("Indonesia");
                fragmentTransaction.replace(R.id.fl_main_content, countryHomeFragment).commit();
                break;
            case R.id.nav_item_indonesia_detail:
                ListProvinceFragment listProvinceFragment = new ListProvinceFragment();
                fragmentTransaction.replace(R.id.fl_main_content, listProvinceFragment).commit();
                break;
            case R.id.nav_item_global:
                GlobalHomeFragment globalHomeFragment = new GlobalHomeFragment();
                fragmentTransaction.replace(R.id.fl_main_content, globalHomeFragment).commit();
                break;
            case R.id.nav_item_global_detail:
                ListCountryFragment listCountryFragment = new ListCountryFragment();
                fragmentTransaction.replace(R.id.fl_main_content, listCountryFragment).commit();
                break;
            case R.id.nav_item_about:
                AboutFragment aboutFragment = new AboutFragment();
                fragmentTransaction.replace(R.id.fl_main_content, aboutFragment).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}