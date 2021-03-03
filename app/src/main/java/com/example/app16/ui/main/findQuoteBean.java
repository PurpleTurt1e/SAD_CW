package com.example.app16.ui.main;

import java.util.ArrayList;

import java.util.List;

import android.content.Context;

public class findQuoteBean
{ ModelFacade model = null;

  private String date = "";
  private String dateEnd = "";
  private String stockTicker = "";
  private List errors = new ArrayList();

  public findQuoteBean(Context _c) { model = ModelFacade.getInstance(_c); }

  public void setdate(String datex)
  { date = datex; }

  public void setEndDate(String datex)
  { dateEnd = datex; }

  public void setStockTicker(String stockTickerx)
  { stockTicker = stockTickerx; }

  public void resetData()
  { date = "";
    dateEnd = "";
    stockTicker = "";
    }

  public boolean isfindQuoteerror()
  { errors.clear(); 
    return errors.size() > 0;
  }

  public String errors() { return errors.toString(); }

  public String findQuote()
  { return model.findQuote(date, dateEnd, stockTicker); }

}

