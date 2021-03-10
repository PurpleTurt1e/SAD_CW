package com.example.app16.ui.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Legend {

    private Map<String, List<LegendMapValue>> XYMap;
    private Double miny;
    private Double maxy;
//    private int seriesID = 0; //TODO REPLACE THIS WITH THE SERIES NAME

    public Legend() {
        this.XYMap = new TreeMap<String, List<LegendMapValue>>(
                new Comparator<String>() {

                    @Override
                    public int compare(String o1, String o2) {
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
                        return o1Date.compareTo(o2Date);
                    }

                });
        this.miny = null;
        this.maxy = null;
    }


    public void addSeries(Series series) {
//        seriesID++; // TODO REPLACE THIS WITH THE SERIES NAME
        recalculateMinMax(series);
        for (String xName : series.getXnames()) {
            List<LegendMapValue> entry = XYMap.get(xName);
            if (entry == null) {
                List<LegendMapValue> legendMapValueList = new ArrayList<>();
                legendMapValueList.add(new LegendMapValue(series.getTicker(), series.getYValue(xName)));
                XYMap.put(xName, legendMapValueList);
            } else {
                List<LegendMapValue> legendMapValueList = XYMap.get(xName);
                Double newSeriesYValue = series.getYValue(xName);
                if (newSeriesYValue != null) {
                    legendMapValueList.add(new LegendMapValue(series.getTicker(), newSeriesYValue));
                }
            }
        }
    }

    private void recalculateMinMax(Series series) {
        if (maxy == null || maxy <= series.getMaxy()) {
            maxy = series.getMaxy();
        }
        if (miny == null || miny >= series.getMiny()) {
            miny = series.getMiny();
        }
    }

    public List<Map.Entry<String, List<LegendMapValue>>> getLegend() {
        Set<Map.Entry<String, List<LegendMapValue>>> entrySet = XYMap.entrySet();
        return new ArrayList<>(entrySet);
    }

}
