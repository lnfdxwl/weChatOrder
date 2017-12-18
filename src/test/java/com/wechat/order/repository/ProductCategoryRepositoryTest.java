package com.wechat.order.repository;

import com.wechat.order.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zzy on 2017/12/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest(){
       ProductCategory productCategory= productCategoryRepository.findOne(1);
       log.info(productCategory.toString());
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory=new ProductCategory("zzy最爱",3);
        productCategory.setCategoryType(56);
       ProductCategory result= productCategoryRepository.save(productCategory);
       Assert.assertNotNull(result);
    }

    @Test
    public void updateTest(){
        ProductCategory productCategory= productCategoryRepository.findOne(1);
        productCategory.setCategoryType(9);
        productCategoryRepository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list= Arrays.asList(9);
       List<ProductCategory> resultList= productCategoryRepository.findByCategoryTypeIn(list);
       Assert.assertNotEquals(0,resultList.size());
    }
}