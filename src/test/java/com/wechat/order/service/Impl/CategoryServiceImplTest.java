package com.wechat.order.service.Impl;

import com.wechat.order.dataobject.ProductCategory;
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
public class CategoryServiceImplTest {

    @Autowired
    private  CategoryServiceImpl categoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory=categoryService.findOne(1);
        Assert.assertNotEquals(null,productCategory);
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> list=categoryService.findAll();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    @Transactional
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> list=categoryService.findByCategoryTypeIn(Arrays.asList(9));
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory=new ProductCategory("店长推荐",10);
        ProductCategory result=categoryService.save(productCategory);
        Assert.assertNotEquals(null,result);
    }

}