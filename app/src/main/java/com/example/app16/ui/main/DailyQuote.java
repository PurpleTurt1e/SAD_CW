package com.example.app16.ui.main;

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

  DailyQuote() { DailyQuote_allInstances.add(this); }

  static DailyQuote createDailyQuote() { DailyQuote result = new DailyQuote();
    return result; }

  String date = ""; /* primary */
  static Map<String,DailyQuote> DailyQuote_index = new HashMap<String,DailyQuote>();

  static DailyQuote createByPKDailyQuote(String datex) { DailyQuote result = new DailyQuote();
    DailyQuote.DailyQuote_index.put(datex,result);
    result.date = datex;
    return result; }

  double open = 0;
  double high = 0;
  double low = 0;
  double close = 0;
  double adjclose = 0;
  double volume = 0;
}

