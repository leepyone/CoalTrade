package com.example.demo.producer.service.impl;

import com.example.demo.producer.dao.producerDao;
import com.example.demo.producer.entity.producer;
import com.example.demo.producer.entity.producerCheck;
import com.example.demo.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class producerSerImpl implements ProducerService {


    @Autowired
    private producerDao producerDao;

    @Override
    public List<producer> getAllProducer() {
        return producerDao.getAllProducer();
    }

    @Override
    public List<producer> getCheckProducers() {
        List<producer> allProducer = producerDao.getAllProducer();
        List<producer> checkProducers  = new ArrayList<>();
        for (producer  producer: allProducer) {
            if(producer.getRegisterState()==0)
                checkProducers.add(producer);
        }
        return checkProducers;
    }

    @Override
    public producer getProducerById(int id) {
        return producerDao.getProducerById(id);
    }

    @Override
    public Map<String, String> getProducerCheck(int id) {
        Map<String, String> producerCheck =producerDao.getProducerCheck(id);
        if(producerCheck==null) {
            System.out.println("没有记录");
            return null;
        }
        String state=  String.valueOf(producerCheck.get("check_state")).trim();

        if(state.equals("0"))
            producerCheck.put("check_state","待审核") ;
        else if (state.equals("1"))
            producerCheck.put("check_state","审核中") ;
        else if (state.equals("2"))
            producerCheck.put("check_state","审核通过") ;
        else if(state.equals("3"))
            producerCheck.put("check_state","驳回") ;

        return producerCheck;
    }

    @Override
    public String getProducerType(producer producer) {

        int type = producer.getProducerType();
        if(type==0)
            return "重点供应商";
        else if (type==1)
            return "内部供应商" ;
        else if (type==2)
            return "一般供应商";
        else
            return "临时供应商" ;
    }

    @Override
    public boolean addBlack(int id) {
        return producerDao.addBlack(id);
    }

    @Override
    public boolean removeBlack(int id) {
        return producerDao.removeBlack(id);
    }

    @Override
    public boolean updateProducerType(int newType, int id) {
      return   producerDao.updateType(newType,id);
    }

    @Override
    public boolean insertProducerCheck(producerCheck producerCheck) {
        return producerDao.insertProducerCheck(producerCheck);
    }

    @Override
    public boolean updateProducerState(int state, int id) {
        return producerDao.updatProducerState(state,id);
    }

//    @Override
//    public String getProducerState(producer producer) {
//        return null;
//    }
}
