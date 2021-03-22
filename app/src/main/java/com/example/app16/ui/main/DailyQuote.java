package com.example.app16.ui.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Collections;

class DailyQuote { static ArrayList<DailyQuote> DailyQuote_allInstances = new ArrayList<DailyQuote>();
  public static String stockTicker1;
  public static String stockTicker2;
  String name;
  DailyQuote() {
    DailyQuote_allInstances.add(this); }

  static DailyQuote createDailyQuote() { DailyQuote result = new DailyQuote();
    return result; }

  String date = ""; /* primary */
  static Map<String,DailyQuote> DailyQuote_index = new HashMap<String,DailyQuote>();

  static DailyQuote createByPKDailyQuote(String datex) {
    DailyQuote result = new DailyQuote();
    DailyQuote.DailyQuote_index.put(datex,result);
    result.date = datex;
    return result; }

  public static Map<String, ArrayList<DailyQuote>> getAllInstances(){
    Map<String, ArrayList<DailyQuote>> finalInstances = new HashMap<>();

    if (stockTicker2 == null || DailyQuote_allInstances.size() == 1) {
      finalInstances.put(stockTicker1, DailyQuote_allInstances);
      return finalInstances;
    }
    for (int i = 1; i < DailyQuote_allInstances.size(); i++) {
      if (DailyQuote.isDateLessThan(DailyQuote_allInstances.get(i - 1).date, DailyQuote_allInstances.get(i).date)) {
        ArrayList<DailyQuote> subList1 =new ArrayList<>(DailyQuote_allInstances.subList(0, i));
        ArrayList<DailyQuote> subList2 =new ArrayList<>(DailyQuote_allInstances.subList(i, DailyQuote_allInstances.size()));
        finalInstances.put(stockTicker1, subList1);
        finalInstances.put(stockTicker2, subList2);
        break;
      }
    }
    return finalInstances;
  }

  public static boolean isDateLessThan(String o1, String o2) {
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Date o1Date = null;
    Date o2Date = null;
    try {
      o1Date = simpleDateFormat.parse(o1);
      o2Date = simpleDateFormat.parse(o2);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return o1Date.compareTo(o2Date) != -1;
  }

  public static void  refreshDB(){
    stockTicker1 = null;
    stockTicker2 = null;
    DailyQuote_allInstances.clear();
    DailyQuote_index.clear();
  }

  double open = 0;
  double high = 0;
  double low = 0;
  double close = 0;
  double adjclose = 0;
  double volume = 0;
}
