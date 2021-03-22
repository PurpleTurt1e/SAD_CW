package com.example.app16;

import com.example.app16.ui.main.Legend;
import com.example.app16.ui.main.LegendMapValue;
import com.example.app16.ui.main.Series;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

public class LegendUnitTest {

    @Test
    public void addOneSeries() {

        Series goog = new Series("GOOG");
        List<String> googX = new ArrayList<>();
        googX.add("2021-3-11");
        googX.add("2021-3-12");
        googX.add("2021-3-13");
        List<Double> googY = new ArrayList<>();
        googY.add(1.1);
        googY.add(2.3);
        googY.add(0.77);
        goog.setAxisData(googX, googY);

        Legend legend = new Legend();
        legend.addSeries(goog);

        List<Map.Entry<String, List<LegendMapValue>>> legendEntries = legend.getLegend();
        assertEquals(legendEntries.size(), 3);

        legendEntries.forEach(e -> {
            switch (e.getKey()) {
                case "2021-3-11": {
                    List<LegendMapValue> val = e.getValue();
                    assertEquals(val.size(), 1);
                    assertEquals(val.get(0).getSeriesName(), "GOOG");
                    assertEquals(val.get(0).getSeriesValue(), 1.1, 0.01);
                    break;
                }
                case "2021-3-12": {
                    List<LegendMapValue> val = e.getValue();
                    assertEquals(val.size(), 1);
                    assertEquals(val.get(0).getSeriesName(), "GOOG");
                    assertEquals(val.get(0).getSeriesValue(), 2.3, 0.01);
                    break;
                }
                case "2021-3-13": {
                    List<LegendMapValue> val = e.getValue();
                    assertEquals(val.size(), 1);
                    assertEquals(val.get(0).getSeriesName(), "GOOG");
                    assertEquals(val.get(0).getSeriesValue(), 0.77, 0.01);
                    break;
                }
                default:
                    fail();
                    break;
            }
        });
    }

    @Test
    public void checkMinMaxIsCorrect() {

        Series goog = new Series("GOOG");
        List<String> googX = new ArrayList<>();
        googX.add("2021-3-11");
        googX.add("2021-3-12");
        googX.add("2021-3-13");
        List<Double> googY = new ArrayList<>();
        googY.add(1.1);
        googY.add(2.3);
        googY.add(0.77);
        goog.setAxisData(googX, googY);

        Series aapl = new Series("AAPL");
        List<String> aaplX = new ArrayList<>();
        aaplX.add("2021-3-11");
        aaplX.add("2021-3-12");
        aaplX.add("2021-3-14");
        List<Double> aaplY = new ArrayList<>();
        aaplY.add(1.1);
        aaplY.add(1.2);
        aaplY.add(4.37);
        aapl.setAxisData(aaplX, aaplY);

        Legend legend = new Legend();
        legend.addSeries(goog);
        legend.addSeries(aapl);

        List<Map.Entry<String, List<LegendMapValue>>> legendEntries = legend.getLegend();

        assertEquals(legendEntries.size(), 4);

        legendEntries.forEach(e -> {
            switch (e.getKey()) {
                case "2021-3-11": {
                    List<LegendMapValue> val = e.getValue();
                    assertEquals(val.size(), 2);

                    assertEquals(val.get(0).getSeriesName(), "GOOG");
                    assertEquals(val.get(1).getSeriesName(), "AAPL");
                    assertEquals(val.get(0).getSeriesValue(), 1.1, 0.01);
                    assertEquals(val.get(1).getSeriesValue(), 1.1, 0.01);
                    break;
                }
                case "2021-3-12": {
                    List<LegendMapValue> val = e.getValue();
                    assertEquals(val.size(), 2);

                    assertEquals(val.get(0).getSeriesName(), "GOOG");
                    assertEquals(val.get(1).getSeriesName(), "AAPL");
                    assertEquals(val.get(0).getSeriesValue(), 2.3, 0.01);
                    assertEquals(val.get(1).getSeriesValue(), 1.2, 0.01);
                    break;
                }
                case "2021-3-13": {
                    List<LegendMapValue> val = e.getValue();
                    assertEquals(val.size(), 1);

                    assertEquals(val.get(0).getSeriesName(), "GOOG");
                    assertEquals(val.get(0).getSeriesValue(), 0.77, 0.01);
                    break;
                }
                case "2021-3-14": {
                    List<LegendMapValue> val = e.getValue();
                    assertEquals(val.size(), 1);

                    assertEquals(val.get(0).getSeriesName(), "AAPL");
                    assertEquals(val.get(0).getSeriesValue(), 4.37, 0.01);
                    break;
                }
                default:
                    fail();
                    break;
            }
        });
    }

    @Test
    public void dateFormatError() {

        Series goog = new Series("GOOG");
        List<String> googX = new ArrayList<>();
        googX.add("2021-3-11");
        googX.add("string");
        googX.add("2021-3-13");
        List<Double> googY = new ArrayList<>();
        googY.add(1.1);
        googY.add(2.3);
        googY.add(0.77);
        goog.setAxisData(googX, googY);

        Legend legend = new Legend();
        assertThrows(Exception.class, () -> legend.addSeries(goog));

    }

}
