package com.example.demo.producer.dao;


import com.example.demo.producer.entity.producer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface producerDao {

    @Select("select * from producer")
    List<producer> getAllProducer();

    @Select("select * from producer where producer_id = #{id}")
    producer getProducerById(int id);

    @Results(id="checkRecord" ,value={
            @Result(property = "id", column = "producer_check.check_id", id = true),
            @Result(property = "userName", column = "user.user_name"),
            @Result(property = "producerName", column = "user.user_name"),
            @Result(property = "checkDate", column = "producer_check.check_date"),
            @Result(property = "checkState", column = "producer_check.check_state"),
            @Result(property = "type", column = "producer.producer_type"),
        }
    )
//    @ResultMap("checkRecord")
    @Select("select  producer_check.check_id,user.user_name,producer.producer_name,producer.producer_type,producer_check.check_date,producer_check.check_state from producer_check,producer,user where producer_check.producer_id=producer.producer_id and producer_check.checkperson_id=user.user_id and producer_check.producer_id=#{id};")
    Map<String,String> getProducerCheck(int id);

    @Update("update  producer set black=1 where producer_id = #{id} ")
    boolean addBlack(int id);

    @Update("update  producer set producer_type=#{newType} where producer_id = #{id}")
    boolean updateType(int newType,int id);
}
