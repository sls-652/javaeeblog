package com.eblog.Interceptor;

import com.eblog.Util.MD5Utils;
import com.eblog.provider.checkUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        /*
        http.formLogin()//自定义自己编写的登录界面
                .permitAll()
                .loginPage("login.html")//登录页面设置 不需要写Controller 框架自动实现
                .loginProcessingUrl("@{/admin/login}")//登录访问路径
                .defaultSuccessUrl("@{/admin/index}")//登录成功之后，跳转路径
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()//设置哪些不需要认证，可以直接访问
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
                */
        //"/admin/**"下只有admin才可以访问
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/**").permitAll()
                .and().formLogin().loginPage("/mylogin").loginProcessingUrl("/login").failureUrl("/mylogin?message=\"error\"").defaultSuccessUrl("/")
                .and().logout()
                .logoutUrl("/logout")
                .permitAll()
                //.antMatchers("/admin/**").hasRole("admin");//可以不登录查看博客首页，但是无法查看admin页面（(type=Forbidden, status=403).Access Denied)
                //.antMatchers("/admin/**").hasRole("admin").and().formLogin();//可以不登录查看博客首页和登录查看admin，但是必须登录两次，一次默认的登录页面/login,一次自己写的/admin(/login)
                //.antMatchers("/admin/**").hasRole("admin").and().formLogin().loginPage("@{/admin/login}");//启动错误
                //.antMatchers("/admin/**").hasRole("admin").and().formLogin().loginPage("/admin/login");//可以启动，不登录查看博客首页，添加/admin报错“localhost 重定向次数过多。”
                //.antMatchers("/admin/**").hasRole("admin").and().formLogin().loginPage("/admin");//可以启动，不登录查看博客首页，添加/admin报错“localhost 重定向次数过多。”
                .and().csrf().disable();
        //.antMatchers("/admin/**").hasRole("admin").and().formLogin().loginProcessingUrl("@{/admin/login}").loginProcessingUrl("@{/admin/index}");
                //.antMatchers("/admin/**").hasRole(new Neo4jProperties.Authentication().getUsername()).and().formLogin();//这个好像也不太对？我看网上说这样还是会对比role_admin，但我们没有这个字段
                //.antMatchers("/admin/**").hasRole(SecurityContextHolder.getContext().getAuthentication().getAuthorities().add(1)).and().formLogin();
        // add那里我写不来，我也不知道这个一串拼接下来对不对，我看到网上有说开了@EnableGlobalMethodSecurity(prePostEnabled = true)就会自动捕获Authencation对象了
        // 参照这个网址https://www.cnblogs.com/longfurcat/p/9417422.html，应该是第三种方法，但是他那个@PreAuthoritze，我这里好像没有。。。

        //开启注销
//        http.logout().logoutSuccessUrl("/");
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //return new MD5Utils().code("Aa65241413.");
    }

    //认证
    /*不太会写加密那一块
    public PasswordEncoder encoder(){
        return new MD5Utils.code(password);
    }*/

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        System.out.println("以用户名加载用户 userDetailService:"+userDetailService);

        //System.out.println( new BCryptPasswordEncoder().encode("ceshi"));
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());//所以这里也没有写加密
        //auth.userDetailsService(userDetailService).passwordEncoder(new MD5Utils().code("Aa65241413."));//所以这里也没有写加密
        /*auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username from User where username=?");
                //这个是我看到的另外一种方式，但。。。我总觉得这个sql语句怪怪的，就先写在这里了
                */
    }
}
