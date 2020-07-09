package com.evalution.covid.service.defitition;

import com.evalution.covid.entity.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
