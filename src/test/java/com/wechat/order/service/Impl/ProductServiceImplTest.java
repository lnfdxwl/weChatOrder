package com.wechat.order.service.Impl;

import com.wechat.order.dataobject.ProductInfo;
import com.wechat.order.enums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zzy on 2017/12/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo=productService.findOne("12313");
        log.info(productInfo.toString());
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> list=productService.findUpAll();
        log.info(list.toString());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest request=new PageRequest(0,2);
        Page<ProductInfo> pageable= productService.findAll(request);
        log.info("==="+pageable.getTotalElements());
        Assert.assertNotEquals(0,pageable.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("12313567");
        productInfo.setProductName("花甲面");
        productInfo.setProductPrice(new BigDecimal(30));
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setProductDescription("好吃的花甲面");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductStock(100);
        productInfo.setCategoryType(10);
        ProductInfo productInfoResult=productService.save(productInfo);
        log.info(productInfoResult.toString());
        Assert.assertNotEquals(null,productInfoResult);
    }

    @Test
    public void testFindProductIn(){
       List<ProductInfo> list= productService.findByProductIdIn(Arrays.asList("12313567"));
       log.info(list.toString());
    }

}