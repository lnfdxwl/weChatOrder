package com.wechat.order.util;

import com.wechat.order.vo.ResultVO;

/**
 * Created by zzy on 2017/12/14.
 */
public class ResultVOUtil {
    public static ResultVO success(Object data){
        return ResultVO.resultBuild(0,"成功",data);
    }

    public static ResultVO success(){
        return ResultVO.resultBuild(0,"成功",null);
    }

    public  static ResultVO error(Integer code,String msg){
        return  ResultVO.resultBuild(code,msg,null);
    }
}
