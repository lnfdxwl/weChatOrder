package com.wechat.order.repository;

import com.wechat.order.dataobject.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zzy on 2017/12/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public  void  saveTest() {
        OrderMaster master=new OrderMaster();
        master.setOrderId("1");
        master.setBuyerAddress("翰城花园");
        master.setBuyerName("赵泽阳");
        master.setBuyerOpenid("lnfdxwl");
        master.setBuyerPhone("18271398325");
        master.setCreateTime(new Date());
        master.setOrderAmount(new BigDecimal("10"));
        OrderMaster result=orderMasterRepository.save(master);
        log.info(result.toString());
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest request=new PageRequest(1,10);
        Page<OrderMaster> resultList = orderMasterRepository.findByBuyerOpenid("lnfdxwl",request);
        List<OrderMaster> list=resultList.getContent();
        log.info(list.toString());
    }

}