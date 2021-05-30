package com.eblog.provider;

import com.eblog.Dao.UserDao;
import com.eblog.POJO.User;
import com.eblog.Util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Component
public class checkUserDetailService implements UserDetailsService {
    @Autowired
    private UserDao dao;
    /*
    @Autowired
    private BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
    */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.springframework.security.core.userdetails.User user1=null;
        User user=null;
        if(username!=null){
            user=dao.findByUsername(username);
            if(user!=null){
                List<GrantedAuthority> list=new ArrayList<>();
                GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_admin");
                list.add(authority);
                user1=new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),list);//Aa65241413.
                System.out.println("loadUserByUsername user:"+user1);
                //System.out.println("loadUserByUsername user's password:"+user.getPassword());
                //System.out.println("loadUserByUsername user1's password:"+user1.getPassword());
                //user1=new org.springframework.security.core.userdetails.User(user.getUsername(),new MD5Utils().code("Aa65241413."),list);

            }
        }
        return user1;
    }
}
