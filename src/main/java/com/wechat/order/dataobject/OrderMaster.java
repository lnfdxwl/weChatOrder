package com.wechat.order.dataobject;

import com.wechat.order.enums.OrderStatusEnum;
import com.wechat.order.enums.PayStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zzy on 2017/12/17.
 */
@Table(name = "order_master")
@Entity
@Data
@Slf4j
@DynamicUpdate
public class OrderMaster {

    @ApiModelProperty("订单ID")
    @Id
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
    private  Integer orderStatus= OrderStatusEnum.NEW.getCode();


    @ApiModelProperty("订单支付状态")
    private Integer payStatus= PayStatusEnum.WAIT.getCode();

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 数据库对应忽略
     */
/*    @Transient
    private List<OrderDetail> orderDetailList;*/
}
