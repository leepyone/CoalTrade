package com.example.demo.producer.dao;


import com.example.demo.producer.entity.producer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface producerDao {

    @Select("select * from producer")
    public List<producer> getAllProducer();


}
