package com.wechat.order.util;

import java.util.Random;

/**
 * Created by zzy on 2017/12/17.
 * 产生key
 */
public class KeyUtils {

    /**
     * 生成订单明细主键
     * 时间加上 随机数
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random=new Random();

        Integer randNum= random.nextInt(900000)+100000;

        return System.currentTimeMillis()+String.valueOf(randNum);
    }
}
