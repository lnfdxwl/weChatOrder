package com.wechat.order.converter;

import com.wechat.order.dataobject.OrderMaster;
import com.wechat.order.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zzy on 2017/12/17.
 */
public class OrderMater2OrderDtoConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
      return orderMasterList.stream().map(e->convert(e)).collect(Collectors.toList());
    }
}
