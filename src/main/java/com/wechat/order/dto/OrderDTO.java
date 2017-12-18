package com.wechat.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wechat.order.dataobject.OrderDetail;
import com.wechat.order.enums.OrderStatusEnum;
import com.wechat.order.enums.PayStatusEnum;
import com.wechat.order.util.serializer.Data2LongSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zzy on 2017/12/17.
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)//不返回为null的字段
public class OrderDTO {

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("买家名字")
    private String buyerName;

    @ApiModelProperty("买家手机号")
    private String buyerPhone;

    @ApiModelProperty("买家地址")
    private String buyerAddress;

    private String buyerOpenid;

    @ApiModelProperty("订单总金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("订单状态")
    private  Integer orderStatus;


    @ApiModelProperty("订单支付状态")
    private Integer payStatus;

    @JsonSerialize(using = Data2LongSerializer.class)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @JsonSerialize(using = Data2LongSerializer.class)
    @ApiModelProperty("更新时间")
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
