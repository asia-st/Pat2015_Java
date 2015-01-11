package com.patronat.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public class StatSum {
    private BigDecimal sum;

    public StatSum() {

    }

    public StatSum(BigDecimal sum) {
        this.sum = sum;
    }

    @JsonProperty
    public BigDecimal getSum() {
        return sum;
    }
}
