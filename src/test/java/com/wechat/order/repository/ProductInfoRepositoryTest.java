package com.wechat.order.repository;

import com.wechat.order.dataobject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zzy on 2017/12/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void saveTest(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("12313213");
        productInfo.setProductName("馒头");
        productInfo.setProductPrice(new BigDecimal(30));
        productInfo.setProductStatus(1);
        productInfo.setProductDescription("好吃的馒头");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductStock(100);
        productInfo.setCategoryType(10);
        ProductInfo productInfoResult=productInfoRepository.save(productInfo);
        log.info(productInfoResult.toString());
        Assert.assertNotEquals(null,productInfoResult);
    }

    @Test
    public void findTest(){
        List<ProductInfo> list=productInfoRepository.findByProductStatus(0);
        log.info(list.toString());

    }
}