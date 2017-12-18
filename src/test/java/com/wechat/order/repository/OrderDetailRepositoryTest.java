package com.wechat.order.repository;

import com.wechat.order.dataobject.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zzy on 2017/12/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail detail=new OrderDetail();
        detail.setDetailId("zzy1");
        detail.setOrderId("1");
        detail.setProductIcon("www.sada.com");
        detail.setProductId("5");
        detail.setProductName("hahaha");
        detail.setProductPrice(new BigDecimal(100));
        detail.setProductQuantity(10);
        OrderDetail detail12=repository.save(detail);
        log.info(detail12.toString());
    }


    @Test
    public void findByOrderId() throws Exception {

        List<OrderDetail> list=repository.findByOrderId("1");
        log.info(list.toString());
    }

}