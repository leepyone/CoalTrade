package com.example.demo.producer.controller;


import com.example.demo.producer.dao.producerDao;
import com.example.demo.producer.entity.producer;
import com.example.demo.user.entity.User;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class producerController {

//    private  static final Logger logger= Logger.getLogger(producerController.class);
    @Autowired
    private producerDao producerDao;

    @RequestMapping("/toProducerList")
    public String toProducerList(Map map , HttpSession httpSession){
        List<producer> allProducer = producerDao.getAllProducer();
        User loginUser = (User)httpSession.getAttribute("loginUser");
//        logger.info(loginUser);
        map.put("loginUser",loginUser);
        map.put("allProducer",allProducer);;
        return "producerList";
    }

}
