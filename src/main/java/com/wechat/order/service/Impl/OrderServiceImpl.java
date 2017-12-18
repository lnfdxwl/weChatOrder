package com.wechat.order.service.Impl;

import com.wechat.order.converter.OrderMater2OrderDtoConverter;
import com.wechat.order.dataobject.OrderDetail;
import com.wechat.order.dataobject.OrderMaster;
import com.wechat.order.dataobject.ProductInfo;
import com.wechat.order.dto.CartDTO;
import com.wechat.order.dto.OrderDTO;
import com.wechat.order.enums.OrderStatusEnum;
import com.wechat.order.enums.PayStatusEnum;
import com.wechat.order.enums.ResultEnum;
import com.wechat.order.exception.SellException;
import com.wechat.order.repository.OrderDetailRepository;
import com.wechat.order.repository.OrderMasterRepository;
import com.wechat.order.service.OrderService;
import com.wechat.order.service.ProductService;
import com.wechat.order.util.KeyUtils;
import com.wechat.order.util.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zzy on 2017/12/17.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductService productService;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId=KeyUtils.getUniqueKey();
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);
        //1、查询商品
        List<String> sendProductIdList= orderDTO.getOrderDetailList().stream().map(p -> p.getProductId()).collect(Collectors.toList());
        List<ProductInfo> productInfoList=productService.findByProductIdIn(sendProductIdList);
        List<String> findProductIdList=productInfoList.stream().map(z->z.getProductId()).collect(Collectors.toList());
        for (OrderDetail detail:orderDTO.getOrderDetailList()){
            //商品不存在
            if(!findProductIdList.contains(detail.getProductId())){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            ProductInfo info= (ProductInfo) ListUtils.getObject(productInfoList,detail.getProductId(),"productId");
            //2、计算订单总价
            orderAmount= orderAmount.add(info.getProductPrice().multiply(new BigDecimal(detail.getProductQuantity())));
            //订单详情入库
            detail.setDetailId(KeyUtils.getUniqueKey());//订单明细ID
            detail.setOrderId(orderId);
            BeanUtils.copyProperties(info,detail);
            orderDetailRepository.save(detail);
        }
        //3、写入订单数据库（orderMaster and orderDetail）
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderId(orderId);
        orderDTO.setOrderId(orderId);
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        orderMasterRepository.save(orderMaster);
        //4、下单成功减库存
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream()
                .map(e ->new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);


        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster master=orderMasterRepository.findOne(orderId);
        if(master==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> detailList=orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(detailList)){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(master,orderDTO);
        orderDTO.setOrderDetailList(detailList);
        return orderDTO;
    }

    @Override
    /**
     * 显示列表不现实详情
     */
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> masterPageList= orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList =OrderMater2OrderDtoConverter.convert(masterPageList.getContent());
        Page<OrderDTO> orderDTOS=new PageImpl<OrderDTO>(orderDTOList,pageable,masterPageList.getTotalElements());
        return orderDTOS;
    }

    @Override
    @Transactional
    public OrderDTO cancel(String orderId) {
        //判断订单状态
        OrderMaster master=orderMasterRepository.findOne(orderId);
         if(!master.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
             log.error("[取消订单] 订单状态不正确，orderId={},orderStatus={}",orderId,master.getOrderStatus());
             throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
         }
        //修改订单状态
        master.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMasterUpdateResult= orderMasterRepository.save(master);
        if(orderMasterUpdateResult==null&&!orderMasterUpdateResult.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode())){
            log.error("[取消订单] 更新失败 ,orderMasterUpdateResult={}",orderMasterUpdateResult);
            throw  new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        List<OrderDetail> detailList=orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(detailList)){
            log.error("[取消订单] 订单中无商品 ,detailList={}",detailList);
            throw  new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList=detailList.stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //如果已支付需要退款
        if(master.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(master,orderDTO);
        orderDTO.setOrderDetailList(detailList);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //判断订单状态
        //判断订单状态
        OrderMaster master=orderMasterRepository.findOne(orderId);
        if(!master.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[完结订单] 订单状态不正确，orderId={},orderStatus={}",orderId,master.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        master.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMasterUpdateResult= orderMasterRepository.save(master);
        if(orderMasterUpdateResult==null&&!orderMasterUpdateResult.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())){
            log.error("[完结订单] 更新失败 ,orderMasterUpdateResult={}",orderMasterUpdateResult);
            throw  new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        List<OrderDetail> detailList=orderDetailRepository.findByOrderId(orderId);
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(master,orderDTO);
        orderDTO.setOrderDetailList(detailList);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(String orderId) {
        //判断订单状态
        OrderMaster master=orderMasterRepository.findOne(orderId);
        if(!master.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[支付订单] 订单状态不正确，orderId={},orderStatus={}",orderId,master.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!master.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[支付订单] 支付状态不正确，orderId={},payStatus={}",orderId,master.getPayStatus());
            throw  new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        //修改状态
        master.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMasterUpdateResult= orderMasterRepository.save(master);
        if(orderMasterUpdateResult==null&&!orderMasterUpdateResult.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            log.error("[支付订单] 更新失败 ,orderMasterUpdateResult={}",orderMasterUpdateResult);
            throw  new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        List<OrderDetail> detailList=orderDetailRepository.findByOrderId(orderId);
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(master,orderDTO);
        orderDTO.setOrderDetailList(detailList);
        return orderDTO;
    }
}
