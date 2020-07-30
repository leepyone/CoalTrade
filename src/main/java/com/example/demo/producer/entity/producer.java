package com.example.demo.producer.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class producer {
    private int producerId;
    private String producerName;
    private String producerCode;
    private String legalPerson;
    private BigDecimal regiCaptial;
    private String bank;
    private String connectPerson;
    private String connectNumber;
    private String connectAddress;
    private String connectEmail;
    private String warehouseAddress;
    private String attachment1;
    private String attachment2;
    private String attachment3;
    private int producerType;
    private int black;
    private int registerState;
    private Date registerDate;



}
