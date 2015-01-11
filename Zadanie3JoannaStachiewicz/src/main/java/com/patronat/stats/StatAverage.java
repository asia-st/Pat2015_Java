package com.patronat.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public class StatAverage {
    private BigDecimal average;

    public StatAverage() {

    }

    public StatAverage(BigDecimal av) {
        this.average = av;
    }

    @JsonProperty
    public BigDecimal getAverage() {
        return average;
    }
}
