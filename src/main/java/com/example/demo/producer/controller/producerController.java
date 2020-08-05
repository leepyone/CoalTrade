package com.example.demo.producer.controller;


import com.example.demo.producer.dao.producerDao;
import com.example.demo.producer.entity.annualScore;
import com.example.demo.producer.entity.producer;
import com.example.demo.producer.entity.producerCheck;
import com.example.demo.producer.service.ProducerService;
import com.example.demo.user.entity.User;

//import org.apache.log4j.Logger;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/producer")
public class producerController {

    @Autowired
    private ProducerService producerService;

//    @RequestMapping("/toProducerList1")
//    public String toProducerList(Map map , HttpSession httpSession){
//        List<producer> allProducer = producerService.getAllProducer();
//        User loginUser = (User)httpSession.getAttribute("loginUser");
////        logger.info(loginUser);
//        map.put("loginUser",loginUser);
//        map.put("allProducer",allProducer);
//        return "producerList";
//    }
    @GetMapping("/toProducerList")
    public String allProducers(Model model,
                               @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                               @RequestParam(defaultValue="2",value="pageSize")Integer pageSize,HttpSession httpSession){

        //为了程序的严谨性，判断非空：
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 5;    //设置默认每页显示的数据数
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        List<producer> producersList = producerService.getAllProducer();
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {

            System.out.println("分页数据："+producersList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<producer> pageInfo = new PageInfo<producer>(producersList,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        model.addAttribute("allProducer",producersList);
        User loginUser = (User)httpSession.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        //5.设置返回的jsp/html等前端页面
        // thymeleaf默认就会拼串classpath:/templates/xxxx.html
        return "producerList";
    }

    @RequestMapping("/toFilterProducerList")
    public String toProducerListFilter(Map map , HttpSession httpSession,String producerName){
        List<producer> allProducer = producerService.getAllProducer();
        User loginUser = (User)httpSession.getAttribute("loginUser");

        List<producer> newProducers = new ArrayList<>();
        if(producerName.isEmpty())
            return "redirect:/producer/toProducerList";
        else
            for(producer producer:allProducer)
                if(producer.getProducerName().equals(producerName))
                    newProducers.add(producer);
        map.put("loginUser",loginUser);

        map.put("allProducer",newProducers);;
        return "producerList";
    }

    @RequestMapping("/toProducerCheckList")
    public String toProducerCheckList1(Map map , HttpSession httpSession){
        List<producer> checkProducers = producerService.getCheckProducers();
        User loginUser = (User)httpSession.getAttribute("loginUser");
//        logger.info(loginUser);
        map.put("loginUser",loginUser);
        map.put("checkProducers",checkProducers);;
        return "producerCheckList";
    }

//    @GetMapping("/toProducerCheckList")
//    public String toProducerCheckList(Model model,
//                               @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
//                               @RequestParam(defaultValue="1",value="pageSize")Integer pageSize,HttpSession httpSession){
//        if(pageNum == null){
//            pageNum = 1;
//        }
//        if(pageNum <= 0){
//            pageNum = 1;
//        }
//        if(pageSize == null){
//            pageSize = 5;
//        }
//        PageHelper.startPage(pageNum,pageSize);
//        List<producer> checkProducers = producerService.getCheckProducers();
////        List<producer> allProducer = producerService.getAllProducer();
//        try {
//            PageInfo<producer> pageInfo = new PageInfo<producer>(checkProducers,pageSize);
//            model.addAttribute("pageInfo",pageInfo);
//        }finally {
//            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
//        }
//        model.addAttribute("checkProducers",checkProducers);;
//        User loginUser = (User)httpSession.getAttribute("loginUser");
//        model.addAttribute("loginUser",loginUser);
//        return "producerCheckList";
//    }

    @RequestMapping("/toProducerFilterCheckList")
    public String toProducerFilterCheckList(Map map , HttpSession httpSession,String producerName){
        List<producer> checkProducers = producerService.getCheckProducers();
        User loginUser = (User)httpSession.getAttribute("loginUser");
        List<producer> newProducers = new ArrayList<>();
        if(producerName.isEmpty())
            return "redirect:/producer/toProducerCheckList";
        else
            for(producer producer:checkProducers)
                if(producer.getProducerName().equals(producerName))
                    newProducers.add(producer);
        map.put("loginUser",loginUser);
        map.put("checkProducers",newProducers);;
        return "producerCheckList";
    }

    @RequestMapping("/toProducerMes/{id}")
    public String toProducerMes(@PathVariable(name = "id") String id, Map map , HttpSession httpSession){
        //从session 中获取 登录用户
        User loginUser = (User)httpSession.getAttribute("loginUser");
//        获取供应商对象
        producer producer = producerService.getProducerById(Integer.parseInt(id.trim()));
//        获取供应商审核记录
        List<Map<String, String>> producerCheck = producerService.getProducerCheck(Integer.parseInt(id.trim()));

//        System.out.println(producerCheck);
        map.put("loginUser",loginUser);
        map.put("producer",producer);
        map.put("producerType",producerService.getProducerType(producer));
        map.put("producerCheck",producerCheck);
        return "producerDetail";
    }
    @RequestMapping("/toProducerCheckMes/{id}")
    public String toProducerCheckMes(@PathVariable(name = "id") String id, Map map , HttpSession httpSession){
        //从session 中获取 登录用户
        User loginUser = (User)httpSession.getAttribute("loginUser");
//        获取供应商对象
        producer producer = producerService.getProducerById(Integer.parseInt(id.trim()));
//        获取供应商审核记录
//        Map<String, String> producerCheck = producerService.getProducerCheck(Integer.parseInt(id.trim()));

//        System.out.println(producerCheck);
        map.put("loginUser",loginUser);
        map.put("producer",producer);
        map.put("producerType",producerService.getProducerType(producer));
//        map.put("producerCheck",producerCheck);
        return "producerCheckDetail";
    }

    @RequestMapping("/addBlack/{id}")
    public String addBlack(@PathVariable(name="id") String id ){

        producerService.addBlack(Integer.parseInt(id.trim()));
        return "redirect:/producer/toProducerList";
    }
    @RequestMapping("/removeBlack/{id}")
    public String removeBlack(@PathVariable(name="id") String id ){
        producerService.removeBlack(Integer.parseInt(id.trim()));
        return "redirect:/producer/toProducerList";
    }
    @RequestMapping("/updateProducerType")
    public String updateProducerType(String newType,String producerId){
        producerService.updateProducerType(Integer.parseInt(newType.trim()),Integer.parseInt(producerId.trim()));
//        System.out.println(newType);
        return "redirect:/producer/toProducerList";
    }


    @RequestMapping("/ProducerCheckSuccess")
    public String addProducerCheck( HttpSession httpSession,String type,String remark,String  producerId){
        if(type.isEmpty())
            type=String.valueOf(3);
        User loginUser = (User)httpSession.getAttribute("loginUser");
        producerCheck producerCheck = new producerCheck();
        producerCheck.setProducerId(Integer.parseInt(producerId.trim()));
        producerCheck.setCheckpersonId(loginUser.getUserId());
        producerCheck.setCheckState(2);
        producerCheck.setCheckDate(new Date());

//        System.out.println(producerCheck);
        //添加一条审核记录
        producerService.insertProducerCheck(producerCheck);
//        并将该供应商的改为审核通过
        producerService.updateProducerState(2,producerCheck.getProducerId());
//        更改供应商的类型
        producerService.updateProducerType(Integer.parseInt(type.trim()),producerCheck.getProducerId());
        return "redirect:/producer/toProducerCheckList";
    }
    @RequestMapping("/ProducerCheckFail")
    public String failProducerCheck( HttpSession httpSession,String type,String remark,String  producerId){
        User loginUser = (User)httpSession.getAttribute("loginUser");
        producerCheck producerCheck = new producerCheck();
        producerCheck.setRemark(remark);
        producerCheck.setProducerId(Integer.parseInt(producerId.trim()));
        producerCheck.setCheckpersonId(loginUser.getUserId());
        producerCheck.setCheckState(3);
        producerCheck.setCheckDate(new Date());

//        System.out.println(producerCheck);
        //添加一条审核记录
        producerService.insertProducerCheck(producerCheck);
//        并将该供应商的改为审核驳回
        producerService.updateProducerState(3,producerCheck.getProducerId());
        return "redirect:/producer/toProducerCheckList";
    }
//    @RequestMapping("/toAnnualScores")
//    public String toAnnualScores1(Map map,HttpSession httpSession){
//        List<annualScore> allAnnualScores = producerService.getAllAnnualScores();
//        User loginUser = (User)httpSession.getAttribute("loginUser");
//        List<Map<String ,String>> AllAnnualScores = producerService.getAnnualsScores();
//
//        map.put("annualScores",AllAnnualScores);
//        map.put("loginUser",loginUser);
//        return "Score";
//    }
    @GetMapping("/toAnnualScores")
    public String toAnnualScores(Model model,
                               @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                               @RequestParam(defaultValue="2",value="pageSize")Integer pageSize,HttpSession httpSession) {

        //为了程序的严谨性，判断非空：
        if (pageNum == null) {
            pageNum = 1;   //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;    //设置默认每页显示的数据数
        }
//        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String ,String>> AllAnnualScores = producerService.getAnnualsScores();
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {

            System.out.println("分页数据：" + AllAnnualScores);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Map<String ,String>> pageInfo = new PageInfo<Map<String ,String>>(AllAnnualScores, pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo", pageInfo);
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        model.addAttribute("annualScores", AllAnnualScores);
        User loginUser = (User) httpSession.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        //5.设置返回的jsp/html等前端页面
        // thymeleaf默认就会拼串classpath:/templates/xxxx.html
        return "Score";
    }

        @RequestMapping("/ScoreFilter")
    public String ScoreFilter(String year,String producerName,Map map,HttpSession httpSession){
        User loginUser = (User)httpSession.getAttribute("loginUser");
        List<Map<String ,String>> AllAnnualScores = producerService.getAnnualsScores();
        List<Map<String ,String>> newAllAnnualScores = new ArrayList<>();
        if(!year.isEmpty()&&!producerName.isEmpty())
            for(Map<String ,String> newMap:AllAnnualScores){
                if(newMap.get("year").equals(year)&&newMap.get("producer_name").equals(producerName))
                    newAllAnnualScores.add(newMap);
            }
        else if(year.isEmpty()&&!producerName.isEmpty())
            for(Map<String ,String> newMap:AllAnnualScores){
                if(newMap.get("producer_name").equals(producerName))
                    newAllAnnualScores.add(newMap);
            }
        else if(!year.isEmpty()&&producerName.isEmpty())
            for(Map<String ,String> newMap:AllAnnualScores){
                String thisYear = newMap.get("year");
                if(thisYear.equals(year.trim()))
                    newAllAnnualScores.add(newMap);
            }
        else
            return "redirect:/producer/toAnnualScores";
        map.put("annualScores",newAllAnnualScores);
        map.put("loginUser",loginUser);
        return "Score";
    }




}