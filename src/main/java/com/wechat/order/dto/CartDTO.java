package com.wechat.order.dto;

import lombok.Data;

/**
 * 购物车 目前只在减库存中使用
 * Created by zzy on 2017/12/17.
 */
@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
