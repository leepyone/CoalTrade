package com.example.demo.producer.entity;

import lombok.Data;

import java.util.Date;

@Data
public class producerCheck {

    private int checkId;
    private int producerId;
    private Date checkDate;
    private int checkpersonId;
    private int checkState;
    private String remark;

}
