package com.example.rubafikri.myapplicationandriod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class event_layout2 extends AppCompatActivity {
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_layout2);

        fm = getSupportFragmentManager();

        addUserFrgmt();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_event_m:
                addUserFrgmt();
                return true;
            case R.id.view_events_m:
                viewUserFrgmt();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addUserFrgmt(){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.user_content, new AddUserFragment());
        ft.commit();
    }
    public void viewUserFrgmt(){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.user_content, new ViewUserFragment());
        ft.commit();
    }
}
