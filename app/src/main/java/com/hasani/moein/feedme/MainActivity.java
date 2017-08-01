package com.hasani.moein.feedme;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.hasani.moein.feedme.CustomListViewAdapter;
import com.hasani.moein.feedme.Item;
import com.hasani.moein.feedme.ParseNews;
import com.hasani.moein.feedme.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="TAG" ;
    private CustomListViewAdapter customListViewAdapter;
    private ArrayList<Item> itemArrayList=new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.Listview);


        downloadData Downloader=new downloadData();
        Downloader.execute("http://www.bartarinha.ir/fa/rss/allnews");
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
                    (MainActivity.this, R.layout.row,itemArrayList);
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
    //////////////////////


}

