package com.example.app16.ui.main;

import java.util.Objects;

public class LegendMapValue {

    private final String seriesName;
    private final Double seriesValue;

    public LegendMapValue(String seriesName, Double seriesValue) {
        this.seriesName = seriesName;
        this.seriesValue = seriesValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LegendMapValue)) return false;
        LegendMapValue that = (LegendMapValue) o;
        return seriesName.equals(that.seriesName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriesName);
    }

    public String getSeriesName() {
        return seriesName;
    }

    public Double getSeriesValue() {
        return seriesValue;
    }
}
