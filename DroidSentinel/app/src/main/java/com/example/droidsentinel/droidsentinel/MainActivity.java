package com.example.droidsentinel.droidsentinel;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int NOTIFICATION_ID = 1;

    EditText et1;
    EditText et2;
    EditText et3;
    EditText et4;
    TextView consola;
    String log;
    Boolean ok;
    LogTask logTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        consola = (TextView) findViewById(R.id.log);
        log = "";
        ok = false;

        et1 = (EditText)findViewById(R.id.threshold);
        et2 = (EditText)findViewById(R.id.window_len);
        et3 = (EditText)findViewById(R.id.maxts);
        et4 = (EditText)findViewById(R.id.readyforescast);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void startstopButton(View vista) {
        if (!ok) ok = true;
        else ok = false;


        int threshold = 0;
        int window_len = 0;
        int maxts = 0;
        int readyforescast = 0;

        if (et1.getText().toString().matches("")) {
            threshold = 20;
        } else threshold = Integer.valueOf(et1.getText().toString());
        if (et2.getText().toString().matches("")) {
            window_len = 5;
        } else window_len = Integer.valueOf(et2.getText().toString());
        if (et3.getText().toString().matches("")) {
            maxts = 52 ;
        } else maxts = Integer.valueOf(et3.getText().toString());
        if (et4.getText().toString().matches("")) {
            readyforescast = 20;
        } else  readyforescast =  Integer.valueOf(et4.getText().toString());


        consola.setText(threshold + "," + window_len + "," + maxts + "," + readyforescast);

        if (ok) {
            logTask = new LogTask(this,consola,log,threshold,window_len,maxts,readyforescast);
            logTask.execute();
        } else logTask.cancel(true);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_warning) {
            // Handle the camera action
        } else if (id == R.id.nav_logs) {

        } else if (id == R.id.nav_aboutthis) {

        } else if (id == R.id.nav_aboutthis) {

        } else if (id == R.id.nav_aboutus) {

        } else if (id == R.id.nav_github) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    static public String RunCommand(String[] cmd) {
        StringBuffer cmdOut = new StringBuffer();
        Process process;
        try {
            process = Runtime.getRuntime().exec(cmd);
            InputStreamReader r = new InputStreamReader(process.getInputStream());
            BufferedReader bufReader = new BufferedReader(r);
            char[] buf = new char[4096];
            int nRead = 0;
            while ((nRead = bufReader.read(buf)) > 0) {
                cmdOut.append(buf, 0, nRead);
            }
            bufReader.close();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cmdOut.toString();
    }

}

