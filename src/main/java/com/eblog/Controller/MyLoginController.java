package com.eblog.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MyLoginController {
    @GetMapping("/mylogin")
    public String loginPage(){
        return "/mylogin";
    }
    @GetMapping("/logout")
    public String logout(){
        return "/index";
    }
}
