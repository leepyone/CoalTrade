package com.example.demo.producer.service;

import com.example.demo.producer.entity.producer;

import java.util.Map;

public interface ProducerService {

    /**
     * 通过供应商的id来查询该供应商
     * @param id
     * @return
     */
    producer getProducerById(int id);

    /**
     * 通过供应商id来查询该供应商的审核记录
     * @param id
     * @return
     */
    Map<String,String> getProducerCheck(int id);

    /***
     * 将该供应商的type转化成字符串
     * @param producer
     * @return
     */
    String getProducerType(producer producer );

    /**
     * 返回该用户的状态 String类型
     * @param producer
     * @return
     */
//    String getProducerState(producer producer);

    /**
     * 将该供应商添加到
     * @param id
     * @return
     */
    boolean addBlack(int id );

    /**
     * 更新供应商的类型
     * @param type
     * @param id
     * @return
     */
    boolean updateProducerType(int type,int id);
}
