package com.example.demo;

import com.example.demo.producer.dao.producerDao;
import com.example.demo.producer.entity.producer;
import com.example.demo.user.DAO.UserDao;
import com.example.demo.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class test {

    @Autowired
    private UserDao userDao;

    @Autowired
    private producerDao producerDao;
    @Test
    public void testSelect(){
        User user = userDao.getUserByAccount("1679");
        System.out.println(user);

    }

    @Test
    public void testGetAllProducer(){
        List<producer> allProducer = producerDao.getAllProducer();
        producer producer = allProducer.get(0);
        System.out.println(producer);

    }
    @Test
    public void testGetProducerCheck(){
        Map<String, String> producerCheck = producerDao.getProducerCheck(1);
        System.out.println(producerCheck);
    }
    @Test
    public void testAddBlack(){
        System.out.println(producerDao.addBlack(1));

    }
}
