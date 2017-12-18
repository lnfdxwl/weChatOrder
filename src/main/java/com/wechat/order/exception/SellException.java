package com.wechat.order.exception;

import com.wechat.order.enums.ResultEnum;

/**
 * Created by zzy on 2017/12/17.
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public SellException(Integer code,String message){
        super(message);
        this.code=code;
    }
}
