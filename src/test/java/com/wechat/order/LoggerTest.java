package com.wechat.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zzy on 2017/12/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    @Test
    public void test1(){
        String name="zzy";
        String password="a709796558";
        log.debug("debugger");
        log.info("info");
        log.error("error");
        log.getName();
        log.info("name: {},password: {}",name,password);
        log.warn("warn");

    }
}
