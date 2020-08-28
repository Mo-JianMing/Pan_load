package com.mj.pan_load.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class Fe {
    private String fileName,size,user;
    private Date cDate;
}
