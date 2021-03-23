package com.example.app16.ui.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ModelFacade
  implements InternetCallback
{
  JSONDataStorage dataStorage1;
  JSONDataStorage dataStorage2;
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


  public String findQuote(String date, String dateEnd, String stockTicker, String stockTicker2) throws ExecutionException, InterruptedException, TimeoutException {
      DailyQuote_DAO.date = date;
      DailyQuote_DAO.dateEnd = dateEnd;
      DailyQuote.refreshDB();
      DailyQuote.stockTicker1 = stockTicker;
      dataStorage1 = new JSONDataStorage(stockTicker, date, dateEnd, fileSystem);

    String result = "";
//    if (DailyQuote_DAO.isCached(date))
//    {
//      result = "Data already exists";
//      return result;
//    }
//    else { }
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
      url2 = DailyQuote_DAO.getURL(stockTicker2, sq1, sq2);

      InternetAccessor x = null;
      x = new InternetAccessor();
      x.setDelegate(this);

      if (!stockTicker2.equals("Select Ticker")) { //update condition
          DailyQuote.stockTicker2 = stockTicker2;
          dataStorage2 = new JSONDataStorage(stockTicker2, date, dateEnd, fileSystem);
          x.execute(url, url2);
      }else{
          x.execute(url);
      }

      result = "Called url: " + url + url2 + "\n Data Storage not yet finalised. Please Press \"Analyse\" button on the next tab. ";
    }else{
        if (!stockTicker2.equals("Select Ticker")) { //update condition
            DailyQuote.stockTicker2 = stockTicker2;
            dataStorage2 = new JSONDataStorage(stockTicker2, date, dateEnd, fileSystem);
            if (dataStorage1.readFromFile() && dataStorage2.readFromFile()) {
                result = "Gathered Data from Offline Resource";
            }else{
                result = "You are offline, please connect Online to retrieve data";
            }
        }else{
            if (dataStorage1.readFromFile()) {
                result = "Gathered Data from Offline Resource";
            }else{
                result = "You are offline, please connect Online to retrieve data";
            }
        }

    }
    return result;
  }

  public GraphDisplay analyse()
  {
      GraphDisplay result = null;
      result = new GraphDisplay();
      Legend legend = new Legend();
      Map<String, ArrayList<DailyQuote>> allQuotes = DailyQuote.getAllInstances();
//      System.out.println("These are all the quotes for "+ dataStorage1.getName() +": "  + allQuotes.get(dataStorage1.getName()));
//      System.out.println("These are all the quotes for "+ dataStorage2.getName() +": "  + allQuotes.get(dataStorage2.getName()));
      dataStorage1.setQuotes(allQuotes.get(dataStorage1.getName()));
      dataStorage2.setQuotes(allQuotes.get(dataStorage2.getName()));
//      System.out.println(allQuotes);
      if (activeNetwork != null) {
          if (dataStorage1 != null){
              dataStorage1.writeIntoFile();
          }
          if(dataStorage2 != null){
              dataStorage2.writeIntoFile();
          }
      }
      allQuotes.forEach((key, value)->{
        List<String> xVals = new ArrayList<>();
        List<Double> yVals = new ArrayList<>();

        value.forEach((DailyQuote)->{
          xVals.add(DailyQuote.date);
          yVals.add(DailyQuote.close);
        });
          Series series = new Series(key);
          series.setAxisData(xVals,yVals);
          legend.addSeries(series);
      });
      ArrayList<String> Xvals = new ArrayList<>();
      ArrayList<Double> Yvals = new ArrayList<>();
      ArrayList<Double> Zvals = new ArrayList<>();

      List<Map.Entry<String, List<LegendMapValue>>> XYmap = legend.getLegend();
      XYmap.forEach((q)->{
        if (allQuotes.keySet().size() == 1){
          Xvals.add(q.getKey());
          Yvals.add(q.getValue().get(0).getSeriesValue());
        }else if(q.getValue().size() == 2){
            Xvals.add(q.getKey());
            Yvals.add(q.getValue().get(0).getSeriesValue());
            Zvals.add(q.getValue().get(1).getSeriesValue());
        }
      });
      if (allQuotes.keySet().size() == 2){
          result.setZPoints(Zvals);
      }
      result.setXNominal(Xvals);
      result.setYPoints(Yvals);
    return result;
  }
}
