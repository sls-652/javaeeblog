package com.eblog.Service.Impl;

import com.eblog.Dao.UserDao;
import com.eblog.POJO.User;
import com.eblog.Service.UseService;
import com.eblog.Util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UseService {
    @Autowired
    private UserDao userDao;
    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
    @Override
    public User checkUserByName(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }
}
