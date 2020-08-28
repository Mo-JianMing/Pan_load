package com.mj.pan_load;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com/mj/pan_load/dao")
public class PanLoadApplication {

    public static void main(String[] args) {
        SpringApplication.run(PanLoadApplication.class, args);
    }

}
