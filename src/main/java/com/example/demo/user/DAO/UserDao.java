package com.example.demo.user.DAO;


import com.example.demo.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

    @Select("select * from user where user_account = #{account}")
    public User getUserByAccount(String account);
}
