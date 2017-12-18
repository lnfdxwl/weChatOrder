package com.wechat.order.enums;

import lombok.Getter;

/**
 * Created by zzy on 2017/12/17.
 */
@Getter
public enum ResultEnum {

    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存不正确"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13,"订单详情不存在"),
   ORDER_STATUS_ERROR(14,"订单状态不正确"),
   ORDER_UPDATE_FAIL(15,"订单更新失败"),
   ORDER_DETAIL_EMPTY(16,"订单中无商品"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),
CART_EMPTY(18,"购物车为空"),
ORDER_OWNER_ERROR(19,"该订单不属于当前用户"),
WX_MP_ERROR(20,"微信公众账号方面错误")
;
    private Integer code;

    private String message;

    ResultEnum(int code, String message) {
        this.code=code;
        this.message=message;
    }

}