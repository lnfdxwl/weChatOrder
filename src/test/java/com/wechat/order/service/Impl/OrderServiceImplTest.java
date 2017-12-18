package com.wechat.order.service.Impl;

import com.google.common.collect.Lists;
import com.wechat.order.dataobject.OrderDetail;
import com.wechat.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zzy on 2017/12/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String openid="asdagfasasd";

    private final String orderId="1513500613938732883";
    @Test
    public void create() throws Exception {
        OrderDTO dto=new OrderDTO();
        dto.setBuyerAddress("汉城花园");
        dto.setBuyerName("赵泽阳");
        dto.setBuyerPhone("18271396325");
        dto.setBuyerOpenid(openid);
        //购物车
        List<OrderDetail> orderDetailList= Lists.newArrayList();
        OrderDetail o1=new OrderDetail();//12313213
        OrderDetail o2=new OrderDetail();
        o1.setProductId("12313");
        o1.setProductQuantity(1);
        BeanUtils.copyProperties(o1,o2);
        o2.setProductId("12313213");
        orderDetailList.addAll(Arrays.asList(o1,o2));
        dto.setOrderDetailList(orderDetailList);
        OrderDTO orderDTO=orderService.create(dto);
        log.info(orderDTO.toString());
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO dto=orderService.findOne(orderId);
        log.info(dto.toString());
    }

    @Test
    public void findList() throws Exception {

        log.info(orderService.findList(openid,new PageRequest(0,10)).toString());

    }

    @Test
    public void cancel() throws Exception {

        OrderDTO dto=orderService.cancel("1513503886305371185");
        log.info(dto.toString());
    }

    @Test
    public void finish() throws Exception {

        OrderDTO dto=orderService.finish("1513500703805898542");
        log.info(dto.toString());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO dto=orderService.finish("1513500540744403606");
        log.info(dto.toString());
    }

}