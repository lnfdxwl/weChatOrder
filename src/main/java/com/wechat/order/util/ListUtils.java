package com.wechat.order.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by zzy on 2017/12/17.
 */
@Slf4j
public class ListUtils {

    public static  Object getObject(List list,Object key,String proName)  {

        Object result=null;
        try {
            for (Object theObject : list) {
                Field field = theObject.getClass().getDeclaredField(proName);
                field.setAccessible(true);
                Object fieldValue = field.get(theObject);
                if (key.equals(fieldValue)) {
                    result = theObject;
                }
            }
        }catch (Exception e){
           log.error(e.getMessage(),e);
        }
        return result;
    }
}
