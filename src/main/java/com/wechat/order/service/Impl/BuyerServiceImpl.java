package com.wechat.order.service.Impl;

import com.wechat.order.dto.OrderDTO;
import com.wechat.order.enums.ResultEnum;
import com.wechat.order.exception.SellException;
import com.wechat.order.service.BuyerService;
import com.wechat.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zzy on 2017/12/17.
 */
@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {

        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrderOne(String openid, String orderId) {
        OrderDTO dto=checkOrderOwner(openid,orderId);
        if(dto==null){
            log.error("[取消订单] 查不到该订单，orderId={}" ,orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderId);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO dto= orderService.findOne(orderId);
        if(dto==null){
            return null;
        }
        if(!dto.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[查询订单] 订单的openid不一致，查询失败");
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return dto;
    }
}
