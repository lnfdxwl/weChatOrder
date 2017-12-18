package com.wechat.order.enums;

import lombok.Getter;

/**
 * Created by zzy on 2017/12/17.
 */
@Getter
public enum  PayStatusEnum {
   WAIT(0,"等待支付"),
   SUCCESS(1,"支付成功")
    ;
    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
