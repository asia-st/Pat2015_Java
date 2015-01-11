package com.patronat.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public class Stat {
    private BigDecimal number;

    public Stat() {

    }

    public Stat(BigDecimal num) {
        this.number = num;
    }

    @JsonProperty
    public BigDecimal getNumber() {
        return number;
    }
}
