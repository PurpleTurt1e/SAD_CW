package com.example.app16.ui.main;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Map;

public class JSONDataStorage {
    private String name;
    private String date;
    private String dateEnd;
    private FileAccessor fileSystem;
    private ArrayList<DailyQuote> quotes;

    public JSONDataStorage(String name, String date, String dateEnd, FileAccessor files){
        this.name = name;
        this.date = date;
        this.dateEnd = dateEnd;
        this.fileSystem = files;
        fileSystem.createFile(name);
    }

    //Test this
    public boolean writeIntoFile(){
//        System.out.println(quotes.get(1));
        if (quotes.isEmpty()){
            return false;
        }
        ArrayList<DailyQuote> fileRead;
        if (internalReadFromFile() == null){
            fileSystem.createFile(name);
            fileRead = new ArrayList<>();
        }else{
            fileRead = internalReadFromFile();
        }

        if(fileRead.size() > 0){
                    ArrayList<DailyQuote> allDailyQuotes = quotes;
                    ArrayList<DailyQuote> quotesPlaceholderBefore = new ArrayList<>();
                    ArrayList<DailyQuote> quotesPlaceholderAfter = new ArrayList<>();
                    if(DailyQuote.isDateLessThan(date, fileRead.get(0).date)){
                        allDailyQuotes.forEach(q->{
                            if (DailyQuote.isDateLessThan(q.date, fileRead.get(0).date)){
                                quotesPlaceholderBefore.add(q);
                            }
                        });
                    }
                    if(DailyQuote.isDateLessThan(fileRead.get(fileRead.size()-1).date, dateEnd)){
                        allDailyQuotes.forEach(q->{
                            if(!DailyQuote.isDateLessThan(dateEnd, fileRead.get(fileRead.size()).date)){
                                quotesPlaceholderAfter.add(q);
                            }
                        });
                    }
                    ArrayList<String> finalArray = new ArrayList<>();
                    ArrayList<String> allStringQuotesBefore = getStringQuotes(quotesPlaceholderBefore);
                    ArrayList<String> allStringQuotesWithin = getStringQuotes(fileRead);
                    ArrayList<String> allStringQuotesAfter = getStringQuotes(quotesPlaceholderAfter);

                    if(allStringQuotesBefore != null || allStringQuotesWithin != null ||allStringQuotesAfter != null){
                        finalArray.addAll(getStringQuotes(quotesPlaceholderBefore));
                        finalArray.addAll(getStringQuotes(fileRead));
                        finalArray.addAll(getStringQuotes(quotesPlaceholderAfter));
//                        System.out.println(this.name);
//                        System.out.println(quotesPlaceholderBefore);
//                        System.out.println(fileRead);
//                        System.out.println(quotesPlaceholderAfter);
//                        System.out.println("Final Built Array" + finalArray);
                        fileSystem.writeFile(name, finalArray);
                        System.out.println(fileSystem.readFile(name));
                        return true;
                    }
        }else{
            ArrayList<DailyQuote> allQuotes = quotes;
            ArrayList<String> allStringQuotes = getStringQuotes(allQuotes);
//            System.out.println(allStringQuotes);
            if (allStringQuotes != null){
                fileSystem.writeFile(name, allStringQuotes);
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> getStringQuotes(ArrayList<DailyQuote> dailyQuotes){
        JSONArray JSONArrayQuotes = DailyQuote_DAO.writeJSONArray(dailyQuotes);
        ArrayList<String> quotesArray = new ArrayList<>();
        for (int i = 0; i < JSONArrayQuotes.length(); i++) {
            try {
                quotesArray.add(JSONArrayQuotes.getString(i));
            } catch (JSONException e) {
                quotesArray = null;
            }
        }
        return quotesArray;
    }

    public boolean readFromFile(){
        try {
            ArrayList<String> file = fileSystem.readFile(name);
            System.out.println("File Read for "+ name+": " + file);
            if (file == null){
                return false;
            }
            JSONArray JsonArrayFile = new JSONArray(file);
//            System.out.println(JsonArrayFile);
            ArrayList<DailyQuote> status = DailyQuote_DAO.parseJSON(JsonArrayFile);
//            System.out.println(status);
            if (status != null) {
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    private ArrayList<DailyQuote> internalReadFromFile(){
        try {
            ArrayList<String> file = fileSystem.readFile(name);
            if (file == null){
                return null;
            }
            JSONArray JsonArrayFile = new JSONArray(file);
            ArrayList<DailyQuote> status = DailyQuote_DAO.parseJSON(JsonArrayFile);
            if (status != null) {
                return status;
            }
            return null;
        }catch (Exception e){
            return null;
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

    public void setQuotes(ArrayList<DailyQuote> paramQuotes) { quotes= paramQuotes; }
}
