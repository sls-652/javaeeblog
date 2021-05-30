package com.eblog.Service;

import com.eblog.POJO.User;

public interface UseService {
    User checkUser(String username,String password);
    User checkUserByName(String username);
}
