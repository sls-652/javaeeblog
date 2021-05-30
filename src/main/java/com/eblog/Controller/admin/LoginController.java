package com.eblog.Controller.admin;

import com.eblog.Dao.UserDao;
import com.eblog.POJO.User;
import com.eblog.Service.UseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UseService userService;
    /**
     *跳转登录页面
     *返回登录页面
     */


    @GetMapping("")
    public String loginPage(HttpSession session){
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        UserDetails userDetails =(UserDetails)auth.getPrincipal();
        System.out.println(userDetails.getUsername());
        User user = userService.checkUserByName(userDetails.getUsername());
        System.out.println("获取到的用户:"+user);
        session.setAttribute("user",user);
        return "/admin/index";
    }


    public User getUser() { //为了session从获取用户信息,可以配置如下
        System.out.println("获取用户");
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        UserDetails userDetails =(UserDetails)auth.getPrincipal();
        System.out.println(userDetails.getUsername());
        return userService.checkUserByName(userDetails.getUsername());
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 登录校验
     * 登录成功跳转登录成功页面，登录失败返回登录页面
     */
    /*
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        System.out.println("user:"+user);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }*/
    @GetMapping("index")
    public String index(HttpSession session){
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        UserDetails userDetails =(UserDetails)auth.getPrincipal();
        System.out.println(userDetails.getUsername());
        User user = userService.checkUserByName(userDetails.getUsername());
        System.out.println("获取到的用户:"+user);
        session.setAttribute("user",user);
        System.out.println(user);
        return "/admin/index";
    }

    /**
     * 注销
     * 返回登录页面
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "/mylogin";
    }
}
