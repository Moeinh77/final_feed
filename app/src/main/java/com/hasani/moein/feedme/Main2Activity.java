package com.hasani.moein.feedme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG ="TAG" ;
    private CustomListViewAdapter customListViewAdapter;
    private ArrayList<Item> itemArrayList=new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.Listview);


        downloadData Downloader=new downloadData();
        Downloader.execute("http://www.bartarinha.ir/fa/rss/allnews");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main2, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //////////////////////
    private class downloadData extends AsyncTask<String, Void, String> {

        public void refreshData() {

//                itemArrayList.clear();

//                for(int i=0;i<setsfromdb.size();i++){
//
//                    String Ddate=setsfromdb.get(i).getDate();
//                    int Nnumbers=setsfromdb.get(i).getTimes();
//                    int mId=setsfromdb.get(i).getSetId();
//
//                    set nSet=new set();
//
//                    nSet.setTimes(Nnumbers);
//                    nSet.setSetId(mId);
//                    nSet.setDate(Ddate);
//
//                    setList.add(nSet);
//
//                }
//                dbh.close();
            customListViewAdapter=new CustomListViewAdapter
                    (Main2Activity.this, R.layout.row,itemArrayList);
            listView.setAdapter(customListViewAdapter);
            customListViewAdapter.notifyDataSetChanged();

        }

        @Override
        protected String doInBackground(String... params) {
            String rss=downloadXML(params[0]);
            if(rss==null)
                Log.e(TAG, "doInBackground: Error downloading xml");
            return rss;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParseNews parseNews=new ParseNews();
            parseNews.parse(s);

            itemArrayList=parseNews.getItemsList();
            refreshData();

        }



        private String downloadXML (String urlpath){
            StringBuilder stringBuilder=new StringBuilder();

            try {
                /////
                URL url=new URL(urlpath);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                InputStream input=connection.getInputStream();
                InputStreamReader inputreader=new InputStreamReader(input);
                BufferedReader reader=new BufferedReader(inputreader);
                ////
                int num_of_chars;

                char[] charbuffered = new char[500];

                while (true) {
                    num_of_chars = reader.read(charbuffered);
                    if (num_of_chars < 0)
                        break;
                    if (num_of_chars > 0)
                        stringBuilder.append(String.copyValueOf(charbuffered, 0, num_of_chars));
                }
                reader.close();
                return stringBuilder.toString();

            }catch (MalformedURLException e){
                Log.e(TAG, "downloadXML: Invalid URL: "+e.getMessage());
            }catch (IOException e){
                Log.e(TAG,"downloadXML: IO Exception reading"+e.getMessage());
            }
            return null;
        }


    }
}
