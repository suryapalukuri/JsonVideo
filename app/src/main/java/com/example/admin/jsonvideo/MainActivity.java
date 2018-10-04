package com.example.admin.jsonvideo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private List<Data> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        data=new ArrayList<>();
        String url="https://interview-e18de.firebaseio.com/media.json?print=pretty";
        new ResponseAsync().execute(url);

    }

    private class ResponseAsync extends AsyncTask<String, Void, String> {
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Response from url");
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... urls) {
            String response=urls[0];

            try{
                URL url=new URL(response);
                InputStream inputStream=url.openConnection().getInputStream();
                StringBuffer buffer=new StringBuffer();
                BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line=reader.readLine())!=null){
                    buffer.append(line+"\n");
                    Log.i("Why data ", buffer.toString());
                }
                return buffer.toString();



            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String result){
            Log.i("Response",result);
            Bitmap bitmap=null;
            try{
                JSONArray array=new JSONArray(result);

                for(int i=0;i<array.length();i++){
                    Data item=new Data();
                    JSONObject list_obj = array.getJSONObject(i);
                    item.setDescription(list_obj.getString("description"));
                    item.setId(list_obj.getString("id"));
                    item.setThumb(list_obj.getString("thumb"));
                    item.setTitle(list_obj.getString("title"));
                    item.setUrl(list_obj.getString("url"));
                         data.add(item);
                }
                recyclerView.setAdapter(new DataAdapter(MainActivity.this,data));

            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }
    }
}
