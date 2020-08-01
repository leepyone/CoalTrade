package com.example.demo.producer.controller;


import com.example.demo.producer.dao.producerDao;
import com.example.demo.producer.entity.producer;
import com.example.demo.producer.service.ProducerService;
import com.example.demo.user.entity.User;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class producerController {

//    private  static final Logger logger= Logger.getLogger(producerController.class);
    @Autowired
    private producerDao producerDao;
    @Autowired
    private ProducerService producerService;

    @RequestMapping("/toProducerList")
    public String toProducerList(Map map , HttpSession httpSession){
        List<producer> allProducer = producerDao.getAllProducer();
        User loginUser = (User)httpSession.getAttribute("loginUser");
//        logger.info(loginUser);
        map.put("loginUser",loginUser);
        map.put("allProducer",allProducer);;
        return "producerList";
    }

    @RequestMapping("/toProducerMes/{id}")
    public String toProducerMes(@PathVariable(name = "id") String id, Map map , HttpSession httpSession){
        //从session 中获取 登录用户
        User loginUser = (User)httpSession.getAttribute("loginUser");
//        获取供应商对象
        producer producer = producerService.getProducerById(Integer.parseInt(id.trim()));
//        获取供应商审核记录
        Map<String, String> producerCheck = producerService.getProducerCheck(Integer.parseInt(id.trim()));

//        System.out.println(producerCheck);
        map.put("loginUser",loginUser);
        map.put("producer",producer);
        map.put("producerType",producerService.getProducerType(producer));
        map.put("producerCheck",producerCheck);
        return "producerDetail";
    }

    @RequestMapping("/addBlack/{id}")
    public String addBlack(@PathVariable(name="id") String id ){

//        System.out.println(id);
        producerService.addBlack(Integer.parseInt(id.trim()));
        return "redirect:/toProducerList";
    }

    @RequestMapping("/updateProducerType")
    public String updateProducerType(String newType,String producerId){
        producerService.updateProducerType(Integer.parseInt(newType.trim()),Integer.parseInt(producerId.trim()));
//        System.out.println(newType);
        return "redirect:/toProducerList";
    }

}
