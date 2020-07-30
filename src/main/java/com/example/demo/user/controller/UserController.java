package com.example.demo.user.controller;


import com.example.demo.user.DAO.UserDao;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;



    @RequestMapping("/")
    public String Welcome(){
        return "login";
    }
    @RequestMapping("/login")
    public String login(String account, String password, Map map, HttpSession httpSession){
        User user = userService.GetUser(account.trim());
        httpSession.setAttribute("loginUser" ,user);
        map.put("loginUser" ,user);
        if(user.getUserPassword().equals(password.trim()))
            return "index";
        else{
            map.put("wrongMessage","账号或密码错误");
            System.out.println("账号或密码错误！");
            return "login";
        }
    }
}
