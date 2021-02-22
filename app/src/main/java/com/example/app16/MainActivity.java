package com.example.app16;

import android.os.Bundle;

import com.example.app16.ui.main.Stock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.app16.ui.main.SectionsPagerAdapter;
import com.example.app16.ui.main.ModelFacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ModelFacade model;
//    Spinner spinner;
//    private List<Stock> stockList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        model = ModelFacade.getInstance(this);
//        spinner = findViewById(R.id.stockDropdown);
//
//        ArrayAdapter<Stock> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stockList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);


    }

//    private void readStockData() {
//        InputStream is = getResources().openRawResource(R.raw.yahoo_ticker_symbols);
//
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(is, Charset.forName("UTF-8"))
//        );
//
//        String line = "";
//        try {
//            while (!((line = reader.readLine()) != null)) {
//                //Split data
//                String[] tokens = line.split(",");
//
//                //read data
//                Stock stock = new Stock(tokens[0], tokens[1]);
//                stockList.add(stock);
//            }
//        } catch (IOException e) {
//            Log.wtf("MainClass", "Error reading stock data on line " + line, e);
//            e.printStackTrace();
//        }
//
//    }
}