package com.wechat.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by zzy on 2017/12/17.
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    @ApiModelProperty(name = "买家姓名")
    private String name;

    @NotEmpty(message = "买家手机号不能为空")
    @ApiModelProperty("买家手机号")
    private String phone;

    @NotEmpty(message = "买家地址不能为空")
    @ApiModelProperty("买家地址")
    private String address;

    @NotEmpty(message = "openid不能为空")
    private String openid;


    @NotEmpty(message = "购物车信息必填")
    private String items;
}
