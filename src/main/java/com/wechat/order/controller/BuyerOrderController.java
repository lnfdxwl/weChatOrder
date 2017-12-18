package com.wechat.order.controller;

import com.google.common.collect.Maps;
import com.wechat.order.converter.OrderForm2OrderDtoConverter;
import com.wechat.order.dataobject.OrderMaster;
import com.wechat.order.dto.OrderDTO;
import com.wechat.order.enums.ResultEnum;
import com.wechat.order.exception.SellException;
import com.wechat.order.form.OrderForm;
import com.wechat.order.service.BuyerService;
import com.wechat.order.service.OrderService;
import com.wechat.order.util.ResultVOUtil;
import com.wechat.order.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by zzy on 2017/12/17.
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;
    /**
     * 创建订单
     */
    @PostMapping("/create")
     public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
         if(bindingResult.hasErrors()){
             log.error("[创建订单] 参数不正确,orderForm={}",orderForm);
             throw  new SellException(ResultEnum.PARAM_ERROR.getCode(),
                     bindingResult.getFieldError().getDefaultMessage());
         }
         OrderDTO orderDTO= OrderForm2OrderDtoConverter.conver(orderForm);
         if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
             log.error("[创建订单] 购物车不能为空");
             throw  new SellException(ResultEnum.CART_EMPTY);
         }

        OrderDTO orderDTO1Result= orderService.create(orderDTO);
         Map map= Maps.newHashMap();
         map.put("orderId",orderDTO1Result.getOrderId());
         return ResultVOUtil.success(map);
     }
    /**
     * 订单列表
     */
    @GetMapping("list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){

        if(StringUtils.isEmpty(openid)){
            log.error("[查询订单列表] openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest=new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage= orderService.findList(openid,pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }
    /**
     * 订单详情
     */
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
    if(StringUtils.isEmpty(openid)||StringUtils.isEmpty(orderId)){
        log.error("[查询订单详情] openid或者orderId为空");
        throw new SellException(ResultEnum.PARAM_ERROR);
    }

    OrderDTO dto= buyerService.findOrderOne(openid,orderId);
    return ResultVOUtil.success(dto);
}
    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        if(StringUtils.isEmpty(openid)||StringUtils.isEmpty(orderId)){
            log.error("[订单取消] openid或者orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrderOne(openid,orderId);
        return  ResultVOUtil.success();
    }
}
