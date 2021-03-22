package com.example.app16;

import com.example.app16.ui.main.Series;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class SeriesUnitTest {

    @Test
    public void checkMinMaxIsCorrect() {
        Series series = new Series("GOOG");

        List<String> xnames = new ArrayList<>();
        xnames.add("Label 1");
        xnames.add("Label 2");
        xnames.add("Label 3");
        List<Double> yvals = new ArrayList<>();
        yvals.add(1.1);
        yvals.add(2.3);
        yvals.add(0.77);
        series.setAxisData(xnames, yvals);

        assertNotNull(series.getMiny());
        assertNotNull(series.getMaxy());
        double miny = series.getMiny();
        double maxy = series.getMaxy();
        assertEquals(miny, 0.77, 0.01);
        assertEquals(maxy, 2.3, 0.01);
    }

    @Test
    public void checkGetYValue() {
        Series series = new Series("GOOG");

        List<String> xnames = new ArrayList<>();
        xnames.add("Label 1");
        xnames.add("Label 2");
        xnames.add("Label 3");
        List<Double> yvals = new ArrayList<>();
        yvals.add(1.1);
        yvals.add(2.3);
        yvals.add(0.77);
        series.setAxisData(xnames, yvals);

        double val1 = series.getYValue("Label 1");
        double val2 = series.getYValue("Label 2");
        assertEquals(val1, 1.1, 0.01);
        assertEquals(val2, 2.3, 0.01);

    }

    @Test
    public void checkGetXNames() {
        Series series = new Series("GOOG");

        List<String> xnames = new ArrayList<>();
        xnames.add("Label 1");
        xnames.add("Label 2");
        xnames.add("Label 3");
        List<Double> yvals = new ArrayList<>();
        yvals.add(1.1);
        yvals.add(2.3);
        yvals.add(0.77);
        series.setAxisData(xnames, yvals);

        Set<String> val1 = series.getXnames();
        assertEquals(val1, new HashSet<>(xnames));

    }

    @Test
    public void checkGetTicker() {
        Series series = new Series("GOOG");

        List<String> xnames = new ArrayList<>();
        xnames.add("Label 1");
        xnames.add("Label 2");
        xnames.add("Label 3");
        List<Double> yvals = new ArrayList<>();
        yvals.add(1.1);
        yvals.add(2.3);
        yvals.add(0.77);
        series.setAxisData(xnames, yvals);

        assertEquals(series.getTicker(), "GOOG");

    }

}
