package com.example.rubafikri.myapplicationandriod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class event_layout extends AppCompatActivity {
    FirebaseAuth mAuth;

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_layout);



        fm = getSupportFragmentManager();
        addProFrgmt();



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
                addProFrgmt();
                return true;
            case R.id.view_events_m:
                viewProFrgmt();
                return true;


            case R.id.main:

                Intent i1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void addProFrgmt(){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.product_content, new AddProductFragment());
        ft.commit();
    }
    public void viewProFrgmt(){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.product_content, new ViewProductFragment());
        ft.commit();
    }



}
