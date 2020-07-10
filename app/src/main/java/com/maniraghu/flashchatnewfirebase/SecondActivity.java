package com.maniraghu.flashchatnewfirebase;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.DashboardFragment;
import com.maniraghu.flashchatnewfirebase.ui.home.HomeFragment;
import com.maniraghu.flashchatnewfirebase.ui.notifications.NotificationsFragment;
import com.maniraghu.flashchatnewfirebase.ui.profile.profile;


public class SecondActivity extends BaseActivity implements EventListener, BottomNavigationView.OnNavigationItemSelectedListener {


    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new DashboardFragment();
    final Fragment fragment3 = new NotificationsFragment();
    final Fragment fragment4 = new profile();
    final FragmentManager fm=getSupportFragmentManager();
    Fragment active = fragment1;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //addFragmentToContainer(new DashboardFragment(), null);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
      //  AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
          //      .build();
       // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(navView, navController);

       navView.setOnNavigationItemSelectedListener(this);
        //fm.beginTransaction().add(R.id.nav_host_fragment, fragment4, "4").hide(fragment4).commit();
        //fm.beginTransaction().add(R.id.nav_host_fragment, fragment3, "3").hide(fragment3).commit();
        //fm.beginTransaction().add(R.id.nav_host_fragment, fragment2, "2").hide(fragment2).commit();
        //fm.beginTransaction().add(R.id.nav_host_fragment,fragment1, "1").commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                // fm.beginTransaction().hide(active).show(fragment1).commit();
                //  active = fragment1;
                getSupportActionBar().setTitle("Corporatz");
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
                return true;
            case R.id.navigation_dashboard:
                //  fm.beginTransaction().hide(active).show(fragment2).commit();
                //  active = fragment2;
                getSupportActionBar().setTitle("Dashboard");
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new DashboardFragment()).commit();
                return true;
            case R.id.navigation_notifications:
                // fm.beginTransaction().hide(active).show(fragment3).commit();
                // active = fragment3;
                getSupportActionBar().setTitle("Notifications");
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new NotificationsFragment()).commit();
                return true;
            case R.id.navigation_profile:
                // fm.beginTransaction().hide(active).show(fragment4).commit();
                // active = fragment4;
                getSupportActionBar().setTitle("Your Profile");
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new profile()).commit();
                return true;


        }
        return false;
    }



}
