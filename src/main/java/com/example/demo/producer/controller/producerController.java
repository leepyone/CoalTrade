package com.example.demo.producer.controller;


import com.example.demo.producer.dao.producerDao;
import com.example.demo.producer.entity.producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Map;

@Controller
public class producerController {

    @Autowired
    private producerDao producerDao;

    @RequestMapping("/toProducerList")
    public String toProducerList(Map map ){
        List<producer> allProducer = producerDao.getAllProducer();
        map.put("allProducer",allProducer);;
        return "producerList";
    }

}
