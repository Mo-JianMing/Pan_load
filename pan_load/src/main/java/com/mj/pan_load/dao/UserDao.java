package com.mj.pan_load.dao;

import com.mj.pan_load.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User su(String name,String password);
}
