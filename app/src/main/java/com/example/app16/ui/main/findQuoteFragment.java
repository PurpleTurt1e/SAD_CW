package com.example.app16.ui.main;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.app16.R;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;
import android.view.View.OnClickListener;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;
import android.view.View;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.webkit.WebView;
import android.widget.TextView;



public class findQuoteFragment extends Fragment implements OnClickListener
{ View root;
  Context myContext;
  findQuoteBean findquotebean;

  EditText findQuotedateTextField;
  String findQuotedateData = "";
  EditText findQuotedateEndTextField;
  String findQuotedateEndData = "";
  String stockTicker = "";
  TextView findQuoteResult;
  Button findQuoteOkButton;
  Button findQuotecancelButton;
  Spinner spinner;
  private List<Stock> stockList = new ArrayList<>();


 public findQuoteFragment() {}

  public static findQuoteFragment newInstance(Context c)
  { findQuoteFragment fragment = new findQuoteFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    fragment.myContext = c;
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  { super.onCreate(savedInstanceState);}

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  { root = inflater.inflate(R.layout.findquote_layout, container, false);
    Bundle data = getArguments();
    findQuotedateTextField = (EditText) root.findViewById(R.id.findQuotedateField);
    findQuotedateEndTextField = (EditText) root.findViewById(R.id.findQuoteDateEndField);
    findQuoteResult = (TextView) root.findViewById(R.id.findQuoteResult);
    findquotebean = new findQuoteBean(myContext);
    findQuoteOkButton = root.findViewById(R.id.findQuoteOK);
    findQuoteOkButton.setOnClickListener(this);
    findQuotecancelButton = root.findViewById(R.id.findQuoteCancel);
    findQuotecancelButton.setOnClickListener(this);
    spinner = root.findViewById(R.id.stockDropdown);

    readStockData();
//    System.out.println(stockList.get(0).getStockTicker());
//      Stock stock1 = new Stock("GME", "GameStop");
//      stockList.add(stock1);
    ArrayAdapter<Stock> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stockList);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    return root;
  }



  public void onClick(View _v)
  { InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
    try { _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0); } catch (Exception _e) { }
    if (_v.getId() == R.id.findQuoteOK)
    { findQuoteOK(_v); }
    else if (_v.getId() == R.id.findQuoteCancel)
    { findQuoteCancel(_v); }
  }

  public void findQuoteOK(View _v) 
  { 
    findQuotedateData = findQuotedateTextField.getText() + "";
    findQuotedateEndData = findQuotedateEndTextField.getText() + "";
    stockTicker = spinner.getSelectedItem().toString();
    findquotebean.setdate(findQuotedateData);
    findquotebean.setEndDate(findQuotedateEndData);
    findquotebean.setStockTicker(stockTicker);
    if (findquotebean.isfindQuoteerror())
    { Log.w(getClass().getName(), findquotebean.errors());
      Toast.makeText(myContext, "Errors: " + findquotebean.errors(), Toast.LENGTH_LONG).show();
    }
    else
    { findQuoteResult.setText(findquotebean.findQuote() + ""); }
  }



  public void findQuoteCancel(View _v)
  { findquotebean.resetData();
    findQuotedateTextField.setText("");
    findQuoteResult.setText("");
  }

    private void readStockData() {
        InputStream is = getResources().openRawResource(R.raw.yahoo_ticker_symbols);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                //Split data
                String[] tokens = line.split(",");
                System.out.println(tokens);
                //read data
                Stock stock = new Stock(tokens[0]);
                stockList.add(stock);
            }
        } catch (IOException e) {
            Log.wtf("MainClass", "Error reading stock data on line " + line, e);
            e.printStackTrace();
        }

    }
}
