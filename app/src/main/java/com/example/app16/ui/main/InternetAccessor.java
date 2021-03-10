package com.example.app16.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;

public class InternetAccessor extends AsyncTask<String, Void, String>
{   private InternetCallback delegate = null;
    private static InternetAccessor instance = null;

    public void setDelegate(InternetCallback c)
    { delegate = c; }

   public static InternetAccessor getInstance()
   { if (instance == null) 
     { instance = new InternetAccessor(); }
     return instance; }

   @Override
   protected void onPreExecute() {}

   @Override
   protected String doInBackground(String... params)
   { String url = params[0];
     String url2 = params.length == 1 ? "" : params[1];
     String myData = "";
     String myData2 = "";
     String finalResult ="";
       try {
           myData = fetchUrl(url);
           if(!url2.equals("")) {
               myData2 = fetchUrl(url2);
           }
     }
     catch (Exception _e)
     { delegate.internetAccessCompleted(null);
       return null;
     }
       if(!myData2.equals("")){
           myData2 = myData2.substring(myData2.indexOf('\n')+1);
           finalResult = myData+myData2;
       }else {
           finalResult = myData;
       }
       return finalResult;
   }

   private String fetchUrl(String url)
   { String urlContent = "";
     StringBuilder myStrBuff = new StringBuilder();

     try{
          URL myUrl = new URL(url);
          HttpURLConnection myConn = (HttpURLConnection) myUrl.openConnection();
          myConn.setRequestProperty("User-Agent", "");
          myConn.setRequestMethod("GET");
          myConn.setDoInput(true);
          myConn.connect();

          InputStream myInStrm = myConn.getInputStream();
          BufferedReader myBuffRdr = new BufferedReader(new InputStreamReader(myInStrm));

          while ((urlContent = myBuffRdr.readLine()) != null) {
              myStrBuff.append(urlContent + '\n');
          }

      } catch (IOException e) {
          delegate.internetAccessCompleted(null);
          return null;
      }

      return myStrBuff.toString();
  }

  @Override
  protected void onPostExecute(String result) { delegate.internetAccessCompleted(result); }

  @Override
  protected void onProgressUpdate(Void... values) {}
 }

interface InternetCallback
{ public void internetAccessCompleted(String response); }


