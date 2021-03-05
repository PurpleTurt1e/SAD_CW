package com.example.app16.ui.main;

import java.util.*;

public class Series {

    private final String ticker;
    private Map<String, Double> XYMap;
    private Double miny;
    private Double maxy;

    public Series(String ticker) {
        this.ticker = ticker;
        miny = null;
        maxy = null;
        XYMap = new HashMap<>();
    }

    public void setAxisData(List<String> xnames, List<Double> yvalues) {
        miny = null;
        maxy = null;
        if (!yvalues.isEmpty()) {
            setMinMaxValues(yvalues);
        }
        for (int i = 0; i < xnames.size() && i < yvalues.size(); i++) {
            XYMap.put(xnames.get(i), yvalues.get(i));
        }
    }

    private void setMinMaxValues(List<Double> yvalues) {
        miny = yvalues.get(0);
        maxy = yvalues.get(0);
        for (int i = 1; i < yvalues.size(); i++) {
            double ycoord = yvalues.get(i);
            if (ycoord < miny) {
                miny = ycoord;
            }
            if (ycoord > maxy) {
                maxy = ycoord;
            }
        }
    }

    public Double getYValue(String xvalue) {
        return XYMap.get(xvalue);
    }

    public String getTicker() {
        return ticker;
    }

    public Set<String> getXnames() {
        return XYMap.keySet();
    }

    public Double getMiny() {
        return miny;
    }

    public Double getMaxy() {
        return maxy;
    }
}