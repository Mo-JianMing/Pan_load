package com.mj.pan_load.dao;

import com.mj.pan_load.entity.Fe;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeDao {
    void save(Fe fe);
    List<Fe> sAll(String user);
    void delete(String fileN,String userN);
}
