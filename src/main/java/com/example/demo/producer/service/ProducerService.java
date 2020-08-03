package com.example.demo.producer.service;

import com.example.demo.producer.entity.producer;
import com.example.demo.producer.entity.producerCheck;

import java.util.List;
import java.util.Map;

public interface ProducerService {

    /**
     * 获取数据库中所有的供应商
     * @return
     */
    List<producer> getAllProducer();

    /**
     * 获取所有待审核的供应商
     * @return
     */
    List<producer> getCheckProducers();

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
     * 将该供应商添加到黑名单
     * @param id
     * @return
     */
    boolean addBlack(int id );

    /**
     * 将该供应商从黑名单中移除
     * @param id
     * @return
     */
    boolean removeBlack(int id);

    /**
     * 更新供应商的类型
     * @param type
     * @param id
     * @return
     */
    boolean updateProducerType(int type,int id);

    /**
     * 插入一条供应商审核记录
     * @param producerCheck
     * @return
     */
    boolean insertProducerCheck(producerCheck producerCheck);

    /**
     * 改变用户商的审核状态
     * @param state
     * @param id
     * @return
     */
    boolean updateProducerState(int state,int id );

}
