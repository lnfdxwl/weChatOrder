package com.wechat.order.service;

import com.wechat.order.dataobject.OrderDetail;
import com.wechat.order.dataobject.OrderMaster;
import com.wechat.order.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zzy on 2017/12/17.
 */
public interface OrderService {

    /**
     * 创建订单
     */
    OrderDTO create(OrderDTO orderDTO);
    /**
     * 查询单个订单
     */
    OrderDTO findOne(String orderId);

    /**
     * 查询订单列表
     */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单
     */
    OrderDTO cancel(String orderId);

    /**
     * 完结订单
     */
    OrderDTO finish(String orderId);

    /**
     * 支付订单
     */
    OrderDTO paid(String orderId);
}
