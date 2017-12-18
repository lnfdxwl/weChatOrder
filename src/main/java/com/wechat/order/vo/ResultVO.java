package com.wechat.order.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求返回格式
 * Created by zzy on 2017/12/14.
 */
@Data
public class ResultVO<T> implements Serializable {

    @ApiModelProperty("错误码")
    private Integer code;

    @ApiModelProperty("提示信息")
    private String msg;

    @ApiModelProperty("返回数据")
    private T data;

    /**
     * zzy封装
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ResultVO resultBuild(Integer code,String msg,Object data){
        ResultVO resultVO= new ResultVO<>();
        resultVO.setData(data);
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return  resultVO;
    }
}
