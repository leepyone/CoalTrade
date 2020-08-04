package com.example.demo.producer.dao;


import com.example.demo.producer.entity.annualScore;
import com.example.demo.producer.entity.producer;
import com.example.demo.producer.entity.producerCheck;
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
    List<Map<String,String>> getProducerCheck(int id);

    @Update("update  producer set black=1 where producer_id = #{id} ")
    boolean addBlack(int id);
    @Update("update  producer set black=0 where producer_id = #{id} ")
    boolean removeBlack(int id);

    @Update("update  producer set producer_type=#{newType} where producer_id = #{id}")
    boolean updateType(int newType,int id);

    @Insert("insert into producer_check(`producer_id`,`check_date`,`checkperson_id`,`check_state`,`remark`) values( #{producerId},#{checkDate},#{checkpersonId},#{checkState},#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "checkId", keyColumn = "check_id")
    boolean insertProducerCheck(producerCheck producerCheck);

    @Update("update  producer set register_state=#{state} where producer_id = #{id}")
    boolean updatProducerState(int state,int id);

    @Select("select * from annual_score")
    List<annualScore> getAllAnnualScores();

    @Results(id="allAnnualScores" ,value={
            @Result(property = "producerName", column = "producer.producer_name"),
            @Result(property = "cScore", column = "annual_score.c_score"),
            @Result(property = "fScore", column = "annual_score.f_score"),
            @Result(property = "pScore", column = "annual_score.p_score"),
            @Result(property = "Level", column = "annual_score.level"),
            @Result(property = "Year", column = "annual_score.year")
    }
    )
    @Select("select producer.producer_name ,annual_score.c_score,annual_score.f_score,annual_score.p_score,annual_score.level,annual_score.year from producer,annual_score where producer.producer_id=annual_score.producer_id")
    List<Map<String,String>> getAnnualScores();
}
