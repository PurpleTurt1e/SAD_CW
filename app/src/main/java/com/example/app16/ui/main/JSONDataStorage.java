package com.example.app16.ui.main;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class JSONDataStorage {
    private String name;
    private String date;
    private String dateEnd;
    private FileAccessor fileSystem;

    public JSONDataStorage(String name, String date, String dateEnd, FileAccessor files){
        this.name = name;
        this.date = date;
        this.dateEnd = dateEnd;
        this.fileSystem = files;
    }

    public boolean writeIntoFile(){
        fileSystem.createFile(name + "_" + date + "_" + dateEnd);
        ArrayList<DailyQuote> quotes = DailyQuote.DailyQuote_allInstances;
        JSONArray JSONArrayQuotes = DailyQuote_DAO.writeJSONArray(quotes);
        ArrayList<String> quotesArray = new ArrayList<>();
        for (int i = 0; i < JSONArrayQuotes.length(); i++) {
            try {
                quotesArray.add(JSONArrayQuotes.getString(i));
            } catch (JSONException e) {
                System.out.println("Unable to create Stock Price file");
                quotesArray = null;
            }
        }
        if (quotesArray != null) {
            fileSystem.writeFile(name + "_" + date + "_" + dateEnd, quotesArray);
            return true;
        }
        return false;
    }

    public boolean readFromFile(){
        try {
            ArrayList<String> file = fileSystem.readFile(name + "_" + date + "_" + dateEnd + "");
            if (file == null){
                return false;
            }
            System.out.println("This is meant to be a file: " + file);
            JSONArray JsonArrayFile = new JSONArray(file);
            System.out.println("This is meant to be an JSONArray: " + JsonArrayFile);
            ArrayList<DailyQuote> status = DailyQuote_DAO.parseJSON(JsonArrayFile);
            System.out.println("This is meant to be a Array of Quotes: " + status);
            if (status != null) {
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }


    public String getDate() {
        return date;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getName() {
        return name;
    }
}
