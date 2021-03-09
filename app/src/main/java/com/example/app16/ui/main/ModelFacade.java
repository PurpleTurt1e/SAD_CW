package com.example.app16.ui.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelFacade
  implements InternetCallback
{
  JSONDataStorage dataStorage;
  NetworkInfo activeNetwork;
  FileAccessor fileSystem;
  Context myContext;
  static ModelFacade instance = null; 

  public static ModelFacade getInstance(Context context)
  { if (instance == null) 
    { instance = new ModelFacade(context); }
    return instance;
  }

  private ModelFacade(Context context)
  { myContext = context; 
    fileSystem = new FileAccessor(context); 
  }

  public void internetAccessCompleted(String response) {
    DailyQuote_DAO.makeFromCSV(response);
  }

  public String findQuote(String date)
  {
    String result = "";
    if (DailyQuote_DAO.isCached(date))
    {
      result = "Data already exists";
      return result;
    }
    else {  }
    long t1 = 0;
    t1 = DateComponent.getEpochSeconds(date);
    long t2 = 0;
    t2 = (t1 + 7 * 86400);
    String url = "";
    ArrayList<String> sq1 = null;
    sq1 = Ocl.copySequence(Ocl.initialiseSequence("period1","period2","interval","events"));
    ArrayList<String> sq2 = null;
    sq2 = Ocl.copySequence(Ocl.initialiseSequence((t1 + ""),(t2 + ""),"1d","history"));
    url = DailyQuote_DAO.getURL("GBPUSD=X", sq1, sq2);
    InternetAccessor x = null;
    x = new InternetAccessor();
    x.setDelegate(this);
    x.execute(url);
    result = ("Called url: " + result);
    return result;
  }

  private String validateQuote(String stockTicker, String stockerTicker2, long t1, long t2){
    String output = "";

    if (t1 >= t2) {
      output += "Please make the start date earlier than the end date. ";
    }

    if (t2 > t1 + 731 * 86400) {
      output += "Please make the end date within 2 years of the start date. ";
    }

    if(stockTicker.equals("Select Ticker")){
      output += "Please choose a stock. ";
    }

    if(stockTicker.equals(stockerTicker2)){
      output += "Please choose separate stocks. ";
    }
    return output;
  }


  public String findQuote(String date, String dateEnd, String stockTicker, String stockTicker2)
  {
    dataStorage = new JSONDataStorage(stockTicker, date, dateEnd, fileSystem);
    System.out.println("date End of file will be: " + dataStorage.getDateEnd());
    String result = "";
    if (DailyQuote_DAO.isCached(date))
    {
      result = "Data already exists";
      return result;
    }
    else { }
    long t1 = 0;
    t1 = DateComponent.getEpochSeconds(date);
    long t2 = 0;
    t2 = DateComponent.getEpochSeconds(dateEnd);

   String validation = validateQuote(stockTicker, stockTicker2, t1, t2);
   if (!validation.equals("")){
     return validation;
   }

    String url = "";
    String url2 = "";
    ArrayList<String> sq1 = null;
    sq1 = Ocl.copySequence(Ocl.initialiseSequence("period1", "period2", "interval", "events"));
    ArrayList<String> sq2 = null;
    sq2 = Ocl.copySequence(Ocl.initialiseSequence((t1 + ""), (t2 + ""), "1d", "history"));
    ConnectivityManager cm = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    activeNetwork = cm.getActiveNetworkInfo();
    if (activeNetwork != null) {
      url = DailyQuote_DAO.getURL(stockTicker, sq1, sq2);
      url2 = DailyQuote_DAO.getURL("AMZN", sq1, sq2);

      InternetAccessor x = null;
      x = new InternetAccessor();
      x.setDelegate(this);
      x.execute(url);
      System.out.println("has X cancelled the call to the URL?: " + x.isCancelled());

      if (true) { //update condition
        InternetAccessor x2 = null;
        x2 = new InternetAccessor();
        x2.setDelegate(this);
        x2.execute(url2);
        System.out.println("has X cancelled the call to the URL?: " + x.isCancelled());
      }

      result = "Called url: " + url + url2 + "\n Data Storage not yet finalised. Please Press \"Analyse\" button on the next tab. ";
    }else{
        if (dataStorage.readFromFile()) {
          result = "Gathered Data from Offline Resource";
        }else{
          result = "You are offline, please connect Online to retrieve data";
        }
    }
    return result;
  }

  public GraphDisplay analyse()
  {
    GraphDisplay result = null;
    result = new GraphDisplay();
    ArrayList<DailyQuote> quotes = null;
    quotes = Ocl.copySequence(DailyQuote.DailyQuote_allInstances);
    if (activeNetwork != null) {
      dataStorage.writeIntoFile();
    }
    ArrayList<String> xnames = null;
    xnames = Ocl.copySequence(Ocl.collectSequence(quotes,(q)->{return q.date;}));
    ArrayList<Double> yvalues = null;
    yvalues = Ocl.copySequence(Ocl.collectSequence(quotes,(q)->{return q.close;}));
    result.setXNominal(xnames);
//    result.setYPoints(yvalues);

    ArrayList<Double> xvals = new ArrayList<>();
    xvals.add(1.0);
    xvals.add(2.0);
    xvals.add(3.0);
    xvals.add(4.0);

    ArrayList<String> xvals2 = new ArrayList<>();
    xvals2.add("test");
    xvals2.add("test 2");
    xvals2.add("test 3");
    xvals2.add("test 4");


    ArrayList<Double> yvals = new ArrayList<>();
    yvals.add(70.0);
    yvals.add(200.0);
    yvals.add(30.0);


    result.setXNominal(xvals2);
    result.setYPoints(yvalues);
    result.setZPoints(yvals);
    result.addLine("Test", xvals, yvals);


    return result;
  }
}
