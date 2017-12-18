package com.wechat.order.dataobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by zzy on 2017/12/17.
 */
@Table(name = "order_detail")
@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("商品ID")
    private String productId;

    private String productName;

    @ApiModelProperty("商品单价")
    private BigDecimal productPrice;

    @ApiModelProperty("商品数量")
    private Integer productQuantity;

    @ApiModelProperty("商品图片")
    private String productIcon;

}
