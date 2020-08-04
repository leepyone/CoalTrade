package com.example.demo.producer.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class annualScore {

    private int scoreId;
    private int producerId;

    private String year;
    private BigDecimal fScore;
    private BigDecimal pScore;
    private BigDecimal cScore;
    private String level;
}
